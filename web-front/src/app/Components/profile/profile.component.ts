import { Component, OnInit } from '@angular/core';
import { StorageService } from '../../ServicesAndHelpers/_services/storage.service';
import { SecurityService } from '../../ServicesAndHelpers/_services/security.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit {

  currentUser: any;
  isLoading: boolean = false;

  // ─── 2FA ──────────────────────────────────────────────────────────────────
  twoFactorEnabled: boolean = false;
  is2FALoading: boolean = false;

  // Controle do formulário de desativação
  isDisabling2FA: boolean = false;
  disable2FAPassword: string = '';

  // Feedback
  twoFASuccessMessage: string = '';
  twoFAErrorMessage: string = '';

  constructor(
    private storage: StorageService,
    private securityService: SecurityService
  ) {}

  ngOnInit(): void {
    this.isLoading = true;
    this.currentUser = this.storage.getUser();
    this.isLoading = false;
    // Nota: o campo twoFactor do usuário não é armazenado no localStorage.
    // O estado real está no backend. A UI reflete o que foi alterado nesta sessão.
  }

  // ─── Ativar 2FA ───────────────────────────────────────────────────────────

  enable2FA(): void {
    this.is2FALoading = true;
    this.twoFASuccessMessage = '';
    this.twoFAErrorMessage = '';

    this.securityService.enable2FA().subscribe({
      next: () => {
        this.is2FALoading = false;
        this.twoFactorEnabled = true;
        this.twoFASuccessMessage = '2FA ativado com sucesso! Sua conta está mais segura.';
      },
      error: err => {
        this.is2FALoading = false;
        this.twoFAErrorMessage = err?.error?.error ?? 'Não foi possível ativar o 2FA. Tente novamente.';
      }
    });
  }

  // ─── Desativar 2FA ────────────────────────────────────────────────────────

  startDisabling2FA(): void {
    this.isDisabling2FA = true;
    this.disable2FAPassword = '';
    this.twoFAErrorMessage = '';
    this.twoFASuccessMessage = '';
  }

  cancelDisabling2FA(): void {
    this.isDisabling2FA = false;
    this.disable2FAPassword = '';
    this.twoFAErrorMessage = '';
  }

  confirmDisable2FA(): void {
    if (!this.disable2FAPassword.trim()) {
      this.twoFAErrorMessage = 'Informe sua senha para confirmar.';
      return;
    }

    this.is2FALoading = true;
    this.twoFAErrorMessage = '';

    this.securityService.disable2FA(this.disable2FAPassword).subscribe({
      next: () => {
        this.is2FALoading = false;
        this.twoFactorEnabled = false;
        this.isDisabling2FA = false;
        this.disable2FAPassword = '';
        this.twoFASuccessMessage = '2FA desativado com sucesso.';
      },
      error: err => {
        this.is2FALoading = false;
        this.twoFAErrorMessage = err?.error?.error ?? 'Senha incorreta ou erro ao desativar o 2FA.';
      }
    });
  }
}

