import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {SupplyService} from '../../data/dao/impl/Supply.service';
import {ExchangeDataService} from '../../service/exchange.data.service';
import {MessageService} from '../../service/message.service';
import {Category, Customer, Supply, SupplyItem, Product, Supplier} from '../../../model/Models';

import {CategoryService} from '../../data/dao/impl/category.service';
import {ProductService} from '../../data/dao/impl/product.service';
import {ActivatedRoute, Router} from '@angular/router';
import {SupplyItemService} from '../../data/dao/impl/supply-item.service';


@Component({
  selector: 'app-supply-edit',
  templateUrl: './supply-edit.component.html',
  styleUrls: ['./supply-edit.component.css']
})
export class SupplyEditComponent implements OnInit {
  editedDocument: Supply;
  categories: Category[];
  allProducts: Product[];
  currentProducts: Product[];
  currentPage = 1;
  itemsPerPage = 20;
  totalPages: number;

  suppliers: Supplier[];
  allCategory = new Category('--All products', 0);
  selectedCategory: Category = this.allCategory;
  items: SupplyItem[] = [];
  @ViewChild('supplyDateInput') supplyDateInput: ElementRef<HTMLInputElement>;
  newMode = false;


  constructor(
    private categoryService: CategoryService,
    public productService: ProductService,
    private route: ActivatedRoute,
    private router: Router,
    private supplyService: SupplyService,
    private supplyItemService: SupplyItemService,
    private readonly exchangeDataService: ExchangeDataService,
    private messageService: MessageService,
  ) {
  }

  ngOnInit(): void {

    this.exchangeDataService.getSuppliers()
      .subscribe(list => {
          this.suppliers = list;
        }
      );

    // load id edited document from query parameter. if id = 0, then create new document
    this.route.paramMap.subscribe(params => {
      const sid = +params.get('sid');
      this.newMode = sid === 0;
      if (!this.newMode) {
        this.supplyService.findById(sid)
          .subscribe(supply => {
            this.editedDocument = supply;
            if (this.editedDocument === undefined) {
              this.messageService.add('Unknown Supply document id');
              this.router.navigate(['supply']).then(r => {
              });
              return;
            }
            // load items from backend
            this.supplyItemService.findItemsBySupply(this.editedDocument.id)
              .subscribe(items => {
                this.items = items;
              });
          });

      } else {
        this.editedDocument = new Supply(0);
        this.editedDocument.createdAt = new Date();
        this.editedDocument.supplier = new Supplier();
        this.editedDocument.supplier.id = 1;
        // this.editedDocument.customer = new Customer();
        // this.editedDocument.customer.id = 1;
        this.supplyService.findMaxSupplyId()
          .subscribe(n => this.editedDocument.code = 'supply-' + (n + 1));
      }
    });

    this.exchangeDataService.getCategories()
      .subscribe(list => {
          this.categories = list;
        console.log(this.categories);
        }
      );
    this.exchangeDataService.getProducts()
      .subscribe(list => {
          // console.log('666666666666');
          // console.log(list);
          this.allProducts = list;
        }
      );
    this.currentProducts = this.allProducts;


    // this.categoryService.findAll().subscribe(cat => {
    //   this.categories = cat;
    // });

    // this.fetchProductsBySelectedCategory();
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

    const existingItem = this.items.find(
      item => item.product.id === productId);
    console.log(existingItem);
    if (existingItem) {
      existingItem.quantity += 1;
    } else {

      const product = this.currentProducts.find(p => p.id === productId);
      const newItem = new SupplyItem(product, 1, product.wholesalePrice);
      console.log(newItem);
      this.items.push(newItem);
    }
  }

  removeItem(i: number): void {
    this.items.splice(i, 1);
  }


  fetchProductsBySelectedCategory(): void {
    if (!this.selectedCategory || this.selectedCategory.id === 0 || this.selectedCategory.id === undefined
      || this.selectedCategory === this.allCategory) {
      this.currentProducts = this.allProducts;
      return;
    }
    this.currentProducts = this.allProducts.filter(p => p.category.id === this.selectedCategory.id);
  }

  save(): void {
    const supplyDateValue = this.supplyDateInput.nativeElement.value;
    const currentDate = new Date(); // Текущая дата и время

// Compare the year, month and day of the selected date with the current ones
    if (this.isInputDateCurrent(supplyDateValue)) {
      this.editedDocument.createdAt = new Date();
    } else {
      this.editedDocument.createdAt = new Date(
        currentDate.getFullYear(),
        currentDate.getMonth(),
        currentDate.getDate()
      );
    }
    let supplyId: number;
    let sumAllDocument = 0;
    this.items
      .forEach(value => {
        sumAllDocument += value.quantity * value.price;
      });
    this.editedDocument.sum = sumAllDocument;

    if (this.newMode) {
      this.supplyService.add(this.editedDocument).subscribe(d => {

          this.messageService.add(`Added new Supply Document with id #${d.id}`);
          supplyId = d.id;

          this.items
            .forEach(value => {
              value.supply = new Supply(supplyId);
            });
          this.supplyItemService.addItems(this.items);
        }
      );
    } else {
      this.supplyService.update(this.editedDocument).subscribe();
      this.supplyItemService.deleteAllItems(this.editedDocument.id);
      supplyId = this.editedDocument.id;
      console.log('supply id=' + supplyId);

      this.items
        .forEach(value => value.supply = this.editedDocument);
      this.supplyItemService.addItems(this.items);
    }
    // now we must refresh all-app product-list
    this.productService.refreshProductsList();

    // finished, redirect to sales list
    this.router.navigate(['supply']).then(r => {
        if (!this.newMode) {
          this.messageService.add(`Supply document #'${this.editedDocument.id}' was updated successfully.`);
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
    this.router.navigate(['supply']);
  }

}

