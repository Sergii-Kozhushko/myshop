import {Customer, DecInvoice, DecInvoiceProduct, Product} from './Models';

export class DecInvoiceProductSaveDto {

  product: Product;
  decInvoice: DecInvoice;
  quantity: number;
  price: number;


  constructor(product: Product, decInvoice: DecInvoice, quantity: number, price: number) {
    this.product = product;
    this.decInvoice = decInvoice;
    this.quantity = quantity;
    this.price = price;
  }
}
