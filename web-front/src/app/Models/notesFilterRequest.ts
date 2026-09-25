/**
 * Payload para pesquisa paginada de anotações.
 * Espelha o NotesFilterRequest.java do backend.
 */
export interface NotesFilterRequest {
  searchContent?: string;
  categoryId?: number;
  minPrice?: number;
  maxPrice?: number;
  page: number;
  size: number;
  orderFor?: string;
  direction?: string;
}
