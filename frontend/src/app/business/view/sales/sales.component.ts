import {Component, OnInit} from '@angular/core';
import {KeycloakService} from '../../../oauth2/bff/keycloak.service';
import {constructor} from 'path';
import {DecInvoice} from '../../../model/Models';
import {CustomerService} from '../../data/dao/impl/customer.service';
import {CategoryService} from '../../data/dao/impl/category.service';
import {ActivatedRoute} from '@angular/router';
import {ExchangeDataService} from '../../service/ExchangeDataService';
import {MessageService} from '../../service/message.service';
import {DecinvoiceService} from '../../data/dao/impl/decinvoice.service';

@Component({
  selector: 'app-sales',
  templateUrl: './sales.component.html',
  styleUrls: ['./sales.component.css']
})
export class SalesComponent implements OnInit {
  sales: DecInvoice[];
  sortField = 'Date created';
  sortMode = 'Desc';

  constructor(
    private decInvoiceService: DecinvoiceService,
    private readonly exchangeDataService: ExchangeDataService,
    private messageService: MessageService) {
  }

  ngOnInit(): void {
    // this.fetchAllDecInvoices();
    this.fetchAllDecInvoicesSortDateDesc();
    if (this.sortMode === 'Date created') {
      this.sales.sort((a, b) =>
        this.sortMode === 'Desc' ? b.createdAt.getTime() - a.createdAt.getTime() : a.createdAt.getTime() - b.createdAt.getTime());
    }

  }

  fetchAllDecInvoices(): void {
    this.decInvoiceService.findAll()
      .subscribe(sales => {
        this.sales = sales;

      });
  }

  fetchAllDecInvoicesSortDateDesc(): void {
    this.decInvoiceService.findDecInvoicesByDateDesc()
      .subscribe(sales => {
        this.sales = sales;
        this.sales.forEach(invoice => invoice.createdAt = new Date(invoice.createdAt));

      });
  }

  edit(): void {

  }

  changeSortMode(field: string): void {
    // this.sales.forEach(invoice => console.log(invoice.createdAt));
    this.sortField = field;
    this.sortMode = this.sortMode === 'Desc' ? 'Asc' : 'Desc';
    if (field === 'Date created') {
      this.sales.sort((a, b) =>
        this.sortMode === 'Desc' ? b.createdAt.getTime() - a.createdAt.getTime() : a.createdAt.getTime() - b.createdAt.getTime());
    }
    if (field === 'Sum' || field === 'Discount') {
      this.sales.sort((a, b) => this.sortMode === 'Desc' ? b.sum - a.sum : a.sum - b.sum);
    }
    if (field === 'Customer') {
      this.sales.sort((a, b) =>
        this.sortMode === 'Desc' ? a.customer.name.localeCompare(b.customer.name) : b.customer.name.localeCompare(a.customer.name));
    }
    if (field === 'Discount') {
      this.sales.sort((a, b) => this.sortMode === 'Desc' ? b.discount - a.discount : a.discount - b.discount);
    }
    if (field === 'Sale condition') {
      this.sales.sort((a, b) =>
        this.sortMode === 'Desc' ? a.saleCondition.localeCompare(b.saleCondition) : b.saleCondition.localeCompare(a.saleCondition));
    }
    if (field === 'Code') {
      this.sales.sort((a, b) =>
        this.sortMode === 'Desc' ? a.code.localeCompare(b.code) : b.code.localeCompare(a.code));
    }



  }


}
