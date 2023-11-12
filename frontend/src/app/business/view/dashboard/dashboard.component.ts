import {Component, Inject, OnInit} from '@angular/core';
import {MessageService} from '../../service/message.service';
import {BACKEND_URL} from '../../service/backend.service';
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
        this.messageService.add('Customer list was uploaded from backend server');
      });

    this.categoryService.refreshCategoriesList();
    this.productService.refreshProductsList();
    this.supplierService.refreshSuppliersList();


    this.messageService.add('Dashboard component initialized');

  }

}
