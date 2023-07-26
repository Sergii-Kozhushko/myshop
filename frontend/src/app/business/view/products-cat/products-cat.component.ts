import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Category} from '../../../model/Models';
import {CategoryService} from '../../data/dao/impl/CategoryService';
import {ActivatedRoute} from '@angular/router';
import {ExchangeDataService} from '../../data/dao/impl/ExchangeDataService';

@Component({
  selector: 'app-products-cat',
  templateUrl: './products-cat.component.html',
  styleUrls: ['./products-cat.component.css']
})
export class ProductsCatComponent implements OnInit{
  categories: Category[];
  currentCategory = 0;

  // @Output() categorySelected = new EventEmitter<number>();

  constructor(public categoryService: CategoryService,
              private route: ActivatedRoute,
              private readonly exchangeDataService: ExchangeDataService) {
  }

  ngOnInit(): void {
    this.currentCategory = + this.route.snapshot.paramMap.get('cid'); // получить параметр из адресной строки
    // this.exchangeDataService.setValue(this.currentCategory);

    this.getCategories();
  }
  selectCategory(category: number): void {
    this.currentCategory = category;
    this.exchangeDataService.setSelectedCategory(category);
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
