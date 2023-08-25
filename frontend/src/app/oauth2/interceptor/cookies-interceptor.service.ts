import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';

/*

An interceptor is similar to filters in a web application, where you can modify the request before sending it.
The interceptor must be registered in the app.module.ts file, otherwise it won't work.

For all outgoing requests from Angular to the BFF, headers need to be added in order to:
- Attach cookies (containing tokens) to the request in the browser
- Ensure the BFF processes the request correctly

*/
@Injectable()
export class CookiesInterceptor implements HttpInterceptor {

  constructor() {}

  // This method is automatically called for each outgoing request
  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    request = request.clone({

      // Headers to ensure proper BFF processing
      setHeaders: {
        'Content-Type': 'application/json; charset=utf-8',
        'Access-Control-Allow-Headers': '*',
        'Access-Control-Allow-Methods': '*',
        'Access-Control-Allow-Origin': '*'
      },
      // Attach cookies (containing tokens) in the browser
      withCredentials: true,
    });

    return next.handle(request); // Send the modified request further
  }
}
