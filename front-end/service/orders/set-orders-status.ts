import { privateApi } from "@/api/privateApi";
import { PedidoStatus } from "@/enums/order/pedidoStatus.enum";
import { PedidoResponse } from "@/types/order/Pedido";

interface setOrderStatusProps {
  clientId: string;
  pedidoStatus: PedidoStatus;
}

export async function setOrderStatus(status: setOrderStatusProps) {
  const url = "/admin/pedido/status";

  const resumo = await privateApi.patch<PedidoResponse>(url, status);

  return resumo;
}
