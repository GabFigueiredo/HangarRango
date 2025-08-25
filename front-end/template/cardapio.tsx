"use client";

import ProductCard from "@/components/product-card";
import { SiteHeader } from "@/components/site-header";
import { Button } from "@/components/ui/button";
import { SidebarInset } from "@/components/ui/sidebar";
import { GetAllAvailableProducts } from "@/service/product/get-all-available-products";
import { ProdutoType } from "@/types/Produto";
import { useQuery } from "@tanstack/react-query";
import Link from "next/link";

export const produtos: ProdutoType[] = [
  {
    id: "1",
    nome: "Notebook Dell",
    preco: 4500.0,
    descricao: "Notebook Dell Inspiron 15, i7, 16GB RAM, SSD 512GB",
    quantidade: 10,
    status: true,
  },
  {
    id: "2",
    nome: "Mouse Gamer",
    preco: 150.0,
    descricao: "Mouse gamer com iluminação RGB e 7 botões programáveis",
    quantidade: 50,
    status: true,
  },
  {
    id: "3",
    nome: "Monitor LG 24''",
    preco: 850.0,
    descricao: "Monitor LG Full HD de 24 polegadas com painel IPS",
    quantidade: 20,
    status: false,
  },
  {
    id: "4",
    nome: "Notebook Dell",
    preco: 4500.0,
    descricao: "Notebook Dell Inspiron 15, i7, 16GB RAM, SSD 512GB",
    quantidade: 10,
    status: true,
  },
  {
    id: "5",
    nome: "Mouse Gamer",
    preco: 150.0,
    descricao: "Mouse gamer com iluminação RGB e 7 botões programáveis",
    quantidade: 50,
    status: true,
  },
  {
    id: "6",
    nome: "Monitor LG 24''",
    preco: 850.0,
    descricao: "Monitor LG Full HD de 24 polegadas com painel IPS",
    quantidade: 20,
    status: false,
  },
];

export default function CardapioPage() {
  const { data, isSuccess } = useQuery({
    queryKey: ["availabe-products"],
    queryFn: GetAllAvailableProducts
  })

  if (isSuccess) {
    console.log("SUCESSO", data)

  }

  return (
    <SidebarInset className="h-full">
      <SiteHeader name="Pedir" />
      <div className="flex flex-wrap gap-5 p-5">
        {data ? (
          data.map((p) => {
            return <ProductCard key={p.id} produto={p} />;
          })
        ) : (
          <div className="flex flex-col items-center gap-3 mt-5">
            <h1 className="text-4xl font-bold">Ops..</h1>
            <p>A cantina está fechada.</p>
            <Link href={"/pedir"}>
              <Button>Ir ao cardápio</Button>
            </Link>
          </div>
        )}
      </div>
    </SidebarInset>
  );
}
