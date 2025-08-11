import { FormaPagamento } from "@/enums/order/formaPagamento.enum";
import { PedidoStatus } from "@/enums/order/pedidoStatus.enum";
import { StatusPagamento } from "@/enums/order/statusPagamento.enum";

export interface PedidoResponse {
  id: string;
  clienteNome: string;
  preco: number;
  statusPagamento: StatusPagamento;
  formaPagamento: FormaPagamento;
  numeroPedido: number;
  createdAt: string;
  status: PedidoStatus;
}

export interface PedidoPage {
  number: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

export interface Link {
  href: string;
}

export interface PedidoApiResponse {
  page: PedidoPage;
  _embedded: {
    pedidoList: PedidoResponse[];
  };
  _links: {
    first?: Link;
    last?: Link;
    next?: Link;
    prev?: Link;
    self: Link;
  };
}
