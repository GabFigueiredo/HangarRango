import { privateApi } from "@/api/privateApi";
import { PedidoResponse } from "@/types/order/Pedido";

export async function getAllOrders() {
  const url = "/admin/pedido";

  const { data, status } = await privateApi.get<PedidoResponse[]>(url);

  return data;
}
