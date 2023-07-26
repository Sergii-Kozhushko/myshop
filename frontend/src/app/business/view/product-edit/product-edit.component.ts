import {Component, OnInit} from '@angular/core';
import {ExchangeDataService} from '../../data/dao/impl/ExchangeDataService';
import {Product} from '../../../model/Models';
import {ProductService} from '../../data/dao/impl/ProductService';

@Component({
  selector: 'app-product-edit',
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.css']
})
export class ProductEditComponent implements OnInit{
  public editedProduct: Product = {
    id: 0,
    name: '',
    price: 0,
    wholesalePrice: 0,
    quantity: 0,
  };



  constructor(private exchangeDataService: ExchangeDataService,
              private productService: ProductService) {
  }

  ngOnInit(): void {

    this.exchangeDataService.getEditedProduct()
      .subscribe((product) => {
        this.editedProduct = product;

      });
  }
  clearEditedProduct(){
    this.editedProduct = {
      id: 0,
      name: '',
      price: 0,
      wholesalePrice: 0,
      quantity: 0,
    };

  }

  save(): void {
    console.log(this.editedProduct.name);
    this.productService.update(this.editedProduct).
    subscribe({
      next: ((response: any) => {

      }),

      error: (error => {
        console.log(error);
      })
    });

    this.clearEditedProduct()
    this.exchangeDataService.setEditedProduct(this.editedProduct);
  }

  cancel(): void {
    this.clearEditedProduct();
    this.exchangeDataService.setEditedProduct(this.editedProduct);
  }

}
