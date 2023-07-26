import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {CommonDAO} from '../interface/CommonDAO';
import {environment} from '../../../../../environments/environment';
import {HttpMethod, Operation} from '../../../../model/RequestBFF';


// базовые методы доступа к данным, одинаковые для всех классов,
// чтобы не нужно было дублировать весь этот код в каждом классе-сервисе

// JSON формируется автоматически для параметров и результатов

// благодаря DAO и единому интерфейсу - мы можем вынести общую реализацию в класс выше и избежать дублирования кода
// классу остается только реализовать свои специфичные методы доступа к данным

export class CommonService<T> implements CommonDAO<T> {

  private readonly url: string;

  constructor(url: string,  // базовый URL для доступа к данным
              private httpClient: HttpClient // для выполнения HTTP запросов
  ) {
    this.url = url;
  }

  // основной смысл всех методов - просто вызвать BBF и передать туда параметры

  add(t: T): Observable<T> {
    const operation = new Operation();
    operation.url = this.url + '/add'; // это адрес, который BFF будет вызывать у Resource Server, добавляя к запросу access token
    operation.body = t; // вложенный объект (конвертируется в JSON автоматически)
    operation.httpMethod = HttpMethod.POST; // у Resource Server должен быть именно такой тип метода
    return this.httpClient.post<T>(environment.bffURI + '/operation', operation); // единый адрес вызова BFF
  }

  delete(id: number): Observable<any> {
    const operation = new Operation();
    operation.url = this.url + '/delete/' + id;
    operation.body = id;
    operation.httpMethod = HttpMethod.DELETE;
    return this.httpClient.post<T>(environment.bffURI + '/operation', operation);
  }

  findById(id: number): Observable<T> {
    const operation = new Operation();
    operation.url = this.url + '/id';
    operation.body = id;
    operation.httpMethod = HttpMethod.POST;
    return this.httpClient.post<T>(environment.bffURI + '/operation', operation);
  }

  findAll(): Observable<T[]> {
    const operation = new Operation();
    operation.url = this.url + '/all';
    operation.body = ' ';
    operation.httpMethod = HttpMethod.GET;
    return this.httpClient.post<T[]>(environment.bffURI + '/operation', operation);
  }

  update(t: T): Observable<any> {
    const operation = new Operation();
    operation.url = this.url + '/update';
    operation.body = t;
    operation.httpMethod = HttpMethod.PUT;
    console.log('performing update' + t + 'host: ' + environment.bffURI + '/operation');
    return this.httpClient.post(environment.bffURI + '/operation', operation);
  }


}





