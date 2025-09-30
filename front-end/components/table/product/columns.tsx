'use client'

import { IconCircleCheckFilled, IconLoader } from "@tabler/icons-react";
import { ColumnDef } from "@tanstack/react-table";
import { Badge } from "../../ui/badge";
import { Label } from "../../ui/label";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "../../ui/select";
import { ProdutoType } from "@/types/Produto";
import { ChangeProductStatus } from "@/service/product/change-product-status";

export const ProductColumns: ColumnDef<ProdutoType>[] = [

  {
    accessorKey: "nome",
    header: "Nome",
  },
  {
    accessorKey: "preco",
    header: "Preço",
    cell: ({ row }) => {
      const amount = parseFloat(row.getValue("preco"));
      const formatted = new Intl.NumberFormat("pt-BR", {
        style: "currency",
        currency: "BRL",
      }).format(amount);

      return <p>{formatted}</p>;
    },
  },
  {
    accessorKey: "descricao",
    header: "Descrição",
  },
  {
    accessorKey: "quantidade",
    header: "Quantidade",
    cell: ({ row }) => (
      <Badge variant="outline" className="text-muted-foreground px-1.5">
        {row.original.quantidade > 0 ? (
          <IconCircleCheckFilled className="fill-green-500 dark:fill-green-400" />
        ) : (
          <IconLoader />
        )}
        {row.original.quantidade}
      </Badge>
    ),
  },
  {
    accessorKey: "status",
    header: "Status",
    cell: ({ row }) => {

      return (
        <>
          <Label className="sr-only">Status</Label>
          <Select onValueChange={(value) => 
            ChangeProductStatus({
              produtoId: row.original.id,
              status: value === "Ativo" ? true : false
            })
          }>
            <SelectTrigger
              className="w-38 **:data-[slot=select-value]:block **:data-[slot=select-value]:truncate"
              size="sm"
            >
              <SelectValue placeholder={row.original.status ? "Ativo" : "Inativo"} />
            </SelectTrigger>
            <SelectContent align="end">
              <SelectItem value="Ativo">Ativo</SelectItem>
              <SelectItem value="Inativo">Inativo</SelectItem>
            </SelectContent>
          </Select>
        </>
      );
    },
  },
];
