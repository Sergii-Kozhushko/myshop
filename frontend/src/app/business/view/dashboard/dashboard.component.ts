import {Component, Inject, OnInit} from '@angular/core';
import {MessageService} from '../../service/message.service';
import {BACKEND_URL, DEV_MODE} from '../../service/backend.service';
import {SpinnerService} from '../../../oauth2/spinner/spinner.service';
import {KeycloakService} from '../../../oauth2/bff/keycloak.service';
import {Router} from '@angular/router';
import {CustomerService} from '../../data/dao/impl/customer.service';
import {CategoryService} from '../../data/dao/impl/category.service';
import {ProductService} from '../../data/dao/impl/product.service';
import {SupplierService} from '../../data/dao/impl/supplier.service';
import {ExchangeDataService} from '../../service/exchange.data.service';

//import {logCumulativeDurations} from '@angular-devkit/build-angular/src/builders/browser-esbuild/profiling';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  // heroes: Hero[] = [];

  constructor(
    @Inject(DEV_MODE) private devMode,
    private spinnerService: SpinnerService,
    private keycloakService: KeycloakService,
    private router: Router,
    private customerService: CustomerService,
    private categoryService: CategoryService,
    private productService: ProductService,
    private supplierService: SupplierService,
    private messageService: MessageService,
    private exchangeDataService: ExchangeDataService
  ) {
  }

  ngOnInit(): void {
    // this.customerService.refreshCustomersList();
    this.customerService.findAll()
      .subscribe(list => {
        this.exchangeDataService.setCustomers(list);
        console.log('00000');
        this.messageService.add('Customer list was uploaded from backend server');
      });

    this.categoryService.refreshCategoriesList();
    this.productService.refreshProductsList();
    this.supplierService.refreshSuppliersList();

    if (this.devMode) {
      this.messageService.add('Dashboard component initialized');
    }
  }


  getHeroes(): void {
    // this.heroService.getHeroes()
    //   .subscribe(heroes => this.heroes = heroes.slice(1, 5));
    // возвращает 4-х героев: (2-ого, 3-ого, 4-ого и 5-ого).
  }


}
