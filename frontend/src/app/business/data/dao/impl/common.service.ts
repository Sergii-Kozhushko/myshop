import {catchError, Observable, of} from 'rxjs';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {CommonDAO} from '../interface/CommonDAO';
import {environment} from '../../../../../environments/environment';
import {HttpMethod, Operation} from '../../../../model/RequestBFF';
import {Product} from '../../../../model/Models';
import {Router} from '@angular/router';
import {MessageService} from '../../../service/message.service';
import {tap} from 'rxjs/operators';
import {inject} from '@angular/core';
import {ExchangeDataService} from '../../../service/exchange.data.service';
import {logger} from 'codelyzer/util/logger';
import {AboutDialogComponent} from '../../../dialog/about-dialog/about-dialog.component';
import {MatDialog} from '@angular/material/dialog';
import {ErrorInfoDialogComponent} from '../../../dialog/error-info-dialog/error-info-dialog.component';


// базовые методы доступа к данным, одинаковые для всех классов,
// чтобы не нужно было дублировать весь этот код в каждом классе-сервисе

// JSON формируется автоматически для параметров и результатов

// благодаря DAO и единому интерфейсу - мы можем вынести общую реализацию в класс выше и избежать дублирования кода
// классу остается только реализовать свои специфичные методы доступа к данным

export class CommonService<T> implements CommonDAO<T> {

  private readonly url: string;
  private ms: MessageService;
  private eds: ExchangeDataService;


  constructor(url: string,  // базовый URL для доступа к данным
              private httpClient: HttpClient,
              private router: Router,
              protected messageService: MessageService,
              protected dialog: MatDialog
  ) {
    this.url = url;
    // this.eds = new ExchangeDataService();
    // this.ms = new MessageService();
  }

  // основной смысл всех методов - просто вызвать BBF и передать туда параметры

  add(t: T): Observable<T> {
    const operation = new Operation();
    operation.url = this.url + '/add'; // это адрес, который BFF будет вызывать у Resource Server, добавляя к запросу access token
    operation.body = t; // вложенный объект (конвертируется в JSON автоматически)
    operation.httpMethod = HttpMethod.POST; // у Resource Server должен быть именно такой тип метода
    return this.httpClient.post<T>(environment.bffURI + '/operation', operation)
      .pipe(
        tap(() => {
          // Send a success message to the message service
        }),
        catchError((error: HttpErrorResponse) => {
          this.handleError(error);
          // Верните пустой массив или другое значение по умолчанию в случае ошибки
          return [];
        }));

  }

  delete(id: number): void {
    const operation = new Operation();
    operation.url = this.url + '/delete/' + id;
    operation.body = id;
    operation.httpMethod = HttpMethod.DELETE;
    this.httpClient.post(environment.bffURI + '/operation', operation)
      .pipe(
        tap(() => {
          // Send a success message to the message service
        }),
        catchError((error: HttpErrorResponse) => {
          this.handleError(error);
          // Верните пустой массив или другое значение по умолчанию в случае ошибки
          return [];
        })
      ).subscribe();
  }


  findById(id: number): Observable<T> {

    const operation = new Operation();
    operation.url = this.url + '/get/' + id;
    // operation.body = id;
    operation.httpMethod = HttpMethod.GET;

    return this.httpClient.post<T>(environment.bffURI + '/operation', operation)
      .pipe(
        catchError((error: HttpErrorResponse) => {

          this.handleError(error);
          // Верните пустой массив или другое значение по умолчанию в случае ошибки
          return of([] as T);
        })
      );
  }

  findAll(): Observable<T[]> {
    const operation = new Operation();
    operation.url = this.url + '/all';
    operation.body = ' ';
    operation.httpMethod = HttpMethod.GET;
    return this.httpClient.post<T[]>(environment.bffURI + '/operation', operation).pipe(
      tap(() => {

        // Send a success message to the message service
      }),
      catchError((error: HttpErrorResponse) => {
        this.handleError(error);
        // Верните пустой массив или другое значение по умолчанию в случае ошибки
        return [];
      })
    );
  }

  update(t: T): Observable<T> {
    const operation = new Operation();
    operation.url = this.url + '/update';
    operation.body = t;
    operation.httpMethod = HttpMethod.PUT;

    return this.httpClient.post<T>(environment.bffURI + '/operation', operation)
      .pipe(
        tap(() => {
          // Send a success message to the message service
        }),
        catchError((error: HttpErrorResponse) => {
          this.handleError(error);
          return of([] as T);
          // Верните пустой массив или другое значение по умолчанию в случае ошибки

        })
      );

  }

  private extractErrorMessage(error: HttpErrorResponse): string {
    if (error.error && error.error.message) {
      const messages = error.error.message.split('\n');
      if (messages.length > 1) {
        // Выбираем вторую строку, которая содержит сообщение об ошибке
        const errorMessage = messages[1].trim();
        return errorMessage;
      }
    }

    return 'Unknown error occurred';
  }

  handleError(error: HttpErrorResponse): void {
    console.log('Status:', error.status);
    console.log('Error Message:', error);

    const errorMessage = error.error && error.error.errors && error.error.errors.length > 0
      ? error.error.errors[0]
      : 'Unknown error occurred';

    this.dialog.open(ErrorInfoDialogComponent,
      {
        autoFocus: false,
        data: {
          dialogTitle: 'Upps. Error!',
          message: errorMessage,
          credits: ''
        },
        width: '400px'
      });



    if (error.status === 400) {
      this.router.navigate(['login']);
    }

    // Получаем сообщение об ошибке из тела ответа сервера, если оно есть

  }


}





