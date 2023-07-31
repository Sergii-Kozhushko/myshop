import {Component, OnInit} from '@angular/core';
import {ExchangeDataService} from '../../service/ExchangeDataService';
import {Category, Product} from '../../../model/Models';
import {ProductService} from '../../data/dao/impl/ProductService';
import {Router} from '@angular/router';
import {MessageService} from '../../service/message.service';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-product-edit',
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.css']
})
export class ProductEditComponent implements OnInit {
  public editedProduct: Product = {
    id: 0,
    name: '',
    price: 0,
    wholesalePrice: 0,
    quantity: 0,
    active: true,
    category: null
  };
  categories: Category[];


  constructor(private exchangeDataService: ExchangeDataService,
              private productService: ProductService,
              private router: Router,
              private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.exchangeDataService.getEditedProduct()
      .subscribe((product) => {
        this.editedProduct = product;

      });
    this.exchangeDataService.getCategories()
      .subscribe((categories) => {
        this.categories = categories;

      });

  }

  clearEditedProduct(): void {
    this.editedProduct = {
      id: 0,
      name: '',
      price: 0,
      wholesalePrice: 0,
      quantity: 0,
      active: true,
      category: null
    };
  }

  save(): void {
    console.log('cat id=' + this.editedProduct.category.id);
    this.productService.update(this.editedProduct);
    this.clearEditedProduct();
    this.exchangeDataService.setEditedProduct(this.editedProduct);

  }

  cancel(): void {
    this.clearEditedProduct();
    this.exchangeDataService.setEditedProduct(this.editedProduct);
  }

  delete(): void {
    this.editedProduct.active = false;
    // this.productService.delete(this.editedProduct.id);
    this.productService.update(this.editedProduct);
    this.messageService.add(`Product '${this.editedProduct.name} (id: ${this.editedProduct.id})' was deleted successfully. Soft`);
    this.clearEditedProduct();
    // дать сигналу компоненту грид - обновить товары в категории
    this.exchangeDataService.setUpdateProductsInGrid(true);
  }

}
