
/*
Объект для запросов от клиента в BFF - содержит информацию, какую операцию должен выполнить BFF
Все поля должны совпадать один в один с таким же POJO из backend, чтобы работала автоматическая конвертация в JSON и обратно.
Названия, последовательность и типы полей - должны совпадать.
*/
export class Operation {
  httpMethod: HttpMethod; // тип метода для вызова
  url: string; // какой адрес BFF будет вызывать у Resource Server
  body: any; // вложения запроса (конвертируется автоматически в JSON)
}



/*
Используется для того, чтобы указать BFF какой тип метода вызывать в Resource Server

В Java кстати существует соотв. класс для этих же целей
public enum HttpMethod {
    GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;
}

 */

export enum HttpMethod {
  GET,
  HEAD,
  POST,
  PUT,
  PATCH,
  DELETE,
  OPTIONS,
  TRACE
}
