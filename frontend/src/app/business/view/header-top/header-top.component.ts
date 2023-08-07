import { Component } from '@angular/core';
import {KeycloakService} from '../../../oauth2/bff/keycloak.service';
import {Router} from '@angular/router';
import {AboutDialogComponent} from '../../dialog/about-dialog/about-dialog.component';
import {MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-header-top',
  templateUrl: './header-top.component.html',
  styleUrls: ['./header-top.component.css']
})
export class HeaderTopComponent {
  constructor(private keycloakService: KeycloakService,
              private router: Router,
              private dialog: MatDialog) {
  }

  logoutAction(event: Event): void {
    event.preventDefault();
    this.keycloakService.logoutAction().subscribe({

      complete: (() => {
        this.router.navigate(['login']);
      }),
    });

  }

  openAboutDialog(event: Event): void {
    event.preventDefault();
    this.dialog.open(AboutDialogComponent,
      {
        autoFocus: false,
        data: {
          dialogTitle: 'About',
          message: 'MyShop v.0.3.0. ' +
            'Account applications must be light and funny.   ' +
            'Back and front parts are written by Sergii Kozhushko.   ' +
            'Remagen, Germany, 2023'
        },
        width: '400px'
      });

  }

}
