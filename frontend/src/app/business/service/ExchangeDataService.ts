import {Injectable} from '@angular/core';
import {Subject, Observable} from 'rxjs';
import {Category, Product} from '../../model/Models';

@Injectable({
  providedIn: 'root',
})
export class ExchangeDataService {
  public selectedCategory$: Subject<Category> = new Subject<Category>();
  public editedProduct$: Subject<Product> = new Subject<Product>();
  private messages$: Subject<string[]> = new Subject<string[]>();


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
