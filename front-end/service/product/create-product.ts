import { privateApi } from "@/api/privateApi";
import { ProdutoType } from "@/types/Produto";

export async function CreateProduct(product: ProdutoType) {
  const url = "/admin/produto";

  const { data } = await privateApi.post<ProdutoType>(url, product);

  return data;
}
