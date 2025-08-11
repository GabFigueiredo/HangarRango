import api from "@/api/axios";
import { PedidoResumo } from "@/types/order/PedidoResumo";

export async function getSummary() {
  const url = "/cantina/pedido/resumo";

  const resumo = await api.get<PedidoResumo>(url);

  return resumo;
}
