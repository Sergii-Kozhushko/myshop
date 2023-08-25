import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ExchangeDataService} from '../../service/exchange.data.service';
import {Category, Product} from '../../../model/Models';
import {ProductService} from '../../data/dao/impl/product.service';
import {Router} from '@angular/router';
import {MessageService} from '../../service/message.service';

@Component({
  selector: 'app-product-edit',
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.css']
})
export class ProductEditComponent implements OnInit {
  public editedProduct: Product;
  categories: Category[];


  constructor(private exchangeDataService: ExchangeDataService,
              private productService: ProductService,
              private router: Router,
              private messageService: MessageService,
              private cdRef: ChangeDetectorRef) {
  }

  ngOnInit(): void {
    this.exchangeDataService.getEditedProduct()
      .subscribe((product) => {
        this.editedProduct = product;
        this.cdRef.detectChanges();


      });
    this.exchangeDataService.getCategories()
      .subscribe((categories) => {

        this.categories = categories;
      });
    this.clearEditedProduct();
  }

  clearEditedProduct(): void {
    let cat = new Category('Select category', 0);
    this.editedProduct = {
      id: 0,
      name: '',
      price: 0,
      wholesalePrice: 0,
      quantity: 0,
      active: true,
      category: cat
    };
  }

  save(): void {
    this.productService.update(this.editedProduct).subscribe(p => {
      this.messageService.add(`Updated product '${this.editedProduct.name}'`);
      this.clearEditedProduct();
      this.exchangeDataService.setEditedProduct(this.editedProduct);
      this.exchangeDataService.setUpdateProductsInGrid();
      this.productService.refreshProductsList();
    });


  }

  addNew(): void {
    if (!this.editedProduct.name.trim()) {
      this.messageService.add('Can\'t add product with empty name');
      return;
    }

    this.productService.add(this.editedProduct).subscribe(result => {
      this.messageService.add(`Product '${this.editedProduct.name}' added successfully`);
      this.clearEditedProduct();
      this.exchangeDataService.setUpdateProductsInGrid();
      this.productService.refreshProductsList();
    });

  }

  cancel(): void {
    this.clearEditedProduct();
    this.exchangeDataService.setEditedProduct(this.editedProduct);
  }

  delete(): void {
    this.editedProduct.active = false;
    // this.productService.delete(this.editedProduct.id);
    this.productService.update(this.editedProduct).subscribe();
    this.messageService.add(`Product '${this.editedProduct.name} (id: ${this.editedProduct.id})' was deleted successfully. Soft`);
    this.clearEditedProduct();
    // дать сигналу компоненту грид - обновить товары в категории
    this.exchangeDataService.setUpdateProductsInGrid();
  }

}
