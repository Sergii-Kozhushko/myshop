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
