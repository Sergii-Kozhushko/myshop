import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

/*
Methods to toggle on/off using BehaviorSubject
Used in our project to display the "loading spinner" indicator (show/hide)
*/

@Injectable({
  providedIn: 'root'
})
export class SpinnerService {

  // BehaviorSubject is a special type from TypeScript's "reactive code"
  // It allows subscribers to "listen" to new values and react to them
  // https://www.learnrxjs.io/learn-rxjs/subjects/behaviorsubject
  // In our case, the visibility variable is subscribed to the value, which hides/shows the loading spinner on the HTML
  visibility = new BehaviorSubject(false);

  show(): void {
    this.visibility.next(true); // Send the value "true" to subscribers
  }

  hide(): void {
    this.visibility.next(false); // Send the value "false" to subscribers
  }
}
