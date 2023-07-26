import {Input, Component, OnInit, OnChanges, SimpleChanges} from '@angular/core';
import {CategoryService} from '../../data/dao/impl/CategoryService';
import {ActivatedRoute} from '@angular/router';
import {ProductService} from '../../data/dao/impl/ProductService';
import {Category, Product} from '../../../model/Models';
import {ExchangeDataService} from '../../data/dao/impl/ExchangeDataService';


@Component({
  selector: 'app-products-grid',
  templateUrl: './products-grid.component.html',
  styleUrls: ['./products-grid.component.css']
})
export class ProductsGridComponent implements OnInit {
  products: Product[];
  categories: Category[];
  selectedCategoryId = 0;
  public selectedCategoryName = '';
  editedProductId = 0;


  constructor(public productService: ProductService,
              private categoryService: CategoryService,
              private route: ActivatedRoute,
              private readonly exchangeDataService: ExchangeDataService) {
  }

  ngOnInit(): void {
    this.getAllProducts();
    this.getAllCategories();


    // подписываемся на обновления выбранной категории
    this.exchangeDataService.getSelectedCategory()
      .subscribe((newCategory) => {
        this.selectedCategoryId = newCategory;
        this.selectedCategoryName = this.categories
          .find(category => category.id === newCategory).name;

        this.getProductsBySelectedCategory();
      });
  }


  getAllProducts(): void {
    this.productService.findAll()
      .subscribe(products => {
        this.products = products;
      }); // асинхронный вызов
  }

  getAllCategories(): void {
    this.categoryService.findAll()
      .subscribe(categories => {
        this.categories = categories;
      }); // асинхронный вызов
  }


  getProductsBySelectedCategory(): void {
    this.productService.findProductsByCategory(this.selectedCategoryId)
      .subscribe(products => this.products = products);
  }
  editProduct(productId: number): void {
    this.editedProductId = productId;
    this.exchangeDataService.setEditedProduct(this.products.find(p => p.id === productId));
  }


}
