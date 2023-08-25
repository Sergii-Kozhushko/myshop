import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {LoginComponent} from './oauth2/login/login.component';
import {AppRoutingModule} from './app-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {MainComponent} from './business/view/main/main.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {CookiesInterceptor} from './oauth2/interceptor/cookies-interceptor.service';
import {SpinnerInterceptor} from './oauth2/interceptor/spinner-interceptor.service';
import {environment} from '../environments/environment';
import {BACKEND_URL, DEV_MODE} from './business/service/backend.service';
import {DashboardComponent} from './business/view/dashboard/dashboard.component';

import {MessagesComponent} from './business/view/messages/messages.component';
import {CategoriesComponent} from './business/view/categories/categories.component';
import {LogoComponent} from './business/view/logo/logo.component';
import {HeaderComponent} from './business/view/header/header.component';
import {TopNavComponent} from './business/view/top-nav/top-nav.component';
import {ProductsComponent} from './business/view/products/products.component';
import {SupplyComponent} from './business/view/supply/supply.component';
import {SalesComponent} from './business/view/sales/sales.component';
import {CustomersComponent} from './business/view/customers/customers.component';
import {OptionsComponent} from './business/view/options/options.component';
import {FooterComponent} from './business/view/footer/footer.component';
import {ProductsCatComponent} from './business/view/products-cat/products-cat.component';
import {ProductsGridComponent} from './business/view/products-grid/products-grid.component';
import {CATEGORY_URL_TOKEN} from './business/data/dao/impl/category.service';
import {PRODUCT_URL_TOKEN} from './business/data/dao/impl/product.service';
import {ExchangeDataService} from './business/service/exchange.data.service';
import {ProductEditComponent} from './business/view/product-edit/product-edit.component';
import {CustomerEditComponent} from './business/view/customer-edit/customer-edit.component';
import {CustomersGridComponent} from './business/view/customers-grid/customers-grid.component';
import {CUSTOMER_URL_TOKEN} from './business/data/dao/impl/customer.service';
import {SaleEditComponent} from './business/view/sale-edit/sale-edit.component';
import {CommonModule, DatePipe, NgOptimizedImage} from '@angular/common';
import {MessageService} from './business/service/message.service';
import {SUPPLY_URL_TOKEN} from './business/data/dao/impl/Supply.service';
import { SupplyEditComponent } from './business/view/supply-edit/supply-edit.component';
import {SUPPLIER_URL_TOKEN} from './business/data/dao/impl/supplier.service';
import { SuppliersComponent } from './business/view/suppliers/suppliers.component';
import { SupplierEditComponent } from './business/view/supplier-edit/supplier-edit.component';
import {SALE_URL_TOKEN} from './business/data/dao/impl/sale.service';
import {MatDialogModule} from '@angular/material/dialog';
import {MatButtonModule} from '@angular/material/button';
import { HeaderTopComponent } from './business/view/header-top/header-top.component';
import {NgxPaginationModule} from 'ngx-pagination';
import { DeleteDialogComponent } from './business/dialog/delete-dialog/delete-dialog.component';
import { AboutDialogComponent } from './business/dialog/about-dialog/about-dialog.component';
import { UserInfoDialogComponent } from './business/dialog/user-info-dialog/user-info-dialog.component';



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MainComponent,
    DashboardComponent,
    MessagesComponent,
    CategoriesComponent,
    LogoComponent,
    HeaderComponent,
    TopNavComponent,
    ProductsComponent,
    SupplyComponent,
    SalesComponent,
    OptionsComponent,
    FooterComponent,
    ProductsCatComponent,
    ProductsGridComponent,
    ProductEditComponent,
    CustomersComponent,
    CustomerEditComponent,
    CustomersGridComponent,
    SaleEditComponent,
    SupplyEditComponent,
    SuppliersComponent,
    SupplierEditComponent,

    HeaderTopComponent,
    DeleteDialogComponent,
    AboutDialogComponent,
    UserInfoDialogComponent

  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    CommonModule,
     MatDialogModule,
     MatButtonModule,
     NgOptimizedImage,
    NgxPaginationModule,
  ],
  providers: [

    {
      provide: BACKEND_URL,
      useValue: environment.resourceServerURL
      // base URL for all front
    },

    {
      provide: DEV_MODE,
      useValue: environment.devMode

    },
    {
      provide: CATEGORY_URL_TOKEN,
      useValue: environment.resourceServerURL + '/category'
    },
    {
      provide: PRODUCT_URL_TOKEN,
      useValue: environment.resourceServerURL + '/product'
    },
    {
      provide: CUSTOMER_URL_TOKEN,
      useValue: environment.resourceServerURL + '/customer'
    },
    {
      provide: SUPPLIER_URL_TOKEN,
      useValue: environment.resourceServerURL + '/supplier'
    },
    {
      provide: SALE_URL_TOKEN,
      useValue: environment.resourceServerURL + '/sale'
    },
    {
      provide: SUPPLY_URL_TOKEN,
      useValue: environment.resourceServerURL + '/supply'
    },

    {
      provide: HTTP_INTERCEPTORS, // all HTTP-requests will send server-sided cookies with this interceptor
      useClass: CookiesInterceptor,
      multi: true
    },

    {
      provide: HTTP_INTERCEPTORS, // all HTTP-requests will work with load indicator
      useClass: SpinnerInterceptor,
      multi: true
    },
    ExchangeDataService,
    MessageService

  ],
  entryComponents: [ // https://angular.io/guide/entry-components

  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
