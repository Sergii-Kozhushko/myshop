import {Component, OnInit} from '@angular/core';
import {Category} from '../../../model/Models';
import {CategoryService} from '../../../category.service';

@Component({
  selector: 'app-products-cat',
  templateUrl: './products-cat.component.html',
  styleUrls: ['./products-cat.component.css']
})
export class ProductsCatComponent implements OnInit{
  categories: Category[];

  constructor(public categoryService: CategoryService) {
  }

  ngOnInit(): void {
    this.getCategories();
  }

  getCategories(): void {
    this.categoryService.getAllCategories()
      .subscribe(categories => {
        this.categories = categories;
      }); // асинхронный вызов
    // this.categories = this.categoryService.getAllCategories();//  а это был бы синхронный вызов
  }

  add(name: string): void {
    name = name.trim();
    if (!name) {
      return;
    }
    this.categoryService.add(new Category(null, name, 0))
      .subscribe(category => {
        this.categories.push(category);
      });
  }

  delete(category: Category): void {
    this.categories = this.categories.filter(h => h !== category);
    // this.categoryService.deleteCategory(category).subscribe();
  }

}
