import api from "@/api/axios";
import { ProdutoType } from "@/types/Produto";

export async function CreateProduct(product: ProdutoType) {
  const url = "/cantina/produto";

  const { data } = await api.post<ProdutoType>(url, product);

  return data;
}
