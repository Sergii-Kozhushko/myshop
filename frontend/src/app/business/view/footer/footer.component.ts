import {Component} from '@angular/core';
import {KeycloakService} from '../../../oauth2/bff/keycloak.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent {


  constructor(private keycloakService: KeycloakService,
              private router: Router) {
  }

  logoutAction(): void {

    //  когда вызываем веб сервис у BFF происходит несколько процессов
    //  - в ответе получаем куки со сроком действия 0, поэтому браузер удалит эти куки
    //  - KC удалит (закроет) у себя на сервере все сессии по данному пользователю
    this.keycloakService.logoutAction().subscribe({

      // нам остается просто перейти на главную страницу
      complete: (() => {
        this.router.navigate(['login']);
      }),
    });

  }

}
