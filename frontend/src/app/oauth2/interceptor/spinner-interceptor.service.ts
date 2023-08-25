import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { SpinnerService } from '../spinner/spinner.service';

/*
An interceptor is similar to filters in a web application, where you can modify the request before sending it.
The interceptor must be registered in the app.module.ts file, otherwise it won't work.

This interceptor intercepts all HTTP requests to show a "loading spinner" indicator (until the request is completed).

*/

@Injectable()
export class SpinnerInterceptor implements HttpInterceptor {

  constructor(
    private spinnerService: SpinnerService // Toggler to show/hide the spinner
  ) {
  }

  // This method is automatically called for each outgoing request
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    this.spinnerService.show(); // Display the spinner initially

    // https://rxjs.dev/api/index/function/tap
    // https://rxjs.dev/api/index/function/pipe

    // Reaction to each request's execution: showing and hiding the loading indicator
    return next.handle(req)
      .pipe(
        tap({

          // Successful execution
          next:
            (event: HttpEvent<any>) => {
              if (event instanceof HttpResponse) { // Response received - request is completed
                this.spinnerService.hide(); // Hide the spinner when the request is done
              }
            },

          // Error during execution
          error: (error) => {
            console.log(error);
            this.spinnerService.hide(); // If an error occurs, hide the spinner
          }

        })
      );
  }
}
