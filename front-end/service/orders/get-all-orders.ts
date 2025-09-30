import api from "@/api/axios";
import { PedidoResponse } from "@/types/order/Pedido";

export async function getAllOrders() {
  const url = "/admin/pedido";

  const { data } = await api.get<PedidoResponse[]>(url);

  return data;
}
