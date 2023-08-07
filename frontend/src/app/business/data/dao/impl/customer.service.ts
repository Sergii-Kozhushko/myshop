import {Inject, Injectable, InjectionToken} from '@angular/core';
import {CommonService} from './common.service';
import {Customer, Product} from '../../../../model/Models';
import {CustomerDAO} from '../interface/CustomerDAO';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {MessageService} from '../../../service/message.service';
import {ExchangeDataService} from '../../../service/exchange.data.service';


export const CUSTOMER_URL_TOKEN = new InjectionToken<string>('url');

@Injectable({
  providedIn: 'root'
})
export class CustomerService extends CommonService<Customer> implements CustomerDAO {

  constructor(@Inject(CUSTOMER_URL_TOKEN) private baseUrl, // уникальный url для запросов
              private http: HttpClient,
              router: Router,
              protected messageService: MessageService,
              private exchangeDataService: ExchangeDataService) {
    super(baseUrl, http, router, messageService);
  }

  refreshCustomersList(): void {
    // load customers list from backend. We will use it for all app-pages
    this.findAll()
      .subscribe(list => {
        this.exchangeDataService.setCustomers(list);
        this.messageService.add('Customer list was uploaded from backend server');
      });
  }
}
