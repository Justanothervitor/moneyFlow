import {HttpHeaders} from "@angular/common/http";

export const environment = {
  production: true,
  apiEndPointAuth: '/api/v1/security/auth/',
  apiEndPointOauth: '/api/v1/security/oauth2',
  apiEndPointSecuritySettings: '/api/v1/security/settings',
  apiEndPointSecurityCodeService: '/api/v1/security/services',
  apiEndPointDataNotes: '/api/v1/security/notes',

  httpOptions : {
    'headers': new HttpHeaders({'Content-Type':'application/json','Allow-Origin':'*','Access-Control-Allow-Origin':'true'}),
  }
}
