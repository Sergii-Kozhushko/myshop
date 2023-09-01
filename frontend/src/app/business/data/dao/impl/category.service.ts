import {Inject, Injectable, InjectionToken, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Category} from '../../../../model/Models';
import {CommonService} from './common.service';
import {CategoryDAO} from '../interface/CategoryDAO';
import {Router} from '@angular/router';
import {MessageService} from '../../../service/message.service';
import {ExchangeDataService} from '../../../service/exchange.data.service';
import {MatDialog} from '@angular/material/dialog';

// глобальная переменная для хранения URL
export const CATEGORY_URL_TOKEN = new InjectionToken<string>('url');

// @ts-ignore
@Injectable({
  providedIn: 'root'
})
export class CategoryService extends CommonService<Category> implements CategoryDAO {

  constructor(@Inject(CATEGORY_URL_TOKEN) private baseUrl, // уникальный url для запросов
              private http: HttpClient,
              router: Router,
              messageService: MessageService,
              private exchangeDataService: ExchangeDataService,
              dialog: MatDialog
  ) {
    super(baseUrl, http, router, messageService, dialog);
  }

  refreshCategoriesList(): void {
    // load categories list from backend. We will use it for all app-pages
    this.findAll()
      .subscribe(list => {
        this.exchangeDataService.setCategories(list);
        this.messageService.add('Categories list was uploaded from backend server');
      });
  }

}
