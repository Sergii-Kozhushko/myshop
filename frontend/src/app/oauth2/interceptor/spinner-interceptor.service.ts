import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {tap} from 'rxjs/operators';
import {SpinnerService} from '../spinner/spinner.service';

/*
interceptor - это аналог фильтров в веб приложении, когда перед отправкой вы можете изменить запрос
interceptor нужно обязательно зарегистрировать в файле app.module.ts, иначе он не будет отрабатывать

этот - перехватывает все HTTP запросы, чтобы показать индикатор "спиннер загрузки" (пока не выполнится запрос)

*/

@Injectable()
export class SpinnerInterceptor implements HttpInterceptor {

  constructor(
    private spinnerService: SpinnerService // переключатель вкл/выкл для спиннера
  ) {
  }

  // метод вызывается автоматически для каждого исходящего запроса
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    this.spinnerService.show(); // вначале показываем спиннер

    // https://rxjs.dev/api/index/function/tap
    // https://rxjs.dev/api/index/function/pipe

    // реакция на выполнение каждого запроса: показ и скрытие индикатора загрузки
    return next.handle(req)
      .pipe(
        tap({

          // успешное выполнение
          next:
            (event: HttpEvent<any>) => {
              if (event instanceof HttpResponse) { // пришел ответ - значит запрос завершен
                this.spinnerService.hide(); // когда запрос выполнился - скрыть спиннер
              }
            },

          // ошибка выполнения
          error: (error) => {
            console.log(error);
            this.spinnerService.hide(); // если возникла ошибка - скрыть спиннер
          }

        })
      );
  }
}



