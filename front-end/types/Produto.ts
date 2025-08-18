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
