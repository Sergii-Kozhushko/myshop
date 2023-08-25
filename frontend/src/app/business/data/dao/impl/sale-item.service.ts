import {Inject, Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpMethod, Operation} from '../../../../model/RequestBFF';
import {environment} from '../../../../../environments/environment';
import {CommonService} from './common.service';
import {Sale, SaleItem, Product} from '../../../../model/Models';
import {SaleItemDAO} from '../interface/SaleItemDAO';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {MessageService} from '../../../service/message.service';
import {SALE_URL_TOKEN} from './sale.service';
import {SaleItemSaveDto} from '../../../../model/Dto';
import {logger} from 'codelyzer/util/logger';

@Injectable({
  providedIn: 'root'
})
export class SaleItemService extends CommonService<SaleItem> implements SaleItemDAO {

  constructor(@Inject(SALE_URL_TOKEN) private baseUrl, // уникальный url для запросов
              private http: HttpClient,
              router: Router,
              messageService: MessageService) {
    super(baseUrl, http, router, messageService);
  }

  findItemsBySale(SaleId: number): Observable<any> { // из backend получаем тип Page, поэтому указываем any
    const operation = new Operation();
    operation.url = this.baseUrl + '/get-items/' + SaleId;
    operation.httpMethod = HttpMethod.GET;
    return this.http.post(environment.bffURI + '/operation', operation);
  }

  deleteAllItems(SaleId: number): void { // из backend получаем тип Page, поэтому указываем any
    const operation = new Operation();
    operation.url = this.baseUrl + '/delete-items/' + SaleId;
    operation.httpMethod = HttpMethod.DELETE;
    this.http.post(environment.bffURI + '/operation', operation).subscribe();
  }

  addItems(list: SaleItem[]): void {

    const operation = new Operation();
    operation.url = this.baseUrl + '/add-items/';
    operation.httpMethod = HttpMethod.POST;
    // save only valuable date before transfer to backend :)
    const rafaelka: SaleItemSaveDto[] = [];
    list.forEach(item => {
      rafaelka.push(new SaleItemSaveDto(new Product(item.product.id),
        new Sale(item.sale.id),
        item.quantity, item.price)
      );
    });
    rafaelka.forEach(v => console.log(v));
    operation.body = rafaelka;
    this.http.post(environment.bffURI + '/operation', operation).subscribe();
  }
}
