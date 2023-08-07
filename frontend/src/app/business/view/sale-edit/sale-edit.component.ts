import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {SaleService} from '../../data/dao/impl/sale.service';
import {ExchangeDataService} from '../../service/exchange.data.service';
import {MessageService} from '../../service/message.service';
import {Category, Customer, Sale, SaleItem, Product, Supplier} from '../../../model/Models';

import {CategoryService} from '../../data/dao/impl/category.service';
import {ProductService} from '../../data/dao/impl/product.service';
import {ActivatedRoute, Router} from '@angular/router';
import {SaleItemService} from '../../data/dao/impl/sale-item.service';
import {logger} from 'codelyzer/util/logger';


@Component({
  selector: 'app-sales-edit',
  templateUrl: './sale-edit.component.html',
  styleUrls: ['./sale-edit.component.css']
})
export class SaleEditComponent implements OnInit {

  editedSale: Sale;

  customers: Customer[];
  categories: Category[];
  products: Product[];
  suppliers: Supplier[];
  allCategory = new Category('--All products', 0);
  selectedCategory: Category = this.allCategory;
  itemsInSale: SaleItem[] = [];
  @ViewChild('saleDateInput') saleDateInput: ElementRef<HTMLInputElement>;
  newMode = false;


  constructor(
    private categoryService: CategoryService,
    public productService: ProductService,
    private route: ActivatedRoute,
    private router: Router,
    private SaleService: SaleService,
    private SaleItemService: SaleItemService,
    private readonly exchangeDataService: ExchangeDataService,
    private messageService: MessageService,
  ) {
  }

  ngOnInit(): void {

    this.exchangeDataService.getCustomers()
      .subscribe(list => {
          this.customers = list;
        }
      );

    // load id Sale from query parameter
    this.route.paramMap.subscribe(params => {
      const sid = +params.get('sid');
      this.newMode = sid === 0;
      if (!this.newMode) {
        this.SaleService.findById(sid)
          .subscribe(sale => {
            this.editedSale = sale;
            if (this.editedSale === undefined) {
              this.messageService.add('Unknown Sale document id');
              this.router.navigate(['sales']).then(r => {
              });
              return;
            }
            this.SaleItemService.findItemsBySale(this.editedSale.id)
              .subscribe(items => {
                this.itemsInSale = items;
              });
          });

      } else {
        this.editedSale = new Sale(0);
        this.editedSale.createdAt = new Date();
        this.editedSale.customer = new Customer();
        this.editedSale.customer.id = 1;
        this.SaleService.findMaxSaleId()
          .subscribe(n => this.editedSale.code = 'sale-' + (n + 1));
      }
    });


    this.categoryService.findAll().subscribe(cat => {
      this.categories = cat;
    });

    this.fetchProductsBySelectedCategory();
  }


  selectCategory(category: Category): void {
    this.selectedCategory = category;
    this.fetchProductsBySelectedCategory();
  }

  selectAllCategory(): void {
    this.selectedCategory = this.allCategory;
    this.fetchProductsBySelectedCategory();
  }

  getCategories(): void {
    this.categoryService.findAll()
      .subscribe(categories => {
        this.categories = categories;
        this.exchangeDataService.setCategories(categories);
      });
  }

  addProductToDocument(productId: number): void {

    const existingItem = this.itemsInSale.find(
      item => item.product.id === productId);

    if (existingItem) {
      existingItem.quantity += 1;
    } else {

      this.productService.findById(productId).subscribe(p => {

        const newItem = new SaleItem(p, 1, p.price);
        this.itemsInSale.push(newItem);
      });
    }
  }

  removeItem(i: number): void {
    this.itemsInSale.splice(i, 1);
  }


  fetchProductsBySelectedCategory(): void {
    if (!this.selectedCategory || this.selectedCategory.id === 0 || this.selectedCategory.id === undefined
      || this.selectedCategory === this.allCategory) {
      this.productService.findAll()
        .subscribe(products => {
          this.products = products;
        });
      return;
    }
    this.productService.findProductsByCategory(this.selectedCategory.id)
      .subscribe(products => this.products = products);
  }

  save(): void {
    const saleDateValue = this.saleDateInput.nativeElement.value;
    const currentDate = new Date(); // Текущая дата и время

// Compare the year, month and day of the selected date with the current ones
    if (this.isInputDateCurrent(saleDateValue)) {
      this.editedSale.createdAt = new Date();
    } else {
      this.editedSale.createdAt = new Date(
        currentDate.getFullYear(),
        currentDate.getMonth(),
        currentDate.getDate()
      );
    }
    let saleId: number;

    // find sum of all sales
    let sumAllDocument = 0;
    this.itemsInSale
      .forEach(value => {
        sumAllDocument += value.quantity * value.price;
      });
    this.editedSale.sum = sumAllDocument;

    if (this.newMode) {
      this.SaleService.add(this.editedSale).subscribe(d => {
          console.log('new sales doc id=' + d.id);
          this.messageService.add(`Added new Sales Document with id #${d.id}`);
          saleId = d.id;
          this.itemsInSale
            .forEach(value => value.Sale = new Sale(saleId));
          this.SaleItemService.addItems(this.itemsInSale);
        }
      );
    } else {
      this.SaleService.update(this.editedSale).subscribe();
      this.SaleItemService.deleteAllItems(this.editedSale.id);
      saleId = this.editedSale.id;
      this.itemsInSale
        .forEach(value => value.Sale = new Sale(saleId));
      this.SaleItemService.addItems(this.itemsInSale);
    }

    // finished, redirect to sales list
    this.router.navigate(['sales']).then(r => {
        if (!this.newMode) {
          this.messageService.add(`Updated Sales document #'${this.editedSale.id}'`);
        }
      }
    );

  }

  // compare inputted string date: year, month and day with current date
  isInputDateCurrent(dateFromForm: string): boolean {

    const [day, month, year] = dateFromForm.split('.');
    const selectedDate = new Date(`${year}-${month}-${day}`);
    const currentDate = new Date(); // Текущая дата и время

    return selectedDate.getFullYear() === currentDate.getFullYear() &&
      selectedDate.getMonth() === currentDate.getMonth() &&
      selectedDate.getDate() === currentDate.getDate();
  }

  cancel(): void {
    this.router.navigate(['sales']);
  }

  updateDate(): void {
    // const formattedDate = this.datePipe.transform(this.editedSale.createdAt, 'dd.MM.yyyy');
    // this.saleDateInput.nativeElement.value = this.editedSale.createdAt + ' 2';
  }


}
