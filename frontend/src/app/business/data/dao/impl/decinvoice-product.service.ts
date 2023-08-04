import {Inject, Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpMethod, Operation} from '../../../../model/RequestBFF';
import {environment} from '../../../../../environments/environment';
import {CommonService} from './common.service';
import {DecInvoice, DecInvoiceProduct} from '../../../../model/Models';
import {DecInvoiceDAO} from '../interface/DecInvoiceDAO';
import {DecInvoiceProductDAO} from '../interface/DecInvoiceProductDAO';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {MessageService} from '../../../service/message.service';
import {DECINVOICE_URL_TOKEN} from './decinvoice.service';

@Injectable({
  providedIn: 'root'
})
export class DecinvoiceProductService extends CommonService<DecInvoiceProduct> implements DecInvoiceProductDAO {

  constructor(@Inject(DECINVOICE_URL_TOKEN) private baseUrl, // уникальный url для запросов
              private http: HttpClient,
              router: Router,
              messageService: MessageService) {
    super(baseUrl, http, router, messageService);
  }

  findProductsByDecInvoice(decInvoiceId: number): Observable<any> { // из backend получаем тип Page, поэтому указываем any
    const operation = new Operation();
    operation.url = this.baseUrl + '/get-products/' + decInvoiceId;
    operation.httpMethod = HttpMethod.GET;
    return this.http.post(environment.bffURI + '/operation', operation);
  }

  deleteAllItems(decInvoiceId: number): void { // из backend получаем тип Page, поэтому указываем any
    const operation = new Operation();
    operation.url = this.baseUrl + '/delete-items/' + decInvoiceId;
    operation.httpMethod = HttpMethod.DELETE;
    this.http.post(environment.bffURI + '/operation', operation).subscribe();
  }
  addItems(list: DecInvoiceProduct[]): void {
    const operation = new Operation();
    operation.url = this.baseUrl + '/add-items/';
    operation.httpMethod = HttpMethod.POST;
    operation.body = list;
    this.http.post(environment.bffURI + '/operation', operation).subscribe();
  }
}
