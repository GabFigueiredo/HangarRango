import api from "@/api/axios";
import { PedidoResponse, PreparacaoResponse } from "@/types/order/Pedido";

export async function getAllPreparingOrders() {
  const url = "/cantina/pedidos-pendentes";

  const { data } = await api.get<PedidoResponse[]>(url);

  return data;
}
