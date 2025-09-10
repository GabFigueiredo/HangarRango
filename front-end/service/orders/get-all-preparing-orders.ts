import { privateApi } from "@/api/privateApi";
import { PedidoResponse } from "@/types/order/Pedido";

export async function getAllPreparingOrders() {
  const url = "/admin/pedido/pedidos-pendentes";

  const { data } = await privateApi.get<PedidoResponse[]>(url);

  return data;
}
