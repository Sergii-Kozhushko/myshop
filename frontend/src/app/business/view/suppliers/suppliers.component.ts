import {Component, OnInit} from '@angular/core';
import {Sale, Supply, Supplier} from '../../../model/Models';
import {SupplyService} from '../../data/dao/impl/Supply.service';
import {ExchangeDataService} from '../../service/exchange.data.service';
import {MessageService} from '../../service/message.service';
import {Router} from '@angular/router';
import {SupplierService} from '../../data/dao/impl/supplier.service';
import {SpinnerService} from '../../../oauth2/spinner/spinner.service';

@Component({
  selector: 'app-suppliers',
  templateUrl: './suppliers.component.html',
  styleUrls: ['./suppliers.component.css']
})
export class SuppliersComponent implements OnInit {
  documents: Supplier[];
  sortField = 'Name';
  sortMode = 'Desc';
  editedDocument: Supplier;
  addMode: boolean;

  constructor(
    private supplierService: SupplierService,
    private readonly exchangeDataService: ExchangeDataService,
    private messageService: MessageService,
    private router: Router,
    private spinnerService: SpinnerService) {
  }

  ngOnInit(): void {
    this.addItemInit();

    this.exchangeDataService.getSuppliers()
      .subscribe(list => {
          this.documents = list.filter(i => i.active);
        }
      );

    if (this.sortMode === 'Name') {
      this.documents.sort((a, b) =>
        this.sortMode === 'Desc' ?
          b.createdAt.getTime() - a.createdAt.getTime()
          : a.createdAt.getTime() - b.createdAt.getTime());
    }
  }

  setNextSupplierId(): void {
    this.supplierService.findMaxSupplierId()
      .subscribe(n => {
        this.editedDocument.id = n + 1;
      });
  }


  changeSortMode(field: string): void {
    // this.supplies.forEach(invoice => console.log(invoice.createdAt));
    this.sortField = field;
    this.sortMode = this.sortMode === 'Desc' ? 'Asc' : 'Desc';
    if (field === 'Id') {
      this.documents.sort((a, b) =>
        this.sortMode === 'Desc' ? b.id - a.id : a.id - b.id);
    }

    if (field === 'Name') {
      this.documents.sort((a, b) =>
        this.sortMode === 'Desc' ? a.name.localeCompare(b.name) : b.name.localeCompare(a.name));
    }

  }

  addItemInit(): void {
    this.addMode = true;
    this.editedDocument = new Supplier();
    this.setNextSupplierId();
  }

  save(): void {
    this.spinnerService.show();
    setTimeout(() => {
      // Make delay for realistic performance
      this.spinnerService.hide();
      if (this.addMode) {
        this.supplierService.add(this.editedDocument)
          .subscribe(s => {
              this.messageService.add(`Added new Supplier '${this.editedDocument.name}, id #${s.id}`);
              this.supplierService.refreshSuppliersList();
            }
          );
      } else {
        this.supplierService.update(this.editedDocument).subscribe(s => {
          this.messageService.add(`Updated Supplier '${s.name}'`);
          this.supplierService.refreshSuppliersList();
        });
      }
      this.addItemInit();
    }, 2000);


  }

  delete(): void {
    this.editedDocument.active = false;
    this.spinnerService.show();

    setTimeout(() => {
      // Make delay for realistic performance
      this.spinnerService.hide();
      this.supplierService.update(this.editedDocument).subscribe(s => {
          this.messageService.add(`Soft deleted supplier # ${s.id} ${s.name}`);
          this.supplierService.refreshSuppliersList();
          this.addItemInit();
        }
      );
    }, 2000);

  }

  cancel(): void {
    this.addItemInit();
  }

  editItem(sid: number): void {
    this.editedDocument = this.documents.find(i => i.id === sid);
    this.addMode = false;
  }

}
