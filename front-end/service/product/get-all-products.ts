import api from "@/api/axios";
import { ProdutoType } from "@/types/Produto";

export async function getAllProducts() {
  const url = "/admin/produto";

  const { data } = await api.get<ProdutoType[]>(url);

  return data;
}
