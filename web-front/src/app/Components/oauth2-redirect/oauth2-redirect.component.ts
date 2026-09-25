import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OauthService } from '../../ServicesAndHelpers/_services/oauth.service';
import { StorageService } from '../../ServicesAndHelpers/_services/storage.service';
import { User } from '../../Models/user';

@Component({
  selector: 'app-oauth2-redirect',
  templateUrl: './oauth2-redirect.component.html',
  styleUrl: './oauth2-redirect.component.scss'
})
export class OAuth2RedirectComponent implements OnInit {

  errorMessage = '';
  isError = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private oauthService: OauthService,
    private storage: StorageService
  ) {}

  ngOnInit(): void {
    // Caso o usuário já esteja logado, redireciona direto para home
    if (this.storage.isLoggedIn()) {
      this.router.navigate(['/home']);
      return;
    }

    // Captura o token JWT que o backend enviou como query param (?token=...)
    const token = this.route.snapshot.queryParamMap.get('token');

    if (!token) {
      this.isError = true;
      this.errorMessage = 'Token de autenticação não encontrado. Tente novamente.';
      return;
    }

    // Busca os dados do usuário na API usando o token recebido
    this.oauthService.fetchUserInfo(token).subscribe({
      next: (data) => {
        const userData: User = {
          username: data.name,
          email: data.email,
        };
        const authData = { token };

        this.storage.saveUser(userData, authData);
        this.router.navigate(['/home']);
      },
      error: (err) => {
        this.isError = true;
        this.errorMessage = err?.error?.message ?? 'Falha ao autenticar com o Google. Tente novamente.';
      }
    });
  }
}
