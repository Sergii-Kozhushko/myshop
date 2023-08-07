import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';


@Component({
  selector: 'app-delete-supplier',
  templateUrl: './delete-supplier.component.html',
  styleUrls: ['./delete-supplier.component.css']
})
export class DeleteSupplierComponent {


  constructor(public dialogRef: MatDialogRef<DeleteSupplierComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }
}
