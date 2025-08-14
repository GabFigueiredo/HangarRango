import api from "@/api/axios";
import { PreparacaoResponse } from "@/types/order/Pedido";

export async function getAllPreparingOrders() {
  const url = "/cantina/pedidos-pendentes";

  const { data } = await api.get<PreparacaoResponse[]>(url);

  return data;
}
