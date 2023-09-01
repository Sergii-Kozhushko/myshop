import {Inject, Injectable, InjectionToken} from '@angular/core';
import {CommonService} from './common.service';
import {Sale} from '../../../../model/Models';
import {SaleDAO} from '../interface/SaleDAO';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {MessageService} from '../../../service/message.service';
import {Observable} from 'rxjs';
import {HttpMethod, Operation} from '../../../../model/RequestBFF';
import {environment} from '../../../../../environments/environment';
import {MatDialog} from '@angular/material/dialog';

export const SALE_URL_TOKEN = new InjectionToken<string>('url');
@Injectable({
  providedIn: 'root'
})
export class SaleService extends CommonService<Sale> implements SaleDAO {

  constructor(@Inject(SALE_URL_TOKEN) private baseUrl, // уникальный url для запросов
              private http: HttpClient,
              router: Router,
              protected messageService: MessageService,
              dialog: MatDialog) {
    super(baseUrl, http, router, messageService, dialog);
  }

  findSalesByDateDesc(): Observable<any> { // из backend получаем тип Page, поэтому указываем any
    const operation = new Operation();
    operation.url = this.baseUrl + '/all-sort-date-desc/';
    operation.httpMethod = HttpMethod.GET;
    return this.http.post(environment.bffURI + '/operation', operation);
  }

  findMaxSaleId(): Observable<number> { // из backend получаем тип Page, поэтому указываем any
    const operation = new Operation();
    operation.url = this.baseUrl + '/max-id/';
    operation.httpMethod = HttpMethod.GET;
    return this.http.post<number>(environment.bffURI + '/operation', operation);
  }

}
