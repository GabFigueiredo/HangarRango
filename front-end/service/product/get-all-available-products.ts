import api from "@/api/axios";
import { ProdutoType } from "@/types/Produto";

export async function GetAllAvailableProducts() {
  const url = "/produto/produtos-disponiveis";

  const { data } = await api.get<ProdutoType[]>(url);

  return data;
}
