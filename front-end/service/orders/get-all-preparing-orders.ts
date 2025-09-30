import api from "@/api/axios";
import { PedidoResponse } from "@/types/order/Pedido";

export async function getAllPreparingOrders() {
  const url = "/admin/pedido/pedidos-pendentes";

  const { data } = await api.get<PedidoResponse[]>(url);

  return data;
}
