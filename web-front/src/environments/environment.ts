import {HttpHeaders} from "@angular/common/http";

export const environment = {
  production : false,
  apiEndPointAuth: '/api/v1/security/auth/',
  // Endpoint da API OAuth2 — alinhado com o backend (/api/v2/oauth2)
  apiEndPointOauth: '/api/v2/oauth2',
  // URI de autorização Google — o backend Spring Security gerencia o handshake
  oauthGoogleAuthorizeUri: '/api/v2/oauth2/authorize/google',
  apiEndPointSecuritySettings: '/api/v1/security/settings',
  apiEndPointSecurityCodeService: '/api/v1/security/services',
  apiEndPointDataNotes: '/api/v1/security/notes',

  httpOptions : {
    'headers': new HttpHeaders({'Content-Type':'application/json','Allow-Origin':'*','Access-Control-Allow-Origin':'true'}),
  }
};
