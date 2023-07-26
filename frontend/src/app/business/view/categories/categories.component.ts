import {Component, OnInit} from '@angular/core';
import {CategoryService} from '../../data/dao/impl/CategoryService';
// import {constructor} from 'path';
import {Category} from '../../../model/Models';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {
  categories: Category[];

  constructor(public categoryService: CategoryService) {
  }

  ngOnInit(): void {
    this.getCategories();
  }

  getCategories(): void {
    this.categoryService.findAll()
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
