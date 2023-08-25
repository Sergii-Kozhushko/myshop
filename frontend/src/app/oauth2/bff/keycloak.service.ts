import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { User } from '../../model/User';
import { HttpMethod, Operation } from '../../model/RequestBFF';
import { Router } from '@angular/router';

// Reminder: All requests are sent not directly to the Resource Server, but to the BFF (Backend For Frontend), which acts as an adapter.
// This is done for secure cookie storage in the browser.

@Injectable({
  providedIn: 'root'
})

export class KeycloakService {

  constructor(private http: HttpClient, private router: Router) {
  }

  // Log out from the system
  logoutAction(): Observable<any> {
    // Simply call the URL and do not return anything
    return this.http.get(environment.bffURI + '/logout');
  }

  // Obtain new tokens using the old Refresh Token (from the cookie)
  exchangeRefreshToken(): Observable<any> {
    return this.http.get(environment.bffURI + '/exchange');
  }

  // Request user data (profile)
  requestUserProfile(): Observable<User> {
    return this.http.get<User>(environment.bffURI + '/profile');
  }

}
