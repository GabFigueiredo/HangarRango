import { privateApi } from "@/api/privateApi";
import { ProdutoType } from "@/types/Produto";

export async function getAllProducts() {
  const url = "/admin/produto";

  const { data } = await privateApi.get<ProdutoType[]>(url);

  return data;
}
