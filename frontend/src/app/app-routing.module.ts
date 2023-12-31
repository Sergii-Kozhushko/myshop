import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './oauth2/login/login.component';
import {MainComponent} from './business/view/main/main.component';
import {DashboardComponent} from './business/view/dashboard/dashboard.component';
import {CategoriesComponent} from './business/view/categories/categories.component';
import {AppComponent} from './app.component';
import {ProductsComponent} from './business/view/products/products.component';
import {SupplyComponent} from './business/view/supply/supply.component';
import {SalesComponent} from './business/view/sales/sales.component';
import {CustomersComponent} from './business/view/customers/customers.component';
import {OptionsComponent} from './business/view/options/options.component';
import { ProductsGridComponent } from './business/view/products-grid/products-grid.component';
import {SaleEditComponent} from './business/view/sale-edit/sale-edit.component';
import {SupplyEditComponent} from './business/view/supply-edit/supply-edit.component';
import {SuppliersComponent} from './business/view/suppliers/suppliers.component';
import {Supplier} from './model/Models';
import {SupplierEditComponent} from './business/view/supplier-edit/supplier-edit.component';


const routes: Routes = [

  {path: '', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'index', component: LoginComponent},
  {path: 'categories', component: CategoriesComponent},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'main', component: DashboardComponent},
  {path: 'suppliers', component: SuppliersComponent},
  {path: 'suppliers/edit/:sid', component: SupplierEditComponent },
  {path: 'supply', component: SupplyComponent},
  {path: 'supply/edit/:sid', component: SupplyEditComponent },
  {path: 'sales', component: SalesComponent },
  {path: 'sales/edit/:sid', component: SaleEditComponent },
  {path: 'products', component: ProductsComponent},
  {path: 'products/:pid?', component: ProductsComponent },
  {path: 'customers', component: CustomersComponent},
  {path: 'customers/:cid?', component: CustomersComponent },
  {path: 'options', component: OptionsComponent},
];


@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {
}
