import {Inject, Injectable, InjectionToken, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Product} from '../../../../model/Models';
import {CommonService} from './common.service';
import {ProductDAO} from '../interface/ProductDAO';
import {HttpMethod, Operation} from '../../../../model/RequestBFF';
import {Observable} from 'rxjs';
import {environment} from '../../../../../environments/environment';
import {Router} from '@angular/router';
import {MessageService} from '../../../service/message.service';
import {ExchangeDataService} from '../../../service/exchange.data.service';
import {MatDialog} from '@angular/material/dialog';


// глобальная переменная для хранения URL
export const PRODUCT_URL_TOKEN = new InjectionToken<string>('url');

@Injectable({
  providedIn: 'root'
})
export class ProductService extends CommonService<Product> implements ProductDAO {

  constructor(@Inject(PRODUCT_URL_TOKEN) private baseUrl, // уникальный url для запросов
              private http: HttpClient,
              router: Router,
              protected messageService: MessageService,
              private exchangeDataService: ExchangeDataService,
              dialog: MatDialog) {
    super(baseUrl, http, router, messageService, dialog);
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

  refreshProductsList(): void {
    // load categories list from backend. We will use it for all app-pages
    this.findAll()
      .subscribe(list => {

        this.exchangeDataService.setProducts(list);
        // this.messageService.add('Products list was uploaded from backend server');
      });
  }

}
