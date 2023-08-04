import {Component, OnInit, ViewChild, ElementRef} from '@angular/core';
import {Category, Customer, Product} from '../../../model/Models';
import {ExchangeDataService} from '../../service/exchange.data.service';
import {ProductService} from '../../data/dao/impl/product.service';
import {Router} from '@angular/router';
import {MessageService} from '../../service/message.service';
import {CustomerService} from '../../data/dao/impl/customer.service';

@Component({
  selector: 'app-customer-edit',
  templateUrl: './customer-edit.component.html',
  styleUrls: ['./customer-edit.component.css']
})
export class CustomerEditComponent implements OnInit {
  public editedCustomer: Customer;
  customers: Customer[];
  @ViewChild('customerBirthInput', {static: true}) customerBirthInput: ElementRef<HTMLInputElement>;


  constructor(private exchangeDataService: ExchangeDataService,
              private customerService: CustomerService,
              private messageService: MessageService) {
    this.clearEditedCustomer();
  }

  ngOnInit(): void {
    this.exchangeDataService.getEditedCustomer()
      .subscribe((customer) => {
        console.log('edited customer subscribe worked');
        console.log(customer);
        this.editedCustomer = customer;
      });
    // this.exchangeDataService.getCategories()
    //   .subscribe((categories) => {
    //     console.log('cat length=' + categories.length);
    //     this.categories = categories;
    //   });

  }

  clearEditedCustomer(): void {
    this.editedCustomer = {
      id: 0,
      name: '',
      email: '',
      phone: '',
      address: '',
      discountValue: 0,
      discountCardNumber: '',
      acceptSMSList: false
    };
  }

  addNew(): void {
    if (!this.editedCustomer.name.trim()) {
      this.messageService.add('Can\'t add product with empty name');

      return;
    }

    this.customerService.add(this.editedCustomer).subscribe(result => {
    });
    this.messageService.add(`Customer '${this.editedCustomer.name}' added successfully`);
    this.clearEditedCustomer();
    this.exchangeDataService.setUpdateCustomersInGrid();

  }

  save(): void {
    const birthDateValue = this.customerBirthInput.nativeElement.value;
    console.log('birth=' + birthDateValue);
    // Здесь вы можете выполнить необходимые операции с полученным значением

    // Пример: Преобразование строки в объект Date
    const [day, month, year] = birthDateValue.split('.');
    this.editedCustomer.dateBirth = new Date(`${year}-${month}-${day}`);


    this.customerService.update(this.editedCustomer);
    this.messageService.add(`Updated product '${this.editedCustomer.name}'`);
    this.clearEditedCustomer();
    // this.exchangeDataService.setEditedProduct(this.editedProduct);
    this.exchangeDataService.setUpdateCustomersInGrid();
  }

  delete(): void {

  }

  cancel(): void {
    this.clearEditedCustomer();
    this.exchangeDataService.setEditedCustomer(this.editedCustomer);
  }

}
