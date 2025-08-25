import { ProdutoType } from "@/types/Produto";

/**
 * Converte os produtos usados no cardápio/carrinho para o tipo que o request pede
 *
 * @param orders
 * @returns o tipo esperado do request para criar um pedido
 */
export function ProductToProductRequest(orders: ProdutoType[]) {
  return orders.map((order) => ({
    produtoId: order.id,
    quantidade: order.quantidade,
  }));
}
