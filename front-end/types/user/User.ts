export interface LogInUser {
  email: string;
  senha: string;
}

export interface User {
  nome: string;
  email: string;
}

export interface LogInResponse {
  usuario: User;
  token: string;
}
