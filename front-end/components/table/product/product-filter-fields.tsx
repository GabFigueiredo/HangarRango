'use client'

import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { ProdutoType } from "@/types/Produto";
import { Table } from "@tanstack/react-table";

interface ProductFilterFieldsProps {
  table: Table<ProdutoType>;
}

export function ProductFilterFields({ table }: ProductFilterFieldsProps) {
  return (
    <div className="flex flex-col md:flex-row gap-5 items-center py-4">
      <div className="flex flex-col gap-2 flex-1 w-full">
        <Label htmlFor="nome">Nome</Label>
        <Input
          placeholder="Busque por nome"
          value={(table.getColumn("nome")?.getFilterValue() as string) ?? ""}
          onChange={(event) =>
            table.getColumn("nome")?.setFilterValue(event.target.value)
          }
          className="w-full"
        />
      </div>
      <div className="flex flex-col gap-2 flex-1 w-full">
        <Label htmlFor="status">Status</Label>
        <Select
          value={(table.getColumn("status")?.getFilterValue() as string) ?? ""}
          onValueChange={(event) =>
            table.getColumn("status")?.setFilterValue(event)
          }
        >
          <SelectTrigger className="w-full">
            <SelectValue />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="ATIVO">Ativo</SelectItem>
            <SelectItem value="INATIVO">Inativo</SelectItem>
          </SelectContent>
        </Select>
      </div>
    </div>
  );
}
