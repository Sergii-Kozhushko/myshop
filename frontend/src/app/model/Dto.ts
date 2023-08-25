import {Customer, Sale, SaleItem, Supply, Product} from './Models';

export class SaleItemSaveDto {

  product: Product;
  sale: Sale;
  quantity: number;
  price: number;


  constructor(product: Product, sale: Sale, quantity: number, price: number) {
    this.product = product;
    this.sale = sale;
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
