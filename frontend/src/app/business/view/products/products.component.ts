import {Component, OnInit} from '@angular/core';
import {KeycloakService} from '../../../oauth2/bff/keycloak.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit{
  public selectedCategory = 0;
  public selectedProduct = 0;

  constructor(private keycloakService: KeycloakService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.selectedCategory = + this.route.snapshot.paramMap.get('cid');
    this.selectedProduct = + this.route.snapshot.paramMap.get('pid');
    console.log('pid=' + this.selectedProduct);
    // this.keycloakService.checkAuth();
  }

  onCategorySelected(category: number): void {
    this.selectedCategory = category;
  }

}
