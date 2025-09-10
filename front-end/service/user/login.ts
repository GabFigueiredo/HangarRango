import api from "@/api/axios";
import { LoginUser, LogInResponse } from "@/types/user/User";

export async function logIn(credentials: LoginUser) {
  const { data } = await api.post<LogInResponse>("/auth/login", credentials);

  return data;
}
