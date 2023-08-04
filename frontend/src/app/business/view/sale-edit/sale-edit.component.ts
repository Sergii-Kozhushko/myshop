import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {DecinvoiceService} from '../../data/dao/impl/decinvoice.service';
import {ExchangeDataService} from '../../service/ExchangeDataService';
import {MessageService} from '../../service/message.service';
import {Category, Customer, DecInvoice, DecInvoiceProduct, Product} from '../../../model/Models';
import {constructor} from 'path';
import {CategoryService} from '../../data/dao/impl/category.service';
import {ProductService} from '../../data/dao/impl/product.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DecinvoiceProductService} from '../../data/dao/impl/decinvoice-product.service';
import {logger} from 'codelyzer/util/logger';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-sales-edit',
  templateUrl: './sale-edit.component.html',
  styleUrls: ['./sale-edit.component.css']
})
export class SaleEditComponent implements OnInit {
  editedSale: DecInvoice;
  customers: Customer[];
  categories: Category[];
  products: Product[];
  allCategory = new Category('--All products', 0);
  selectedCategory: Category = this.allCategory;
  itemsInSale: DecInvoiceProduct[];
  @ViewChild('saleDateInput', {static: true}) saleDateInput: ElementRef<HTMLInputElement>;

  constructor(
    private categoryService: CategoryService,
    public productService: ProductService,
    private route: ActivatedRoute,
    private router: Router,
    private decInvoiceService: DecinvoiceService,
    private decInvoiceProductService: DecinvoiceProductService,
    private readonly exchangeDataService: ExchangeDataService,
    private messageService: MessageService,
    private datePipe: DatePipe) {
  }

  ngOnInit(): void {
    // this.selectedCategory = + this.route.snapshot.paramMap.get('cid'); // получить параметр из адресной строки
    // подписываемся на обновления выбранной категории

    // load edited decinvoice and it's items
    this.route.paramMap.subscribe(params => {
      const sid = +params.get('sid');

      this.decInvoiceService.findById(sid)
        .subscribe(sale => {

          this.editedSale = sale;


          if (this.editedSale === undefined) {
            this.messageService.add('Unknown dec invoice id');
            this.router.navigate(['sales']).then(r => {
            });
            return;
          }
          this.decInvoiceProductService.findProductsByDecInvoice(this.editedSale.id)
            .subscribe(items => {

              this.itemsInSale = items;
              this.itemsInSale.forEach(i => console.log(i));


            });


        });
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
    const existingItem = this.itemsInSale.find(item => item.product.id === productId);
    if (existingItem) {
      existingItem.quantity += 1;
    } else {
      this.productService.findById(productId).subscribe(p => {
        const newItem = new DecInvoiceProduct(p, 1, p.price);
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
    // const saleDateValue = this.saleDateInput.nativeElement.value;
    // console.log('date=' + this.saleDateInput);
    // const [day, month, year] = saleDateValue.split('.');
    // this.editedSale.createdAt = new Date(`${year}-${month}-${day}`);
    // this.editedSale.updatedAt = new Date();
    this.decInvoiceService.update(this.editedSale);

    this.decInvoiceProductService.deleteAllItems(this.editedSale.id);
    this.itemsInSale.forEach(i => i.decInvoice = this.editedSale);
    this.decInvoiceProductService.addItems(this.itemsInSale);
    // this.router.navigate(['sales']);
    this.messageService.add('Sales document successfully saved');

  }

  cancel(): void {
    this.router.navigate(['sales']);
  }

  updateDate(): void {
    const formattedDate = this.datePipe.transform(this.editedSale.createdAt, 'dd.MM.yyyy');
    this.saleDateInput.nativeElement.value = formattedDate;
  }

}
