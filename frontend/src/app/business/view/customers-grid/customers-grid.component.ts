import {Component, OnInit} from '@angular/core';
import {Customer} from '../../../model/Models';
import {ProductService} from '../../data/dao/impl/product.service';
import {CategoryService} from '../../data/dao/impl/category.service';
import {ActivatedRoute} from '@angular/router';
import {ExchangeDataService} from '../../service/ExchangeDataService';
import {MessageService} from '../../service/message.service';
import {CustomerService} from '../../data/dao/impl/customer.service';
import {DateUtils} from '../../utils/DateUtils';

@Component({
  selector: 'app-customers-grid',
  templateUrl: './customers-grid.component.html',
  styleUrls: ['./customers-grid.component.css']
})
export class CustomersGridComponent implements OnInit {
  customers: Customer[];
  editedCustomer: Customer;

  constructor(public customerService: CustomerService,
              private categoryService: CategoryService,
              private route: ActivatedRoute,
              private readonly exchangeDataService: ExchangeDataService,
              private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.fetchAllCustomers();

    this.exchangeDataService.getUpdateCustomersInGrid()
      .subscribe(update => {
        this.fetchAllCustomers();


        // this.fetchProductsBySelectedCategory();
      });
  }

  fetchAllCustomers(): void {
    this.customerService.findAll()
      .subscribe(customers => {
        console.log('customerService.findAll()');
        this.customers = customers;
      }); // асинхронный вызов
  }

  editCustomer(customerId: number): void {
    const editedCustomer = this.customers.find(c => c.id === customerId);
    if (editedCustomer === undefined) {
      this.messageService.add('Din\'t find customer to edit');
      return;
    }
    this.exchangeDataService.setEditedCustomer(editedCustomer);
  }

  formatDate(date: Date): string {
    date = new Date(date);
    if (!(date instanceof Date && !isNaN(date.getTime()))) {
      throw new Error('Invalid Date object');
    }

    const options: Intl.DateTimeFormatOptions = { day: '2-digit', month: '2-digit', year: 'numeric' };
    const formattedDate = date.toLocaleDateString('en-US', options);

    return formattedDate.replace(/\//g, '.');
  }

}