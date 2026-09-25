import { Component, OnInit, OnDestroy } from '@angular/core';
import { AnnotationsService } from '../../ServicesAndHelpers/_services/annotations.service';
import { StorageService } from '../../ServicesAndHelpers/_services/storage.service';
import { Annotation } from '../../Models/annotation';
import { NotesFilterRequest } from '../../Models/notesFilterRequest';
import { Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';

@Component({
  selector: 'app-annotation-allview',
  templateUrl: './annotation-allview.component.html',
  styleUrl: './annotation-allview.component.scss'
})

export class AnnotationAllviewComponent implements OnInit, OnDestroy {

  selectedAnnotation?: Annotation | null;
  annotations: Annotation[] = [];
  isLoading = false;
  isLoggedIn = false;
  errorMessage = '';

  // ─── Pesquisa ──────────────────────────────────────────────────────────────
  searchTerm = '';
  private searchSubject = new Subject<string>();

  // ─── Paginação ─────────────────────────────────────────────────────────────
  currentPage = 0;
  totalPages = 0;
  totalElements = 0;
  pageSize = 10;
  isFirstPage = true;
  isLastPage = true;

  constructor(
    protected annotationService: AnnotationsService,
    protected storageService: StorageService
  ) {}

  ngOnInit(): void {
    if (!this.storageService.isLoggedIn()) {
      this.isLoggedIn = false;
      return;
    }
    this.isLoggedIn = true;

    // Debounce na pesquisa — espera 400ms após a última digitação
    this.searchSubject.pipe(
      debounceTime(400),
      distinctUntilChanged()
    ).subscribe(term => {
      this.searchTerm = term;
      this.currentPage = 0; // volta à primeira página ao pesquisar
      this.loadAnnotations();
    });

    this.loadAnnotations();
  }

  ngOnDestroy(): void {
    this.searchSubject.complete();
  }

  // ─── Carregamento ──────────────────────────────────────────────────────────

  loadAnnotations(): void {
    this.isLoading = true;
    this.errorMessage = '';

    const filter: NotesFilterRequest = {
      searchContent: this.searchTerm.trim() || undefined,
      page: this.currentPage,
      size: this.pageSize,
      orderFor: 'name',
      direction: 'asc'
    };

    this.annotationService.searchAnnotations(filter).subscribe({
      next: response => {
        this.annotations = response.content ?? [];
        this.currentPage = response.actualPage;
        this.totalPages = response.totalOfPages;
        this.totalElements = response.totalElements;
        this.isFirstPage = response.first;
        this.isLastPage = response.last;
        this.isLoading = false;
      },
      error: err => {
        this.errorMessage = err?.error?.error ?? 'Erro ao carregar anotações.';
        this.annotations = [];
        this.isLoading = false;
      }
    });
  }

  // ─── Pesquisa ──────────────────────────────────────────────────────────────

  onSearchInput(term: string): void {
    this.searchSubject.next(term);
  }

  clearSearch(): void {
    this.searchTerm = '';
    this.currentPage = 0;
    this.loadAnnotations();
  }

  // ─── Paginação ─────────────────────────────────────────────────────────────

  goToPage(page: number): void {
    if (page < 0 || page >= this.totalPages) return;
    this.currentPage = page;
    this.loadAnnotations();
  }

  previousPage(): void {
    this.goToPage(this.currentPage - 1);
  }

  nextPage(): void {
    this.goToPage(this.currentPage + 1);
  }

  /**
   * Gera array com os números das páginas visíveis na barra de paginação.
   * Mostra no máximo 5 páginas ao redor da página atual.
   */
  getVisiblePages(): number[] {
    const maxVisible = 5;
    let start = Math.max(0, this.currentPage - Math.floor(maxVisible / 2));
    let end = Math.min(this.totalPages, start + maxVisible);

    if (end - start < maxVisible) {
      start = Math.max(0, end - maxVisible);
    }

    const pages: number[] = [];
    for (let i = start; i < end; i++) {
      pages.push(i);
    }
    return pages;
  }


  hasAnnotations(): boolean {
    return this.annotations && this.annotations.length > 0;
  }

  onSelect(event: any): void {
    this.selectedAnnotation = event;
  }
}

