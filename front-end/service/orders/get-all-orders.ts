import api from "@/api/axios";
import { PedidoResponse } from "@/types/order/Pedido";

export async function getAllOrders() {
  const url = "/cantina/pedidos";

  const { data } = await api.get<PedidoResponse[]>(url);

  return data;
}
