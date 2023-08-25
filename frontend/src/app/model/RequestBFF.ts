
/*
Object for requests from the client to BFF - contains information about the operation that BFF should perform
All fields must match exactly with the corresponding POJO in the backend for automatic JSON conversion and back.
The names, sequence, and types of fields must match.
*/
export class Operation {
  httpMethod: HttpMethod; // method type for the call
  url: string; // the address that BFF will invoke on the Resource Server
  body: any; // request payload (automatically converted to JSON)
}


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
