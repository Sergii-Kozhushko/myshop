import {Observable} from 'rxjs';


// Стандартные методы CRUD (Create, Read, Update, Delete)
// Все методы возвращают Observable - для асинхронности и работы в реактивном стиле
export interface CommonDAO<T> {

    // получить все значения
    findAll(email: string): Observable<T[]>;

    // получить одно значение по id
    findById(id: number): Observable<T>; // получение значения по уникальному id

    // обновить значение
    update(obj: T): Observable<T>;

    // удалить значение
    delete(id: number): Observable<T>; // удаление по id

    // добавить значение
    add(obj: T): Observable<T>;

}
