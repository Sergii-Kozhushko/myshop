import {Injectable} from '@angular/core';
import {Subject, Observable} from 'rxjs';
import {Product} from '../../../../model/Models';

@Injectable({
  providedIn: 'root',
})
export class ExchangeDataService {
  public selectedCategory$: Subject<number> = new Subject<number>();
  public editedProduct$: Subject<Product> = new Subject<Product>();


  public setEditedProduct(value: Product): void {
    this.editedProduct$.next(value);
  }

  getEditedProduct(): Observable<Product> {
    return this.editedProduct$.asObservable();
  }

  public setSelectedCategory(value: number): void {
    this.selectedCategory$.next(value);
  }

  getSelectedCategory(): Observable<number> {
    return this.selectedCategory$.asObservable();
  }
}
