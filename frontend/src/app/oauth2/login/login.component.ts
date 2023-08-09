import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {environment} from '../../../environments/environment';

/*

Cтраница авторизации пользователя - без входа он не сможет получить токены и выполнять запросы в RS
Все запросы отправляются на сервер BFF, который является адаптером между клиентом и Resource Server

*/

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  // внедрение объектов (Dependency Injection)
  constructor(
    private router: Router, // навигация
    private activatedRoute: ActivatedRoute, // текущий роут, который применился
    private http: HttpClient,
    // http запросы
  ) {
  }

  // вызывается автоматически после инициализации компонента
  ngOnInit(): void {

    // считываем параметры входящего запроса
    this.activatedRoute.queryParams.subscribe(params => {

      // В компонент логин можно попасть только в 2 случаях:
      // 1) если мы успешно авторизовались и пришел ответ от KC
      // 2) просто сами открыли страницу в браузере и нас перекинуло на login, т.к. мы не авторизовались

      // Подробнее оба случая:
      // 1) если в запросе 'прилетел' параметр code - значит мы успешно авторизовались и
      // пришел ответ от KC (потому что в redirect_uri указывали текущую страницу)
      // Теперь уже code можем обменять на токены
      if (params.code) {

        // PKCE параметры
        const code = params.code;
        const state = params.state;

        // Параметры нужны только 1 раз при первичном запросе
        // Поэтому сразу после использование - очищаем параметры URL запроса,
        // чтобы при обновлении страницы они не отправились повторно и не отображались в адресной строке браузера
        window.history.pushState({}, '', document.location.href.split('?')[0]);

        // запрос на BBF, который обменяет code на токены
        // в Response от BFF будут добавлены токены (заголовок Set-Cookie), которые запишутся в куки браузера
        this.requestTokens(code, state);

        return; // обязательно выходим из метода, чтобы не выполнялся дальнейший код

      }

      // 2) если никакие другие условия выше не сработали - значит это не ответ от KC, а обычный запрос
      this.showAuthWindow(); // показываем окно авторизации, т.е. запускаем заново весь цикл PKCE для получения токенов

    });

  }

  // запускаем заново весь цикл PKCE для получения токенов - показываем окно авторизации
  private showAuthWindow(): void {

    // обязательные параметры для работы PKCE
    const state = this.randomString(40);
    localStorage.setItem('state', state);

    // подготавливаем параметры для запроса в KC на получение Authorization Code, который потом обменяем на токены
    const params = [
      'response_type=code', // этот код затем на 2 шаге будем менять на токены
      'state=' + state, // защита клиента - что ответ от auth server пришел именно на его запрос
      'client_id=' + environment.kcClientID, // из настроек KeyCloak
      'scope=' + environment.scopes, // какие именно данные хотим получить от auth server (какие токены и пр.)
      'redirect_uri=' + encodeURIComponent(environment.redirectURI), // ответ получаем на клиента, который уже отправит этот код в
      // BFF и обменяет на токены
    ];

    // итоговый URL вместе с параметрами
    const url = environment.kcBaseURL + '/auth' + '?' + params.join('&');


    // откроется окно авторизации в этом же окне (а не в новой вкладки в браузере)
    window.open(url, '_self'); // self - значит в этом же окне
  }

  // генератор случайных символов - для state (параметр запроса PKCE)
  private randomString(length: number): string {
    let result = '';
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    const charactersLength = characters.length;

    for (let i = 0; i < length; i++) {
      result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }

    return result;
  }

  // получение токенов (обмен authorization code на токены)
  private requestTokens(code: string, state: string): void {

    if (!this.checkState(state)){
      return; // если проверка не прошла - тогда выходим
    }
    //console.log('!!!Request access token through bdd: ' + environment.bffURI + '/token' + 'body ' + code);
    this.http.post(environment.bffURI + '/token', code, {
      headers: {
        'Content-Type': 'application/json; charset=UTF-8' // обязательно нужно указывать
      }
    }).subscribe({
      next: ((response: any) => {

        // если запрос на получение токенов в BFF выполнился успешно -
        // значит токены будут сохранены в безопасные куки и будут автоматически
        // отправляться с каждым запросом на BFF

        // и можно переходить на страницу для запроса данных
         this.router.navigate(['main']);
      }),

      error: (error => {

        console.log(error);
      })
    });

  }

  // проверяет state ранее сохраненный и полученный от KC
  private checkState(state: string): boolean {

    // если state от auth server совпадает со старым сохраненным значением - значит ответ пришел именно на наш запрос
    if (state !== localStorage.getItem('state') as string) {
      console.log('Invalid state');
      return false; // выходим и дальше ничего не выполняем (state обязательно должны совпадать)
    }

    // удаляем сохраненный state, уже не нужен
    localStorage.removeItem('state');
    return true;
  }




}
