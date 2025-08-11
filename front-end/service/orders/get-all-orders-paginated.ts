import api from "@/api/axios";
import { PedidoApiResponse } from "@/types/order/Pedido";

export async function getAllOrdersPaginated(page?: number, size?: number) {
  const url = "/cantina/pedido";

  const { data } = await api.get<PedidoApiResponse>(url, {
    params: {
      page,
      size,
    },
  });

  return data;
}
