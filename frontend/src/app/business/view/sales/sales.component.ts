import {Component, OnInit} from '@angular/core';


import {Sale} from '../../../model/Models';
import {Router} from '@angular/router';
import {ExchangeDataService} from '../../service/exchange.data.service';
import {MessageService} from '../../service/message.service';
import {SaleService} from '../../data/dao/impl/sale.service';

@Component({
  selector: 'app-sales',
  templateUrl: './sales.component.html',
  styleUrls: ['./sales.component.css']
})
export class SalesComponent implements OnInit {
  sales: Sale[];
  sortField = 'Created At';
  sortMode = 'Desc';

  constructor(
    private SaleService: SaleService,
    private readonly exchangeDataService: ExchangeDataService,
    private messageService: MessageService,
    private router: Router) {
  }

  ngOnInit(): void {
    // this.fetchAllSales();
    this.fetchAllSalesSortDateDesc();
    if (this.sortMode === 'Date created') {
      this.sales.sort((a, b) =>
        this.sortMode === 'Desc' ? b.createdAt.getTime() - a.createdAt.getTime() : a.createdAt.getTime() - b.createdAt.getTime());
    }

  }

  fetchAllSales(): void {
    this.SaleService.findAll()
      .subscribe(sales => {
        this.sales = sales;

      });
  }

  fetchAllSalesSortDateDesc(): void {
    this.SaleService.findSalesByDateDesc()
      .subscribe(sales => {
        this.sales = sales;
        this.sales.forEach(invoice => invoice.createdAt = new Date(invoice.createdAt));

      });
  }


  changeSortMode(field: string): void {
    this.sortField = field;
    this.sortMode = this.sortMode === 'Desc' ? 'Asc' : 'Desc';

    if (field === 'Created At') {
      this.sales.sort((a, b) =>
        this.sortMode === 'Desc' ? b.createdAt.getTime() - a.createdAt.getTime() : a.createdAt.getTime() - b.createdAt.getTime());
    }
    if (field === 'Sum') {
      this.sales.sort((a, b) =>
        this.sortMode === 'Desc' ? b.sum - a.sum : a.sum - b.sum);
    }
    if (field === 'Discount') {
      this.sales.sort((a, b) =>
        this.sortMode === 'Desc' ? b.discount - a.discount : a.discount - b.discount);
    }
    if (field === 'Id') {
      this.sales.sort((a, b) =>
        this.sortMode === 'Desc' ? b.id - a.id : a.id - b.id);
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

  addNew(): void {
    this.router.navigate(['sales/edit/0']);
  }


}
