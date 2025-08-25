// Na tela produtos 'quantidade' é usada como estoque.
// Já na tela cardapio e carrinho, é usada como quantidade do produto.
export interface ProdutoType {
  id: string;
  nome: string;
  preco: number;
  descricao: string;
  quantidade: number;
  status: boolean;
}

export interface ProdutoStatus {
  produtoId: string;
  status: boolean;
}

export interface ItensRequest {
  produtoId: string;
  quantidade: number;
}
