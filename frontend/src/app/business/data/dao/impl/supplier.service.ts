import {Inject, Injectable, InjectionToken} from '@angular/core';
import {CommonService} from './common.service';
import {Supplier} from '../../../../model/Models';
import {SupplierDAO} from '../interface/SupplierDAO';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {MessageService} from '../../../service/message.service';
import {ExchangeDataService} from '../../../service/exchange.data.service';
import {Observable} from 'rxjs';
import {HttpMethod, Operation} from '../../../../model/RequestBFF';
import {environment} from '../../../../../environments/environment';
import {MatDialog} from '@angular/material/dialog';


export const SUPPLIER_URL_TOKEN = new InjectionToken<string>('url');

@Injectable({
  providedIn: 'root'
})
export class SupplierService extends CommonService<Supplier> implements SupplierDAO {

  constructor(@Inject(SUPPLIER_URL_TOKEN) private baseUrl, // уникальный url для запросов
              private http: HttpClient,
              router: Router,
              protected messageService: MessageService,
              private exchangeDataService: ExchangeDataService,
              dialog: MatDialog) {
    super(baseUrl, http, router, messageService, dialog);
  }

  refreshSuppliersList(): void {
    // load suppliers list from backend. We will use it for all app-pages
    this.findAll()
      .subscribe(list => {
        this.exchangeDataService.setSuppliers(list);
        // this.messageService.add('Suppliers list was uploaded from backend server');
      });
  }

  findMaxSupplierId(): Observable<number> { // из backend получаем тип Page, поэтому указываем any
    const operation = new Operation();
    operation.url = this.baseUrl + '/max-id/';
    operation.httpMethod = HttpMethod.GET;
    return this.http.post<number>(environment.bffURI + '/operation', operation);
  }
}
