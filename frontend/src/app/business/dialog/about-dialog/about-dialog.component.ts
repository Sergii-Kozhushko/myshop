import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-about-dialog',
  templateUrl: './about-dialog.component.html',
  styleUrls: ['./about-dialog.component.css']
})
export class AboutDialogComponent {

  protected readonly confirm = confirm;
  dialogTitle: string;
  message: string;
  credits: string;

  constructor(
    private dialogRef: MatDialogRef<AboutDialogComponent>, // для работы с текущим диалог. окном
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
