import api from "@/api/axios";
import { PedidoStatus } from "@/enums/order/pedidoStatus.enum";
import { PedidoResponse } from "@/types/order/Pedido";

interface setOrderStatusProps {
  clientId: string;
  pedidoStatus: PedidoStatus;
}

export async function setOrderStatus(status: setOrderStatusProps) {
  const url = "/admin/pedido/status";

  const resumo = await api.patch<PedidoResponse>(url, status);

  return resumo;
}
