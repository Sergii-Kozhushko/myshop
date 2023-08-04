import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Category} from '../../../model/Models';
import {CategoryService} from '../../data/dao/impl/category.service';
import {ActivatedRoute} from '@angular/router';
import {ExchangeDataService} from '../../service/exchange.data.service';


@Component({
  selector: 'app-products-cat',
  templateUrl: './products-cat.component.html',
  styleUrls: ['./products-cat.component.css']
})
export class ProductsCatComponent implements OnInit {
  categories: Category[];
  selectedCategory: Category;
  allCategory = new Category('--All products', 0);

  // @Output() categorySelected = new EventEmitter<number>();

  constructor(public categoryService: CategoryService,
              private route: ActivatedRoute,
              private readonly exchangeDataService: ExchangeDataService) {
  }

  ngOnInit(): void {
    // this.selectedCategory = + this.route.snapshot.paramMap.get('cid'); // получить параметр из адресной строки
    // подписываемся на обновления выбранной категории
    this.selectedCategory = this.allCategory;
    this.exchangeDataService.getSelectedCategory()
      .subscribe((newCategory) => {
        this.selectedCategory = newCategory;
      });
    // Subject categories is filled in parent 'Products' component for all children
    this.exchangeDataService.getCategories()
      .subscribe(categories => {

          this.categories = categories;
        }
      );
    this.exchangeDataService.getUpdateCategoriesLeft()
      .subscribe(flag => {
        this.getCategories();
      });
  }


  selectCategory(category: Category): void {
    this.exchangeDataService.setSelectedCategory(category);
  }

  selectAllCategory(): void {

    this.exchangeDataService.setSelectedCategory(this.allCategory);
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
    this.categoryService.add(new Category(name, 0))
      .subscribe(category => {
        this.categories.push(category);
        this.exchangeDataService.setCategories(this.categories);
      });
  }

  addCategory(): void {
    this.categoryService.add(new Category('New category', 0))
      .subscribe(category => {
        this.categories.push(category);
        this.categories.sort((a, b) => a.name.localeCompare(b.name));
        this.exchangeDataService.setCategories(this.categories);
      });

  }


}
