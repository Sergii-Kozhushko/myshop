import {Inject, Injectable, InjectionToken} from '@angular/core';
import {CommonService} from './common.service';
import {Customer, Product} from '../../../../model/Models';
import {CustomerDAO} from '../interface/CustomerDAO';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {MessageService} from '../../../service/message.service';


export const CUSTOMER_URL_TOKEN = new InjectionToken<string>('url');
@Injectable({
  providedIn: 'root'
})
export class CustomerService extends CommonService<Customer> implements CustomerDAO {

  constructor(@Inject(CUSTOMER_URL_TOKEN) private baseUrl, // уникальный url для запросов
              private http: HttpClient,
              router: Router,
              messageService: MessageService) {
    super(baseUrl, http, router, messageService);
  }
}
