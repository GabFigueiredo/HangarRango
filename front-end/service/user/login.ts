import api from "@/api/axios";
import { LogInUser, LogInResponse } from "@/types/user/User";

export async function logIn(credentials: LogInUser) {
  const { data } = await api.post<LogInResponse>("/auth/login", credentials);

  return data;
}
