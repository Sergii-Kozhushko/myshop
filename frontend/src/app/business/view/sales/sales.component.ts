import {Component, OnInit} from '@angular/core';
import {KeycloakService} from '../../../oauth2/bff/keycloak.service';

@Component({
  selector: 'app-sales',
  templateUrl: './sales.component.html',
  styleUrls: ['./sales.component.css']
})
export class SalesComponent implements OnInit{
  ngOnInit(): void {

  }


  constructor() {
  }
}
