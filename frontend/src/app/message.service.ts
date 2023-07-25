import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  // @ts-ignore
  messages: string[] = [];

  add(message: string): void {
    this.messages.push(message);
  }
  clear(): void {
    this.messages = [];
  }

  constructor() { }
}
