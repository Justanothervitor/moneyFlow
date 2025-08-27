export interface LoginResponse{
  type: string;
  token: string;
  id: string;
  username: string;
  email: string;
  role : string[];
}
