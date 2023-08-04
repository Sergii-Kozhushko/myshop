export class Category {
  id: number;
  name: string;
  parentId: number;

  constructor(name: string, parentId: number) {
    this.name = name;
    this.parentId = parentId;
  }


}

export class Product {
  id: number;
  name: string;
  price: number;
  wholesalePrice: number;
  quantity: number;
  active: boolean;
  category: Category;

  constructor(id: number, name: string, price: number, wholesalePrice: number,
              quantity: number, active: boolean, category: Category = new Category('', 0)) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.wholesalePrice = wholesalePrice;
    this.quantity = quantity;
    this.active = active;
    this.category = category;
  }
}

export class Customer {
  id: number;
  name: string;
  address: string;
  phone: string;
  email: string;
  discountValue: number;
  discountCardNumber: string;
  dateBirth?: Date; // ? - property optional
  acceptSMSList: boolean;
}

export class DecInvoice {
  id: number;
  createdAt: Date;
  updatedAt: Date;
  code: string;
  discount: number;
  saleCondition: string;
  sum: number;
  isActive: boolean;
  customer: Customer;
  products?: DecInvoiceProduct[];
}

export class DecInvoiceProduct {
  id: number;
  product: Product;
  decInvoice: DecInvoice;
  quantity: number;
  price: number;

  constructor(product: Product, quantity: number, price: number) {
    this.product = product;
    this.quantity = quantity;
    this.price = price;
  }
}
