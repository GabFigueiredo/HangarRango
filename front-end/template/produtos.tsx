'use client'

import CreateProductDialog from "@/components/create-product-dialog";
import { SiteHeader } from "@/components/site-header";
import { ProductColumns } from "@/components/table/product/columns";
import { ProductFilterFields } from "@/components/table/product/product-filter-fields";
import { Button } from "@/components/ui/button";
import { DataTable } from "@/components/ui/data-table";
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog";
import { SidebarInset } from "@/components/ui/sidebar";
import { getAllProducts } from "@/service/product/get-all-products";
import { useQuery } from "@tanstack/react-query";
import { PlusCircle } from "lucide-react";

export default function ProdutosPage() {
    const { data } = useQuery({
        queryKey: ["produtos"],
        queryFn: getAllProducts
    })

    return (
        <SidebarInset className="h-full">
            <SiteHeader name="Gerenciamento de produtos"/>
            <div className="px-4 lg:px-6 mt-4">
                <Dialog>
                    <DialogTrigger asChild>
                        <Button variant={"default"} className="w-min hover:cursor-pointer">
                            <PlusCircle/>
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
                        <CreateProductDialog />
                </DialogContent>
                </Dialog>
            </div>
            <div className="px-4 lg:px-6">
                <DataTable columns={ProductColumns} data={data ?? []} FilterFields={ProductFilterFields} />
            </div>
        </SidebarInset>
    )
}