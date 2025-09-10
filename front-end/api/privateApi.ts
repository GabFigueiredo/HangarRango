// api/private.ts
import axios from "axios";

export const privateApi = axios.create({
  baseURL: "http://localhost:8080",
});

privateApi.interceptors.request.use((config) => {
  if (typeof window !== "undefined") {
    const token = localStorage.getItem("USER_TOKEN");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
  }
  return config;
});

privateApi.interceptors.request.use(async (config) => {
  await new Promise((resolve) => setTimeout(resolve, 2000));

  return config;
});
