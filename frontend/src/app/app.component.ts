import {Component, OnInit} from '@angular/core';
import {SpinnerService} from './oauth2/spinner/spinner.service';
import {KeycloakService} from './oauth2/bff/keycloak.service';
import {Router} from '@angular/router';
import {ExchangeDataService} from './business/service/exchange.data.service';
import {CustomerService} from './business/data/dao/impl/customer.service';
import {CategoryService} from './business/data/dao/impl/category.service';
import {ProductService} from './business/data/dao/impl/product.service';
import {SupplierService} from './business/data/dao/impl/supplier.service';

@Component({
  selector: 'app-root', // по этому названию можем обращаться к компоненту
  templateUrl: './app.component.html' // какой HTML файл отображать
})
export class AppComponent implements OnInit {

  cookieEnabled: boolean; // будет хранить true или false - включены или отключены куки в браузере

  // метод будем вызываться автоматически при иниц. компонента
  spinner: SpinnerService;

  constructor(private spinnerService: SpinnerService,
              private keycloakService: KeycloakService,
              private router: Router,
              private customerService: CustomerService,
              private categoryService: CategoryService,
              private productService: ProductService,
              private supplierService: SupplierService) {

  }

  ngOnInit(): void {
    this.spinner = this.spinnerService;
    this.customerService.refreshCustomersList();
    this.categoryService.refreshCategoriesList();
    this.productService.refreshProductsList();
    this.supplierService.refreshSuppliersList();


    this.cookieEnabled = navigator.cookieEnabled; // проверяем включены ли куки в браузере

    // попробовать установить тестовый кук - если не получится - значит куки не работают
    if (!this.cookieEnabled) { // убеждаемся, что нельзя записать кук
      document.cookie = 'testcookie';
      this.cookieEnabled = (document.cookie.indexOf('testcookie') !== -1); // записываем в переменную true или false
    }
  }


}
