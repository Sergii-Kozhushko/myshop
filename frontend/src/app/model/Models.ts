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
  active: boolean;
  category: Category;

  constructor(id: number, name: string, price: number, wholesalePrice: number,
              quantity: number, active: boolean, category: Category = new Category(0, '', 0)) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.wholesalePrice = wholesalePrice;
    this.quantity = quantity;
    this.active = active;
    this.category = category;
  }
}
