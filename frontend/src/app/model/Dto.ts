import {Customer, Sale, SaleItem, Supply, Product} from './Models';

export class SaleItemSaveDto {

  product: Product;
  Sale: Sale;
  quantity: number;
  price: number;


  constructor(product: Product, Sale: Sale, quantity: number, price: number) {
    this.product = product;
    this.Sale = Sale;
    this.quantity = quantity;
    this.price = price;
  }
}

export class SupplyItemSaveDto {

  product: Product;
  supply: Supply;
  quantity: number;
  price: number;


  constructor(product: Product, supply: Supply, quantity: number, price: number) {
    this.product = product;
    this.supply = supply;
    this.quantity = quantity;
    this.price = price;
  }
}
