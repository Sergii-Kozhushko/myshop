import {Inject, Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpMethod, Operation} from '../../../../model/RequestBFF';
import {environment} from '../../../../../environments/environment';
import {CommonService} from './common.service';
import {Supply, SupplyItem, Product} from '../../../../model/Models';
import {SupplyItemDAO} from '../interface/SupplyItemDAO';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {MessageService} from '../../../service/message.service';
import {SupplyItemSaveDto} from '../../../../model/Dto';
import {SUPPLY_URL_TOKEN} from './Supply.service';
import {MatDialog} from '@angular/material/dialog';

@Injectable({
  providedIn: 'root'
})
export class SupplyItemService extends CommonService<SupplyItem> implements SupplyItemDAO {

  constructor(@Inject(SUPPLY_URL_TOKEN) private baseUrl, // уникальный url для запросов
              private http: HttpClient,
              router: Router,
              protected messageService: MessageService,
              dialog: MatDialog) {
    super(baseUrl, http, router, messageService, dialog);
  }

  findItemsBySupply(SupplyId: number): Observable<any> { // из backend получаем тип Page, поэтому указываем any
    const operation = new Operation();
    operation.url = this.baseUrl + '/get-items/' + SupplyId;
    operation.httpMethod = HttpMethod.GET;
    return this.http.post(environment.bffURI + '/operation', operation);
  }

  deleteAllItems(SupplyId: number): void { // из backend получаем тип Page, поэтому указываем any
    const operation = new Operation();
    operation.url = this.baseUrl + '/delete-items/' + SupplyId;
    operation.httpMethod = HttpMethod.DELETE;
    this.http.post(environment.bffURI + '/operation', operation).subscribe();
  }

  addItems(list: SupplyItem[]): void {
    const operation = new Operation();
    operation.url = this.baseUrl + '/add-items/';
    operation.httpMethod = HttpMethod.POST;
    // save only valuable date before transfer to backend :)
    const rafaelka: SupplyItemSaveDto[] = [];
    list.forEach(item => {
      rafaelka.push(new SupplyItemSaveDto(new Product(item.product.id),
        new Supply(item.supply.id),
        item.quantity, item.price)
      );
    });
    operation.body = rafaelka;
    this.http.post(environment.bffURI + '/operation', operation).subscribe();
  }
}
