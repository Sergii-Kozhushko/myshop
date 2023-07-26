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

import { MessagesComponent } from './business/view/messages/messages.component';
import { CategoriesComponent } from './business/view/categories/categories.component';
import { MainpageComponent } from './business/view/mainpage/mainpage.component';
import { LogoComponent } from './business/view/logo/logo.component';
import { HeaderComponent } from './business/view/header/header.component';
import { TopNavComponent } from './business/view/top-nav/top-nav.component';
import { ProductsComponent } from './business/view/products/products.component';
import { PurchasesComponent } from './business/view/purchases/purchases.component';
import { SalesComponent } from './business/view/sales/sales.component';
import { CustomersComponent } from './business/view/customers/customers.component';
import { OptionsComponent } from './business/view/options/options.component';
import { FooterComponent } from './business/view/footer/footer.component';
import { ProductsCatComponent } from './business/view/products-cat/products-cat.component';
import { ProductsGridComponent } from './business/view/products-grid/products-grid.component';
import {CATEGORY_URL_TOKEN} from './business/data/dao/impl/CategoryService';
import {PRODUCT_URL_TOKEN} from './business/data/dao/impl/ProductService';
import {ExchangeDataService} from './business/data/dao/impl/ExchangeDataService';
import { ProductEditComponent } from './business/view/product-edit/product-edit.component';




/*

Все настройки, описание модулей, подключение зависимостей и пр.

*/

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MainComponent,
    DashboardComponent,
    MessagesComponent,
    CategoriesComponent,
    MainpageComponent,
    LogoComponent,
    HeaderComponent,
    TopNavComponent,
    ProductsComponent,
    PurchasesComponent,
    SalesComponent,
    CustomersComponent,
    OptionsComponent,
    FooterComponent,
    ProductsCatComponent,
    ProductsGridComponent,
    ProductEditComponent

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
  ],
  providers: [ // инициализация системных объектов с нужными параметрами

    {
      provide: BACKEND_URL,
      useValue: environment.resourceServerURL
       // базовый URL веб сервиса + '/user'
    },

    {
      provide: DEV_MODE,
      useValue: environment.devMode
      // базовый URL веб сервиса + '/user'
    },
    {
      provide: CATEGORY_URL_TOKEN,
      useValue: environment.resourceServerURL + '/category'
    },
    {
      provide: PRODUCT_URL_TOKEN,
      useValue: environment.resourceServerURL + '/product'
    },

    /* нужно указывать для корректной работы диалоговых окон */
    {
      provide: HTTP_INTERCEPTORS, // все HTTP запросы будут отправлять защищенные куки
      useClass: CookiesInterceptor,
      multi: true
    },

    {
      provide: HTTP_INTERCEPTORS, // все HTTP запросы будут выполняться с отображением индикатора загрузки
      useClass: SpinnerInterceptor,
      multi: true
    },
    ExchangeDataService

  ],
  entryComponents: [ // https://angular.io/guide/entry-components

  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
