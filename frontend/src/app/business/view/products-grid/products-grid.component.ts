import {Input, Component, OnInit, OnChanges, SimpleChanges} from '@angular/core';
import {CategoryService} from '../../data/dao/impl/CategoryService';
import {ActivatedRoute} from '@angular/router';
import {ProductService} from '../../data/dao/impl/ProductService';
import {Category, Product} from '../../../model/Models';
import {ExchangeDataService} from '../../service/ExchangeDataService';
import {MessageService} from '../../service/message.service';


@Component({
  selector: 'app-products-grid',
  templateUrl: './products-grid.component.html',
  styleUrls: ['./products-grid.component.css']
})
export class ProductsGridComponent implements OnInit {
  products: Product[];
  categories: Category[];
  selectedCategory: Category = new Category(0, 'All categories', 0);
  editedProductId = 0;


  constructor(public productService: ProductService,
              private categoryService: CategoryService,
              private route: ActivatedRoute,
              private readonly exchangeDataService: ExchangeDataService,
              private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.fetchAllProducts();
    this.fetchAllCategories();

    // подписываемся на обновления выбранной категории
    this.exchangeDataService.getSelectedCategory()
      .subscribe((newCategory) => {
        this.selectedCategory = newCategory;
        this.fetchProductsBySelectedCategory();
      });
    // update products: signal from other components
    this.exchangeDataService.getUpdateProductsInGrid()
      .subscribe(update => {
        console.log('got signal to update products');
        this.fetchProductsBySelectedCategory();



      });
  }


  fetchAllProducts(): void {
    this.productService.findAll()
      .subscribe(products => {
        this.products = products;
      }); // асинхронный вызов
  }

  // fill list of categories for product-grid component
  fetchAllCategories(): void {
    this.categoryService.findAll()
      .subscribe(categories => {
        this.categories = categories;
      }); // асинхронный вызов
  }


  fetchProductsBySelectedCategory(): void {
    if (this.selectedCategory.id === 0) {
      console.log('fetch all products');
      this.fetchAllProducts();
      return;
    }
    this.productService.findProductsByCategory(this.selectedCategory.id)
      .subscribe(products => this.products = products);
  }

  editProduct(productId: number): void {
    this.editedProductId = productId;
    this.exchangeDataService.setEditedProduct(this.products.find(p => p.id === productId));
  }

  deleteCategory(): void {
    if (this.products.length) {
      this.messageService.add('Category must be empty before delete');
      return;
    }
    this.categoryService.delete(this.selectedCategory.id);
    this.messageService.add(`Category '${this.selectedCategory.name}' successfully deleted`);
    // this.selectedCategory.id = 0;
    // this.selectedCategory.name = 'All categories';

    this.exchangeDataService.setSelectedCategory(new Category(0, 'All categories', 0));
    this.fetchAllProducts();
  }

  saveCategory(): void {
    if (!this.selectedCategory.name) {
      this.messageService.add('Can not save category with empty name');
      return;
    }
  }


}
