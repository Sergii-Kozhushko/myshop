import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './oauth2/login/login.component';
import {MainComponent} from './business/view/main/main.component';
import {DashboardComponent} from './business/view/dashboard/dashboard.component';
import {CategoriesComponent} from './business/view/categories/categories.component';
import {AppComponent} from './app.component';
import {ProductsComponent} from './business/view/products/products.component';
import {PurchasesComponent} from './business/view/purchases/purchases.component';
import {SalesComponent} from './business/view/sales/sales.component';
import {CustomersComponent} from './business/view/customers/customers.component';
import {OptionsComponent} from './business/view/options/options.component';
import { ProductsGridComponent } from './business/view/products-grid/products-grid.component';


/*

Модуль для настройки всех роутингов (Routing) - перенаправлений

Является отдельным модулем в приложении, который отвечает только за роутинги

Механизм Routing связывает URI (ссылки, по которым переходит пользователь) и компоненты (страницы)

При переходе по какому-либо адресу - произойдет перенаправление на нужный компонент (страницу)

https://angular.io/guide/router


 */


// список всех роутов и связанных компонентов (маппинг)
const routes: Routes = [

  {path: '', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'index', component: LoginComponent},
  {path: 'categories', component: CategoriesComponent},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'main', component: DashboardComponent},

  {path: 'purchases', component: PurchasesComponent},
  {path: 'sales', component: SalesComponent },
  {path: 'products', component: ProductsComponent},
  { path: 'products/:cid/:pid?', component: ProductsComponent },

  {path: 'customers', component: CustomersComponent},
  {path: 'options', component: OptionsComponent},



];


@NgModule({
  imports: [
    RouterModule.forRoot(routes) // без этого работать не будет - импортирует наш mapping перенаправлений
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {
}
