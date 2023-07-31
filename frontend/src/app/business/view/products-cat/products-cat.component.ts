import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Category} from '../../../model/Models';
import {CategoryService} from '../../data/dao/impl/CategoryService';
import {ActivatedRoute} from '@angular/router';
import {ExchangeDataService} from '../../service/ExchangeDataService';
import {isError} from 'util';

@Component({
  selector: 'app-products-cat',
  templateUrl: './products-cat.component.html',
  styleUrls: ['./products-cat.component.css']
})
export class ProductsCatComponent implements OnInit{
  categories: Category[];
  selectedCategory: Category;

  // @Output() categorySelected = new EventEmitter<number>();

  constructor(public categoryService: CategoryService,
              private route: ActivatedRoute,
              private readonly exchangeDataService: ExchangeDataService) {
  }

  ngOnInit(): void {
    // this.selectedCategory = + this.route.snapshot.paramMap.get('cid'); // получить параметр из адресной строки
    // подписываемся на обновления выбранной категории
    this.exchangeDataService.getSelectedCategory()
      .subscribe((newCategory) => {
        this.selectedCategory = newCategory;

      });

    this.getCategories();
  }


  selectCategory(category: Category): void {
    this.exchangeDataService.setSelectedCategory(category);
  }

  getCategories(): void {
    this.categoryService.findAll()
      .subscribe(categories => {
        this.categories = categories;
        this.exchangeDataService.setCategories(categories);
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
        this.exchangeDataService.setCategories(this.categories);
      });
  }


  // protected readonly isError = isError;
}
