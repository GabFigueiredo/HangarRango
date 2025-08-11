import api from "@/api/axios";
import { SearchFilterType } from "@/components/search-orders-form";

export async function SearchClientsByFilter(dados: SearchFilterType) {
  const params = new URLSearchParams();
  const formattedDate = dados.data?.toISOString().slice(0, 10);

  if (dados.nome) params.append("nome", dados.nome);
  if (formattedDate) params.append("data", formattedDate);
  if (dados.pedidoStatus) params.append("pedidoStatus", dados.pedidoStatus);
  if (dados.formaPagamento)
    params.append("formaPagamento", dados.formaPagamento);
  if (dados.statusPagamento)
    params.append("statusPagamento", dados.statusPagamento);

  const url = `/cantina/pedido/filtrar?${params.toString()}`;

  const dadosBuscados = await api.get(url);

  return dadosBuscados;
}
