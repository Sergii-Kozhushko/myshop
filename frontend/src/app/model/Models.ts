export class Category {
  id: number;
  name: string;
  parentId: number;

  constructor(id: number, name: string, parentId: number) {
    this.id = id;
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


  constructor(id: number, name: string, price: number, wholesalePrice: number, quantity: number) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.wholesalePrice = wholesalePrice;
    this.quantity = quantity;
  }
}
