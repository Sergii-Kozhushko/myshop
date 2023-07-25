import {Inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MessageService} from './message.service';
import {catchError, Observable, of} from 'rxjs';
import {Category} from './model/Models';
import {tap} from 'rxjs/operators';
import {BACKEND_URL} from './business/service/backend.service';
import {HttpMethod, Operation} from './model/RequestBFF';
import {environment} from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(@Inject(BACKEND_URL) private backendRootUrl,
              private http: HttpClient,
              private messageService: MessageService) { }

  getAllCategories(): Observable<any> {
    const operation = new Operation();
    operation.url = this.backendRootUrl + '/category/all';
    operation.httpMethod = HttpMethod.GET;
    operation.body = 'test';

    // return this.http.post(environment.bffURI + '/list-operation', operation)
    //   .pipe(
    //     tap(_ => this.log('fetched categories')),
    //     catchError(this.handleError<Category[]>('getAllCategories', []))
    //   );
    return this.http.post(environment.bffURI + '/operation', operation);
  }

  add(category: Category): Observable<Category>{
    const operation = new Operation();
    operation.url = this.backendRootUrl + '/category/add';
    operation.httpMethod = HttpMethod.POST;
    operation.body = category;
    return this.http.post<Category>(environment.bffURI + '/operation', operation);
  }

  private log(message: string) {
    this.messageService.add(`HeroService: ${message}`);
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
