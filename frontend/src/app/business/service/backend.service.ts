import {Observable} from 'rxjs';
import {Inject, Injectable, InjectionToken} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {HttpMethod, Operation} from '../../model/RequestBFF';


/*
Рекомендуется выносить константы вне классов, чтобы их можно было редактировать в одном отдельном месте
Поэтому создали глобальную переменную со значением URL, которая хранится в файле app.module.ts
 */
export const BACKEND_URL = new InjectionToken<string>('url');
export const DEV_MODE = new InjectionToken<string>('url');

@Injectable({
  providedIn: 'root'
})

/*
Класс-сервис для запросов в backend конкретных данных пользователя
 */
export class BackendService {

  constructor(@Inject(BACKEND_URL) private backendRootUrl, // базовый URL сервиса (неизменяемая часть)
              private http: HttpClient // для выполнения HTTP запросов
  ) {  }


  // запрос в BFF
  requestTestData(): Observable<any> { // Observable - чтобы подписываться на результат выполнения запроса через subscribe
    // данные для BFF операции (уточняем для BFF какой именно запрос он должен выполнить в RS)
    const operation = new Operation();
    // это адрес, который BFF будет вызывать у Resource Server, добавляя к запросу access token
    operation.url = this.backendRootUrl + '/product/test';
    operation.httpMethod = HttpMethod.GET; // тип запроса тоже важно указывать
    return this.http.post(environment.bffURI + '/operation', operation);
  }


}
