import {Inject, Injectable, InjectionToken} from '@angular/core';
import {CommonService} from './common.service';
import {DecInvoice} from '../../../../model/Models';
import {DecInvoiceDAO} from '../interface/DecInvoiceDAO';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {MessageService} from '../../../service/message.service';
import {CUSTOMER_URL_TOKEN} from './customer.service';
import {Observable} from 'rxjs';
import {HttpMethod, Operation} from '../../../../model/RequestBFF';
import {environment} from '../../../../../environments/environment';

export const DECINVOICE_URL_TOKEN = new InjectionToken<string>('url');
@Injectable({
  providedIn: 'root'
})
export class DecinvoiceService extends CommonService<DecInvoice> implements DecInvoiceDAO {

  constructor(@Inject(DECINVOICE_URL_TOKEN) private baseUrl, // уникальный url для запросов
              private http: HttpClient,
              router: Router,
              messageService: MessageService) {
    super(baseUrl, http, router, messageService);
  }

  findDecInvoicesByDateDesc(): Observable<any> { // из backend получаем тип Page, поэтому указываем any
    const operation = new Operation();
    operation.url = this.baseUrl + '/all-sort-date-desc/';
    operation.httpMethod = HttpMethod.GET;
    return this.http.post(environment.bffURI + '/operation', operation);
  }
}
