// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

// глобальные константы для периода разработки приложения (не боевого)
export const environment = {
  production: false,
  resourceServerURL: 'https://212.32.255.215:8081', // ссылка на корневой URL бэкенда
  frontendURL: 'https://212.32.255.215:4200', // ссылка на корневой URL фронтэнда
  kcClientID: 'myshopapp-client', // из настроек KeyCloak
  kcBaseURL: 'https://212.32.255.215:8443/realms/myshop-realm/protocol/openid-connect',
  bffURI: 'https://212.32.255.215:8902/bff',
  redirectURI: 'https://212.32.255.215:4200',
  scopes: 'openid',
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
