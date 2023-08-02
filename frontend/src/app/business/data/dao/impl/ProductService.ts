import {Inject, Injectable, InjectionToken, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Product} from '../../../../model/Models';
import {CommonService} from './CommonService';
import {ProductDAO} from '../interface/ProductDAO';
import {HttpMethod, Operation} from '../../../../model/RequestBFF';
import {Observable} from 'rxjs';
import {environment} from '../../../../../environments/environment';
import {Router} from '@angular/router';
import {MessageService} from '../../../service/message.service';


// глобальная переменная для хранения URL
export const PRODUCT_URL_TOKEN = new InjectionToken<string>('url');

@Injectable({
  providedIn: 'root'
})
export class ProductService extends CommonService<Product> implements ProductDAO {

  constructor(@Inject(PRODUCT_URL_TOKEN) private baseUrl, // уникальный url для запросов
              private http: HttpClient,
              router: Router,
              messageService: MessageService) {
    super(baseUrl, http, router, messageService);
  }

  findProductsByCategory(categoryId: number): Observable<any> { // из backend получаем тип Page, поэтому указываем any
    if (!categoryId){
      return this.findAll();

    }
    const operation = new Operation();
    operation.url = this.baseUrl + '/bycategory/' + categoryId;
    operation.httpMethod = HttpMethod.GET;
    return this.http.post(environment.bffURI + '/operation', operation);
  }

}
