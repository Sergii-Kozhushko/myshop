import {Component, OnInit} from '@angular/core';
import {Sale, Supply} from '../../../model/Models';
import {SupplyService} from '../../data/dao/impl/Supply.service';
import {ExchangeDataService} from '../../service/exchange.data.service';
import {MessageService} from '../../service/message.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-purchases',
  templateUrl: './supply.component.html',
  styleUrls: ['./supply.component.css']
})
export class SupplyComponent implements OnInit {
  supplies: Supply[];
  sortField = 'Created At';
  sortMode = 'Desc';

  constructor(
    private SupplyService: SupplyService,
    private readonly exchangeDataService: ExchangeDataService,
    private messageService: MessageService,
    private router: Router) {
  }

  ngOnInit(): void {

    this.fetchAllSupplysSortDateDesc();
    if (this.sortMode === 'Date created') {
      this.supplies.sort((a, b) =>
        this.sortMode === 'Desc' ?
          b.createdAt.getTime() - a.createdAt.getTime()
          : a.createdAt.getTime() - b.createdAt.getTime());
    }
  }

  fetchAllSupplysSortDateDesc(): void {
    this.SupplyService.findSupplysByDateDesc()
      .subscribe(supplies => {

        this.supplies = supplies;
        // arrange date format
        this.supplies.forEach(invoice => invoice.createdAt = new Date(invoice.createdAt));
      });
  }


  changeSortMode(field: string): void {
    this.sortField = field;
    this.sortMode = this.sortMode === 'Desc' ? 'Asc' : 'Desc';
    if (field === 'Id') {
      this.supplies.sort((a, b) =>
        this.sortMode === 'Desc' ? b.id - a.id : a.id - b.id);
    }
    if (field === 'Created At') {
      this.supplies.sort((a, b) =>
        this.sortMode === 'Desc' ?
          b.createdAt.getTime() - a.createdAt.getTime() : a.createdAt.getTime() - b.createdAt.getTime());
    }

    if (field === 'Updated At') {
      this.supplies.sort((a, b) =>
        this.sortMode === 'Desc' ?
          b.updatedAt.getTime() - a.updatedAt.getTime() : a.updatedAt.getTime() - b.updatedAt.getTime());
    }

    if (field === 'Code') {
      this.supplies.sort((a, b) =>
        this.sortMode === 'Desc' ? a.code.localeCompare(b.code) : b.code.localeCompare(a.code));
    }
    if (field === 'Supplier') {
      this.supplies.sort((a, b) =>
        this.sortMode === 'Desc' ? a.supplier.name.localeCompare(b.supplier.name) : b.supplier.name.localeCompare(a.supplier.name));
    }
    if (field === 'Sum') {
      this.supplies.sort((a, b) => this.sortMode === 'Desc' ? b.sum - a.sum : a.sum - b.sum);
    }
    if (field === 'Is Active') {
      this.supplies.sort((a, b) =>
        this.sortMode === 'Desc' ? +b.isActive - +a.isActive : +a.isActive - +b.isActive);
    }
  }

  addNew(): void {
    this.router.navigate(['supply/edit/0']);
  }


}
