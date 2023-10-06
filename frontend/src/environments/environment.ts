// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

// global constants PROD
// export const environment = {
//   production: false,
//   resourceServerURL: 'http://backend:8082', // root backend URL
//   frontendURL: 'https://myshopapp.kyiv.ua:4200', // root URL frontend
//   kcClientID: 'myshopapp-client', // KeyCloak client property
//   kcBaseURL: 'https://myshopapp.kyiv.ua:8443/realms/myshop-realm/protocol/openid-connect',
//   bffURI: 'https://myshopapp.kyiv.ua:8902/bff',
//   redirectURI: 'https://myshopapp.kyiv.ua:4200',
//   scopes: 'openid phone',
//   devMode: true
// };

// global constants DEV
export const environment = {
  production: false,
  resourceServerURL: 'http://localhost:8082', // root backend URL
  frontendURL: 'https://localhost:4200', // root URL frontend
  kcClientID: 'myshopapp-client', // KeyCloak client property
  kcBaseURL: 'https://localhost:8443/realms/myshop-realm/protocol/openid-connect',
  bffURI: 'https://localhost:8902/bff',
  redirectURI: 'https://localhost:4200',
  scopes: 'openid phone',
  devMode: true
};




/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
