import api from "@/api/axios";
import { PedidoResponse, PedidoRequest } from "@/types/order/Pedido";

export async function CreateOrder(order: PedidoRequest) {
  const url = "/pedido";

  const { data } = await api.post<PedidoResponse>(url, order);

  return data;
}
