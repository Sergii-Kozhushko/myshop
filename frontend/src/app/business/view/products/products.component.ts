import {Component, OnInit} from '@angular/core';
import {KeycloakService} from '../../../oauth2/bff/keycloak.service';
import {ActivatedRoute} from '@angular/router';
import {ExchangeDataService} from '../../service/exchange.data.service';
import {CategoryService} from '../../data/dao/impl/category.service';
import {Category} from '../../../model/Models';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  public selectedCategory = 0;
  public selectedProduct = 0;

  constructor(private keycloakService: KeycloakService,
              private route: ActivatedRoute,
              private exchangeDataService: ExchangeDataService,
              private categoryService: CategoryService) {
  }

  ngOnInit(): void {
    // this.selectedCategory = +this.route.snapshot.paramMap.get('cid');
    // this.selectedProduct = +this.route.snapshot.paramMap.get('pid');

    // this.categoryService.findAll()
    //   .subscribe(categories => {
    //
    //
    //     this.exchangeDataService.setCategories(categories);
    //   });


  }

  onCategorySelected(category: number): void {
    this.selectedCategory = category;
  }

}
