import api from "@/api/axios";
import { PedidoResponse, StatusPagamentoRequest } from "@/types/order/Pedido";
import { toast } from "sonner";

export async function ChangePaymentStatus(newStatus: StatusPagamentoRequest) {
  const url = "/cantina/pagamento";

  const response = await api.patch<PedidoResponse>(url, newStatus);

  if (response.status === 200) {
    toast.success("Status alterado com sucesso!");
    return response.data;
  }

  toast.error("Ocorreu um erro ao alterar o status..");
}
