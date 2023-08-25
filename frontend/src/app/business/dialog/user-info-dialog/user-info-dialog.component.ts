import {Component, OnInit} from '@angular/core';
import {KeycloakService} from '../../../oauth2/bff/keycloak.service';
import {keyframes} from '@angular/animations';
import {User} from '../../../model/User';
import {MatDialogRef} from '@angular/material/dialog';
import {AboutDialogComponent} from '../about-dialog/about-dialog.component';

@Component({
  selector: 'app-user-info-dialog',
  templateUrl: './user-info-dialog.component.html',
  styleUrls: ['./user-info-dialog.component.css']
})
export class UserInfoDialogComponent implements OnInit {
  user: User;

  constructor(
    private dialogRef: MatDialogRef<AboutDialogComponent>,
    private keycloakService: KeycloakService) {
  }

  ngOnInit(): void {
    this.keycloakService.requestUserProfile().subscribe(up => {
        this.user = up;
        // console.log('user=' + this.user.email);
      }
    );

  }

  close(): void {
    this.dialogRef.close();
  }


}
