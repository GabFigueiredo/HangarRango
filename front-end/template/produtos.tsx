"use client";

import CreateProductDialog from "@/components/create-product-dialog";
import { SiteHeader } from "@/components/site-header";
import { ProductColumns } from "@/components/table/product/columns";
import { ProductFilterFields } from "@/components/table/product/product-filter-fields";
import { Button } from "@/components/ui/button";
import { DataTable } from "@/components/ui/data-table";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { SidebarInset } from "@/components/ui/sidebar";
import { Skeleton } from "@/components/ui/skeleton";
import { getAllProducts } from "@/service/product/get-all-products";
import { useQuery } from "@tanstack/react-query";
import { PlusCircle } from "lucide-react";
import { useState } from "react";

export default function ProdutosPage() {
  const { data: response, isLoading, isError } = useQuery({
    queryKey: ["produtos"],
    queryFn: getAllProducts,
    refetchOnWindowFocus: false,
    refetchOnReconnect: false,
    refetchInterval: false,
    staleTime: Infinity,
  });

  const [open, setOpen] = useState(false);

  return (
    <SidebarInset className="h-full">
      <SiteHeader name="Gerenciamento de produtos" />
      <div className="px-4 lg:px-6 mt-4">
            <Dialog open={open} onOpenChange={setOpen}>
                <DialogTrigger asChild>
                    <Button variant={"default"} className="w-min hover:cursor-pointer">
                    <PlusCircle />
                    <p>Criar</p>
                    </Button>
                </DialogTrigger>
                <DialogContent>
                    <DialogHeader className="mb-5">
                    <DialogTitle>Criar Produto</DialogTitle>
                    <DialogDescription>
                        Crie um produto para o cardápio do cliente
                    </DialogDescription>
                    </DialogHeader>
                    <CreateProductDialog setOpen={setOpen} />
                </DialogContent>
            </Dialog>
        {isLoading ? (
          <div className="flex flex-wrap gap-5 p-5">
            {Array.from({ length: 8 }).map((_, i) => (
              <Skeleton key={i} className="w-full h-20" />
            ))}
          </div>
        ) : isError ? (
          <p>Não foi possível buscar os pedidos</p>
        ) : (
        <div className="">
            <DataTable
            columns={ProductColumns}
            data={response ?? []}
            FilterFields={ProductFilterFields}
            />
        </div>
        )}
      </div>
    </SidebarInset>
  );
}
