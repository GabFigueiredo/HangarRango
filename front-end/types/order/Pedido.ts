import { FormaPagamento } from "@/enums/order/formaPagamento.enum";
import { PedidoStatus } from "@/enums/order/pedidoStatus.enum";
import { StatusPagamento } from "@/enums/order/statusPagamento.enum";
import { ProdutoPedidoType } from "../ProdutoPedido";
import { ItensRequest } from "../Produto";
import { Cliente } from "../client/client";
import { PixQrCodeResponse } from "../abacate/abacate";

export interface PedidoResponse {
  id: string;
  cliente: Cliente;
  preco: number;
  status: PedidoStatus;
  statusPagamento: StatusPagamento;
  formaPagamento: FormaPagamento;
  numeroPedido: number;
  createdAt: string;
  abacateResponse: PixQrCodeResponse;
  itens: ProdutoPedidoType[];
}

export interface PedidoRequest {
  cliente: Cliente;
  preco: number;
  statusPagamento: StatusPagamento;
  formaPagamento: FormaPagamento;
  itens: ItensRequest[];
}

export interface PedidoResumo {
  totalPreco: number;
  totalPrecoDeHoje: number;
}

export interface produtoPreparacao {
  nome: string;
  quantidade: number;
}

export interface PreparacaoResponse {
  id: string;
  numeroPedido: number;
  listaProdutos: produtoPreparacao[];
}

export interface StatusPagamentoRequest {
  id: string;
  status: StatusPagamento;
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
