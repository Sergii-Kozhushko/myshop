import {Component, OnInit} from '@angular/core';
import {KeycloakService} from '../../../oauth2/bff/keycloak.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit{


  constructor(private keycloakService: KeycloakService) {
  }

  ngOnInit(): void {

    this.keycloakService.checkAuth();
  }

}
