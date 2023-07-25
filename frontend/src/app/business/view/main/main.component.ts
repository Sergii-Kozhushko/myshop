import {Component, OnInit} from '@angular/core';
import {User} from '../../../model/User';
import {SpinnerService} from '../../../oauth2/spinner/spinner.service';
import {Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {KeycloakService} from '../../../oauth2/bff/keycloak.service';
import {BackendService} from '../../service/backend.service';

/*

Класс содержит уже конкретные бизнес-данные пользователя (сюда переходим после успешной авторизации в KC)

напоминание: все запросы отправляются не напрямую в ResourceServer, а в BFF (backend for frontend), который является адаптеров
это нужно для безопасного хранения куков в браузере

*/


@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  user: User = null; // текущий пользователь (аккаунт) - получаем только его данные - по email

  spinner: SpinnerService; // индикатор загрузки в центре экрана (при каждом HTTP запросе)

  data: string; // данные, которые получаем из backend

  constructor(
    private keycloakService: KeycloakService, // запросы в KC через BFF
    private backendService: BackendService, // запросы для получения бизнес-данных пользователя
    private router: Router, // навигация, общие параметры перехода на веб страницу
    private http: HttpClient, // для создания веб запросов
    private spinnerService: SpinnerService // индикатор загрузки в центре экрана (при каждом HTTP запросе)
) {
    // т.к. напрямую к переменной spinnerService из html обратиться нельзя (потому что private),
    // то создаем свою переменную, к которой уже обращаемся из html
    this.spinner = this.spinnerService;
  }

  // вызывается автоматически Angular контейнером, во время иниц. компонента
  ngOnInit(): void {

    // сначала нужно получить данные пользователя
    console.log('requesting user profile');
    this.keycloakService.requestUserProfile().subscribe(
      {
        // успешное выполнение
        next: ((response: User) => {

          // текущий пользователь
          this.user = response;
          console.log('Successfully got user profile');
          // после успешной загрузки пользователя - теперь можно получать его данные из RS

          // вызываем тестовый сервис для проверки работы
          this.backendService.requestTestData().subscribe(
            {
              // успешное выполнение
              next: ((result: any) => {
                this.data = result.data; // данные сразу обновятся и на HTML странице
              }),

              // выполнение с ошибкой
              error: (error => {
                console.log(error);

                // пытаемся обменять Refresh Token на Access Token
                //this.exchangeRefreshToken();

              })
            }
          );


        }),

        // выполнение с ошибкой
        error: (error => {
          console.log(error);

          // пытаемся обменять Refresh Token на Access Token
          this.exchangeRefreshToken();

        })
      }
    );

  }


  // редирект на страницу авторизации
  private redirectLoginPage(): void {
    this.router.navigate(['login']);
  }

  // пытаемся обменять RT на AT (также получаем IT (ID token) - данные о пользователе)
  private exchangeRefreshToken(): void {
    console.log('exchange refresh token');
    this.keycloakService.exchangeRefreshToken().subscribe(
      {

        // если получили токены
        next: (() => {
          this.ngOnInit(); // обновляем страницу и данные
        }),


        // выполнение с ошибкой
        error: (err => {
          console.log(err);

          // если не смогли получить токены - выходим на страницу логина, чтобы запустить заново весь процесс авторизации
          // тем самым явно, с помощью действий пользователя, получаем все токены
          this.redirectLoginPage();

        })
      }
    );
  }


  // выход из системы, удаление куков, удаление сессий в KeyCloak
  logoutAction(): void {

    //  когда вызываем веб сервис у BFF происходит несколько процессов
    //  - в ответе получаем куки со сроком действия 0, поэтому браузер удалит эти куки
    //  - KC удалит (закроет) у себя на сервере все сессии по данному пользователю
    this.keycloakService.logoutAction().subscribe({

      // нам остается просто перейти на главную страницу
      complete: (() => {
        this.redirectLoginPage();
      }),
    });

  }



}


