import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

const OAUTH_AUTHORIZE_URI = '/api/v2/oauth2/authorize/google';
const OAUTH_USER_END = environment.apiEndPointOauth;

@Injectable({
  providedIn: 'root'
})
export class OauthService {

  constructor(private http: HttpClient) { }

  /**
   * Inicia o fluxo OAuth2 com o Google redirecionando o browser
   * para o endpoint de autorização do backend Spring Security.
   * O backend assume o handshake com o Google e, após autenticação,
   * redireciona de volta para /oauth2/redirect?token=<JWT>.
   */
  redirectToGoogleLogin(): void {
    window.location.href = OAUTH_AUTHORIZE_URI;
  }

  /**
   * Busca os dados do usuário autenticado via OAuth2 (Google).
   * Requer o token JWT no header Authorization.
   */
  fetchUserInfo(token: string): Observable<{ id: string; email: string; name: string }> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<{ id: string; email: string; name: string }>(
      OAUTH_USER_END + '/user',
      { headers }
    );
  }
}
