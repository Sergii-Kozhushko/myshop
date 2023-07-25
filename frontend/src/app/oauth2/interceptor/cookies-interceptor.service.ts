import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';


/*

interceptor - это аналог фильтров в веб приложении, когда перед отправкой вы можете изменить запрос
interceptor нужно обязательно зарегистрировать в файле app.module.ts, иначе он не будет отрабатывать

Во все исходящие запросы от Angular к BFF - нужно добавлять заголовки, чтобы:
 - браузер прикреплял куки (в которых хранятся токены)
 - BFF корректно отработал этот запрос

*/
@Injectable()
export class CookiesInterceptor implements HttpInterceptor {

  constructor() {}

  // метод вызывается автоматически для каждого исходящего запроса
  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    request = request.clone({

      // заголовки, чтобы BFF корректно отработал запрос
      setHeaders: {
        'Content-Type': 'application/json; charset=utf-8',
        'Access-Control-Allow-Headers': '*',
        'Access-Control-Allow-Methods': '*',
        'Access-Control-Allow-Origin': '*'
      },
      // чтобы браузер прикреплял куки (в которых хранятся токены)
      withCredentials: true,
    });

    return next.handle(request); // отправляем измененный запрос далее
  }
}
