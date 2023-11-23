export const environment = {
  production: false,
  apiUrl: '/api',
  keycloak: {
    // Url of the Identity Provider
    issuer: 'http://localhost:8080',
    // Realm
    realm: 'mancala',
    clientId: 'mancala_web_ui'
  },
  baseUrl: 'http://localhost:4200',
  websocketUrl: 'http://localhost:8082/socket',
};
