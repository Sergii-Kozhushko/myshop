import {Inject, Injectable, InjectionToken} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CommonService} from './CommonService';
import {CategoryDAO} from '../interface/CategoryDAO';
// import {CategorySearchValues} from '../../model/SearchObjects';
import {Observable} from 'rxjs';
import {environment} from '../../../../../environments/environment';
import {Category} from '../../../../model/Models';
import {HttpMethod, Operation} from '../../../../oauth2/model/RequestBFF';


// глобальная переменная для хранения URL
export const CATEGORY_URL_TOKEN = new InjectionToken<string>('url');

@Injectable({
  providedIn: 'root'
})

export class CategoryService extends CommonService<Category> implements CategoryDAO {

  constructor(@Inject(CATEGORY_URL_TOKEN) private baseUrl, // уникальный url для запросов
              private http: HttpClient // для выполнения HTTP запросов
  ) {
    super(baseUrl, http);
  }

  // поиск по любым параметрам
  // findCategories(categorySearchValues: CategorySearchValues): Observable<any> {
  //   const operation = new Operation();
  //   operation.url = this.baseUrl + '/search'; // это адрес, который BFF будет вызывать у Resource Server, добавляя к запросу access token
  //   operation.body = categorySearchValues;
  //   operation.httpMethod = HttpMethod.POST;
  //   return this.http.post(environment.bffURI + '/operation', operation);
  // }
}
