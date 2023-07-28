import {Inject, Injectable, InjectionToken, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Category} from '../../../../model/Models';
import {CommonService} from './CommonService';
import {CategoryDAO} from '../interface/CategoryDAO';
import {Router} from '@angular/router';
import {MessageService} from '../../../service/message.service';

// глобальная переменная для хранения URL
export const CATEGORY_URL_TOKEN = new InjectionToken<string>('url');

@Injectable({
  providedIn: 'root'
})
export class CategoryService extends CommonService<Category> implements CategoryDAO {

  constructor(@Inject(CATEGORY_URL_TOKEN) private baseUrl, // уникальный url для запросов
              private http: HttpClient,
              router: Router,
              messageService: MessageService// для выполнения HTTP запросов
  ) {
    super(baseUrl, http, router, messageService);
  }

}
