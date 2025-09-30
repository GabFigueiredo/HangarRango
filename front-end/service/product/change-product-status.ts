import api from "@/api/axios";
import { ProdutoStatus, ProdutoType } from "@/types/Produto";
import { toast } from "sonner";

export async function ChangeProductStatus(newStatus: ProdutoStatus) {
  const url = "/admin/produto";

  const response = await api.patch<ProdutoType>(url, newStatus);

  if (response.status === 200) {
    toast.success("Status alterado com sucesso!");
    return;
  }

  toast.error("Ocorreu um erro ao alterar o status..");
}
