import {Injectable} from '@angular/core';
import {Subject, Observable} from 'rxjs';
import {Category, Product} from '../../model/Models';

@Injectable({
  providedIn: 'root',
})
export class ExchangeDataService {
  private selectedCategory$: Subject<Category> = new Subject<Category>();
  private editedProduct$: Subject<Product> = new Subject<Product>();
  private messages$: Subject<string[]> = new Subject<string[]>();
  // key - force product-grid component to update product list
  private updateProductsInGrid$: Subject<boolean> = new Subject<boolean>();
  private updateCategoriesLeft$: Subject<boolean> = new Subject<boolean>();
  // all categories list
  private categories$: Subject<Category[]> = new Subject<Category[]>();

  getCategories(): Subject<Category[]> {
    return this.categories$;
  }

  setCategories(cat: Category[]): void {
    this.categories$.next(cat);
  }

  getUpdateCategoriesLeft(): Subject<boolean> {
    return this.updateCategoriesLeft$;
  }

  setUpdateCategoriesLeft(): void {
    this.updateCategoriesLeft$.next(false);
    this.updateCategoriesLeft$.next(true);
  }

  getUpdateProductsInGrid(): Subject<boolean> {
    return this.updateProductsInGrid$;
  }

  setUpdateProductsInGrid(value: boolean): void {
    this.updateProductsInGrid$.next(false);
    this.updateProductsInGrid$.next(true);
    // this.updateProductsInGrid$.next(false);
  }

  getMessages(): Subject<string[]> {
    return this.messages$;
  }

  setMessages(value: string[]): void {
    this.messages$.next(value);
  }

  public setEditedProduct(value: Product): void {
    this.editedProduct$.next(value);
  }

  getEditedProduct(): Observable<Product> {
    return this.editedProduct$.asObservable();
  }

  public setSelectedCategory(value: Category): void {
    this.selectedCategory$.next(value);
  }

  getSelectedCategory(): Observable<Category> {
    return this.selectedCategory$.asObservable();
  }
}
