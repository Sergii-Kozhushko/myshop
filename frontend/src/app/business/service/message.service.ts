import {Injectable, OnInit} from '@angular/core';
import {ExchangeDataService} from './ExchangeDataService';
import {formatDate} from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class MessageService implements OnInit{
  // @ts-ignore
  messages: string[] = [];

  constructor(private exchangeDataService: ExchangeDataService) { }

  ngOnInit(): void {
    this.exchangeDataService.getMessages()
      .subscribe((m) => {
        this.messages = m;
      });
  }

  add(message: string): void {
    const date = formatDate(new Date(), 'dd/MM/yy HH:mm:ss', 'en-US');
    this.messages.push(date + ' ' + message);
    this.exchangeDataService.setMessages(this.messages);
  }
  clear(): void {
    this.messages = [];
    this.exchangeDataService.setMessages(this.messages);
  }


}
