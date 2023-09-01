import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'error-info-dialog',
  templateUrl: './error-info-dialog.component.html',
  styleUrls: ['./error-info-dialog.component.css']
})
export class ErrorInfoDialogComponent {

  protected readonly confirm = confirm;
  dialogTitle: string;
  message: string;
  credits: string;

  constructor(
    private dialogRef: MatDialogRef<ErrorInfoDialogComponent>, // для работы с текущим диалог. окном
    @Inject(MAT_DIALOG_DATA) private data: { dialogTitle: string, message: string, credits: string } // данные, которые передали в диалоговое окно
  ) {
    this.dialogTitle = data.dialogTitle; // заголовок
    this.message = data.message; // сообщение
    this.credits = data.credits;
  }

  close(): void {
    this.dialogRef.close();
  }
}
