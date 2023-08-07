import { Component } from '@angular/core';
import {KeycloakService} from '../../../oauth2/bff/keycloak.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-header-top',
  templateUrl: './header-top.component.html',
  styleUrls: ['./header-top.component.css']
})
export class HeaderTopComponent {
  constructor(private keycloakService: KeycloakService,
              private router: Router) {
  }

  logoutAction(event: Event): void {
    event.preventDefault();
    this.keycloakService.logoutAction().subscribe({

      complete: (() => {
        this.router.navigate(['login']);
      }),
    });

  }

}
