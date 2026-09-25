import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

const SETTINGS_END = environment.apiEndPointSecuritySettings;
const TWO_FA_END = '/api/v1/security/code/2fa';

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  constructor(private http: HttpClient) { }

  // ─── Recuperação de Senha ────────────────────────────────────────────────────

  /**
   * Solicita o envio de código de recuperação de senha ao email do usuário.
   * Backend: POST /api/v1/security/settings/password/forgot
   * Payload: { email, type: "PASSWORD_RESET" }
   */
  forgotPassword(email: string): Observable<any> {
    return this.http.post(SETTINGS_END + '/password/forgot', {
      email,
      type: 'PASSWORD_RESET'
    });
  }

  /**
   * Redefine a senha usando o código recebido por email.
   * Backend: POST /api/v1/security/settings/password/reset
   * Payload: { email, code, passwordForChange }
   */
  resetPassword(email: string, code: string, passwordForChange: string): Observable<any> {
    return this.http.post(SETTINGS_END + '/password/reset', {
      email,
      code,
      passwordForChange
    });
  }

  // ─── Autenticação em Dois Fatores (2FA) ──────────────────────────────────────

  /**
   * Ativa o 2FA para o usuário autenticado.
   * Backend: POST /api/v1/security/code/2fa/enable
   * Requer token JWT no header (via interceptor).
   */
  enable2FA(): Observable<any> {
    return this.http.post(TWO_FA_END + '/enable', {});
  }

  /**
   * Desativa o 2FA. Requer confirmação da senha atual.
   * Backend: POST /api/v1/security/code/2fa/disable?password=<senha>
   * Requer token JWT no header (via interceptor).
   */
  disable2FA(password: string): Observable<any> {
    const params = new HttpParams().set('password', password);
    return this.http.post(TWO_FA_END + '/disable', {}, { params });
  }

  /**
   * Envia o código 2FA para o email do usuário (usado no fluxo de login 2FA).
   * Backend: POST /api/v1/security/code/2fa/send-code
   * Payload: { email, type: "TWO_FACTOR_VERIFICATION" }
   */
  send2FACode(email: string): Observable<any> {
    return this.http.post(TWO_FA_END + '/send-code', {
      email,
      type: 'TWO_FACTOR_VERIFICATION'
    });
  }

  /**
   * Verifica o código 2FA e retorna o JWT de autenticação.
   * Backend: POST /api/v1/security/code/2fa/verify
   * Payload: { email, code }
   * Retorno: { token, username, email }
   */
  verify2FACode(email: string, code: string): Observable<any> {
    return this.http.post(TWO_FA_END + '/verify', { email, code });
  }
}

