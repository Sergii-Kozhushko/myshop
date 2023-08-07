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

  constructor(id: number, name?: string, price?: number, wholesalePrice?: number,
              quantity?: number, active?: boolean, category?: Category) {
    if (this.category === undefined) {
      this.category = new Category('', 0);
    }
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

export class Supplier {
  id: number;
  name: string;
  address: string;
  phone: string;
  email: string;
  active: boolean;
  createdAt: Date;
  updatedAt: Date;
}

export class Sale {
  id: number;
  createdAt: Date;
  updatedAt: Date;
  code: string;
  discount: number;
  saleCondition: string;
  sum: number;
  isActive: boolean;
  customer: Customer;
  products?: SaleItem[];

  constructor(id: number) {
    this.id = id;
  }
}

export class SaleItem {
  id: number;
  product: Product;
  Sale: Sale;
  quantity: number;
  price: number;

  constructor(product: Product, quantity: number, price: number) {
    this.product = product;
    this.quantity = quantity;
    this.price = price;
  }
}

export class Supply {
  id: number;
  createdAt: Date;
  updatedAt: Date;
  code: string;
  sum: number;
  additionalInfo: string;
  supplier: Supplier;
  isActive: boolean;

  // products?: SaleItem[];

  constructor(id: number) {
    this.id = id;
  }
}

export class SupplyItem {
  id: number;
  product: Product;
  Supply: Supply;
  quantity: number;
  price: number;

  constructor(product: Product, quantity: number, price: number) {
    this.product = product;
    this.quantity = quantity;
    this.price = price;
  }
}
