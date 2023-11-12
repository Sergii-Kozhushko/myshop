import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from '../../../environments/environment';

/*

User authentication page - without logging in, the user won't be able to obtain tokens and make requests to RS
All requests are sent to the BFF server, which acts as an adapter between the client and the Resource Server

*/

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(
    private router: Router, // Navigation
    private activatedRoute: ActivatedRoute, // Current applied route
    private http: HttpClient, // HTTP requests
  ) {
  }

  ngOnInit(): void {

    this.activatedRoute.queryParams.subscribe(params => {

      // Entering the login component can happen in two cases:
      // 1) If we successfully authenticated and received a response from KC
      // 2) If we simply opened the page in the browser and were redirected to login because we're not authenticated

      // More about both cases:
      // 1) If the 'code' parameter is present in the query string, it means we successfully authenticated and
      // received a response from KC (because we specified the current page as the redirect_uri)
      // Now we can exchange this code for tokens
      if (params.code) {

        // PKCE parameters
        const code = params.code;
        const state = params.state;

        // We only need these parameters once for the initial request
        // After using them, we immediately clear the URL query parameters,
        // so they won't be sent again if the page is refreshed, and they won't be displayed in the browser's address bar
        window.history.pushState({}, '', document.location.href.split('?')[0]);

        // Request to BFF, which will exchange the code for tokens
        // The BFF response will include tokens (Set-Cookie header), which will be stored in the browser's cookies
        this.requestTokens(code, state);

        return; // We must exit the method to prevent further code execution

      }

      // 2) If none of the above conditions are met, it means this is not a response from KC, but a regular request
      this.showAuthWindow(); // Display the authentication window, which will restart the entire PKCE flow to obtain tokens

    });

  }

  private showAuthWindow(): void {

    // Mandatory parameters for PKCE
    const state = this.randomString(40);
    localStorage.setItem('state', state);

    // Prepare parameters for the request to KC to obtain the Authorization Code, which will then be exchanged for tokens
    const params = [
      'response_type=code', // This code will later be exchanged for tokens in the second step
      'state=' + state, // Client protection - the response from the auth server must match the client's request
      'client_id=' + environment.kcClientID, // From KeyCloak settings
      'scope=' + environment.scopes, // What data we want to receive from the auth server (which tokens and so on)
      'redirect_uri=' + encodeURIComponent(environment.redirectURI), // We'll receive the response on the client, which will then send this code to the BFF to exchange it for tokens
    ];

    // Final URL with parameters
    const url = environment.kcBaseURL + '/auth' + '?' + params.join('&');

    // The authentication window will open in the same window (not in a new browser tab)
    window.open(url, '_self'); // 'self' means in the same window
  }

  private randomString(length: number): string {
    let result = '';
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    const charactersLength = characters.length;

    for (let i = 0; i < length; i++) {
      result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }

    return result;
  }

  private requestTokens(code: string, state: string): void {

    if (!this.checkState(state)) {
      return; // Exit if the state check fails
    }
    console.log('BFF URI=' + environment.bffURI);
    this.http.post(environment.bffURI + '/token', code, {
      headers: {
        'Content-Type': 'application/json; charset=UTF-8' // Specify the content type
      }
    }).subscribe({
      next: ((response: any) => {

        // If the request for tokens to the BFF was successful,
        // the tokens will be stored in secure cookies and will be automatically sent with each request to the BFF

        // Now we can navigate to the page to request user data
        this.router.navigate(['main']);
      }),

      error: (error => {
        console.log(error);
      })
    });

  }

  private checkState(state: string): boolean {

    // If the state received from the auth server matches the previously saved value,
    // it means the response came from our request
    if (state !== localStorage.getItem('state') as string) {
      console.log('Invalid state');
      return false; // Exit and don't execute any further code (the states must match)
    }

    // Remove the saved state, as it's no longer needed
    localStorage.removeItem('state');
    return true;
  }

}
