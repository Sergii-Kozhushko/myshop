import {Observable} from 'rxjs';
import {Injectable, InjectionToken} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {User} from '../../model/User';
import {HttpMethod, Operation} from '../../model/RequestBFF';
import {Router} from '@angular/router';


// напоминание: все запросы отправляются не напрямую в ResourceServer, а в BFF (backend for frontend), который является адаптеров
// это нужно для безопасного хранения куков в браузере


@Injectable({
  providedIn: 'root'
})

export class KeycloakService {

  constructor(private http: HttpClient,
              private router: Router) {
  }


  // выход из системы
  logoutAction(): Observable<any> { //
    // просто вызываем адрес и ничего не возвращаем
    return this.http.get(environment.bffURI + '/logout');
  }


  // получаем новые токены с помощью старого Refresh Token (из кука)
  exchangeRefreshToken(): Observable<any> {
    return this.http.get(environment.bffURI + '/exchange');
  }

  // запрос данных пользователя (профайл)
  requestUserProfile(): Observable<User> {
    return this.http.get<User>(environment.bffURI + '/profile');
  }

  checkAuth(): Observable<any> {
    const operation = new Operation();
    operation.url = '/product/test';
    operation.httpMethod = HttpMethod.GET; // тип запроса тоже важно указывать
    return this.http.post(environment.bffURI + '/checkauth', operation);
      // .subscribe(
      //   {
      //     next: (response => {
      //       console.log(response);
      //     }),
      //     error: (error => {
      //       console.log(error);
      //       this.router.navigate(['login']);
      //     })
      //   }
      // );
  }


}
