export const environment = {
  production: true,
  apiUrl: '/api',
  keycloak: {
    // Url of the Identity Provider
    issuer: 'http://localhost:8080',
    // Realm
    realm: 'mancala',
    clientId: 'mancala_web_ui'
  },
  baseUrl: 'http://localhost:4200'
};
