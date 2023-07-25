import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

/*
Методы переключатели вкл/выкл с помощью BehaviorSubject
В нашем проекте используется для отображения индикатора "спиннер загрузки" (показать/скрыть)
*/


@Injectable({
  providedIn: 'root'
})
export class SpinnerService {

  // BehaviorSubject - это специальный тип из "реактивного кода" TypeScript
  // Позволяет подписчикам "слушать" новые значения и реагировать на них
  // https://www.learnrxjs.io/learn-rxjs/subjects/behaviorsubject
  // В нашем случае - на значение visibility подписана спец. переменная, которая скрывает/показывает индикатор загрузки на HTML
  visibility = new BehaviorSubject(false);

  show(): void {
    this.visibility.next(true); // передаем подписчикам значение "true"
  }

  hide(): void {
    this.visibility.next(false); // передаем подписчикам значение "false"
  }
}
