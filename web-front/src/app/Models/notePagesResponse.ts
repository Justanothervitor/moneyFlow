/**
 * Resposta paginada da API de pesquisa de notas.
 * Espelha o NotePagesResponse.java do backend.
 */
export interface NotePagesResponse<T> {
  content: T[];
  actualPage: number;
  totalOfPages: number;
  totalElements: number;
  sizeOfPage: number;
  first: boolean;
  last: boolean;
  isNull: boolean;
  appliedFilters: Record<string, any>;
}
