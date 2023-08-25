import { Component, OnInit } from '@angular/core';
import { SpinnerService } from './oauth2/spinner/spinner.service';
import { KeycloakService } from './oauth2/bff/keycloak.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {

  cookieEnabled: boolean; // will store true or false - whether cookies are enabled or disabled in the browser

  spinner: SpinnerService; // method will be automatically called when initializing the component

  constructor(
    private spinnerService: SpinnerService,
    private keycloakService: KeycloakService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.spinner = this.spinnerService;

    this.cookieEnabled = navigator.cookieEnabled; // check if cookies are enabled in the browser

    // try to set a test cookie - if not successful, cookies are not working
    if (!this.cookieEnabled) {
      document.cookie = 'testcookie';
      this.cookieEnabled = document.cookie.indexOf('testcookie') !== -1; // store true or false in the variable
    }
  }
}
