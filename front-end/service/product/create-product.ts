import api from "@/api/axios";
import { ProdutoType } from "@/types/Produto";

export async function CreateProduct(product: ProdutoType) {
  const url = "/admin/produto";

  const { data } = await api.post<ProdutoType>(url, product);

  return data;
}
