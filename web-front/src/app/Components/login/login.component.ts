import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../ServicesAndHelpers/_services/auth.service';
import { StorageService } from '../../ServicesAndHelpers/_services/storage.service';
import { OauthService } from '../../ServicesAndHelpers/_services/oauth.service';
import { SecurityService } from '../../ServicesAndHelpers/_services/security.service';
import { formLogin } from '../../Models/formLogin';
import { User } from '../../Models/user';

/**
 * Steps de recuperação de senha:
 * 0 = formulário de login (padrão)
 * 1 = digitar email para receber o código
 * 2 = digitar código recebido + nova senha
 */
type LoginStep = 0 | 1 | 2;

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {

  // ─── Login ─────────────────────────────────────────────────────────────────
  form: formLogin = { username: '', password: '' };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  user: User = { username: '', email: '' };

  // ─── Recuperação de Senha ──────────────────────────────────────────────────
  step: LoginStep = 0;
  forgotEmail = '';
  resetCode = '';
  newPassword = '';
  confirmPassword = '';

  // Estados de feedback
  isSendingCode = false;
  codeSentSuccess = false;
  isResetting = false;
  resetSuccess = false;
  forgotErrorMessage = '';
  resetErrorMessage = '';
  resetSuccessMessage = '';

  constructor(
    protected authenticator: AuthService,
    protected storage: StorageService,
    protected oauthService: OauthService,
    protected securityService: SecurityService
  ) {}

  ngOnInit(): void {
    if (this.storage.isLoggedIn()) {
      this.isLoggedIn = true;
      this.user = this.storage.getUser();
    }
  }

  // ─── Login ─────────────────────────────────────────────────────────────────

  onSubmit(): void {
    this.authenticator.login(this.form).subscribe({
      next: data => {
        if (data) {
          const userData: User = { username: data?.username, email: data?.email };
          this.storage.saveUser(userData, { token: data?.token });
          this.isLoginFailed = false;
          this.isLoggedIn = true;
          this.reloadPage();
        }
      },
      error: err => {
        this.errorMessage = err?.error?.error ?? err?.error?.message ?? 'Credenciais inválidas.';
        this.isLoginFailed = true;
      }
    });
  }

  loginWithGoogle(): void {
    this.oauthService.redirectToGoogleLogin();
  }

  reloadPage(): void {
    window.location.reload();
  }

  // ─── Recuperação de Senha — Navegação ──────────────────────────────────────

  goToForgotPassword(): void {
    this.step = 1;
    this.forgotEmail = '';
    this.forgotErrorMessage = '';
    this.codeSentSuccess = false;
  }

  goToResetStep(): void {
    this.step = 2;
    this.resetCode = '';
    this.newPassword = '';
    this.confirmPassword = '';
    this.resetErrorMessage = '';
  }

  backToLogin(): void {
    this.step = 0;
    this.forgotEmail = '';
    this.resetCode = '';
    this.newPassword = '';
    this.confirmPassword = '';
    this.forgotErrorMessage = '';
    this.resetErrorMessage = '';
    this.codeSentSuccess = false;
    this.resetSuccess = false;
    this.resetSuccessMessage = '';
  }

  // ─── Recuperação de Senha — Step 1: Enviar código ──────────────────────────

  sendForgotPasswordCode(): void {
    if (!this.forgotEmail.trim()) {
      this.forgotErrorMessage = 'Informe o email cadastrado.';
      return;
    }
    this.isSendingCode = true;
    this.forgotErrorMessage = '';
    this.codeSentSuccess = false;

    this.securityService.forgotPassword(this.forgotEmail.trim()).subscribe({
      next: () => {
        this.isSendingCode = false;
        this.codeSentSuccess = true;
      },
      error: err => {
        this.isSendingCode = false;
        this.forgotErrorMessage = err?.error?.error ?? 'Não foi possível enviar o código. Verifique o email informado.';
      }
    });
  }

  // ─── Recuperação de Senha — Step 2: Redefinir senha ────────────────────────

  submitResetPassword(): void {
    this.resetErrorMessage = '';

    if (!this.resetCode.trim()) {
      this.resetErrorMessage = 'Informe o código recebido por email.';
      return;
    }
    if (this.newPassword.length < 6) {
      this.resetErrorMessage = 'A nova senha deve ter pelo menos 6 caracteres.';
      return;
    }
    if (this.newPassword !== this.confirmPassword) {
      this.resetErrorMessage = 'As senhas não coincidem.';
      return;
    }

    this.isResetting = true;

    this.securityService.resetPassword(
      this.forgotEmail.trim(),
      this.resetCode.trim(),
      this.newPassword
    ).subscribe({
      next: () => {
        this.isResetting = false;
        this.resetSuccess = true;
        this.resetSuccessMessage = 'Senha redefinida com sucesso! Faça login com sua nova senha.';
        setTimeout(() => this.backToLogin(), 3000);
      },
      error: err => {
        this.isResetting = false;
        this.resetErrorMessage = err?.error?.error ?? 'Código inválido ou expirado. Tente novamente.';
      }
    });
  }
}
