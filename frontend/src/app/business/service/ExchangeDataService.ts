import {Injectable} from '@angular/core';
import {Subject, Observable} from 'rxjs';
import {Category, Product} from '../../model/Models';

@Injectable({
  providedIn: 'root',
})
export class ExchangeDataService {
  public selectedCategory$: Subject<Category> = new Subject<Category>();
  public editedProduct$: Subject<Product> = new Subject<Product>();
  public messages$: Subject<string[]> = new Subject<string[]>();
  // key - force product-grid component to update product list
  public updateProductsInGrid$: Subject<boolean> = new Subject<boolean>();
  public categories$: Subject<Category[]> = new Subject<Category[]>();

  getCategories(): Subject<Category[]> {
    return this.categories$;
  }

  setCategories(cat: Category[]): void {
    this.categories$.next(cat);
  }


  getUpdateProductsInGrid(): Subject<boolean> {
    return this.updateProductsInGrid$;
  }

  setUpdateProductsInGrid(value: boolean): void {
    this.updateProductsInGrid$.next(value);
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
