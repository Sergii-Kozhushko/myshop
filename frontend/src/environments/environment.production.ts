export const environment = {
  production: true,
  resourceServerURL: 'http://backend:8082', // root backend URL
  frontendURL: 'https://myshopapp.kyiv.ua:4200', // root URL frontend
  kcClientID: 'myshopapp-client', // KeyCloak client
  kcBaseURL: 'https://myshopapp.kyiv.ua:8443/realms/myshop-realm/protocol/openid-connect',
  bffURI: 'https://myshopapp.kyiv.ua:8902/bff',
  redirectURI: 'https://myshopapp.kyiv.ua:4200',
  scopes: 'openid phone',
  enableLogging: false
};
