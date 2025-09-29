'use client'

import { ProdutoType } from "@/types/Produto";
import { Card } from "./ui/card";
import { Minus, Pizza, Plus } from "lucide-react";
import { Drawer, DrawerClose, DrawerContent, DrawerDescription, DrawerFooter, DrawerHeader, DrawerTitle, DrawerTrigger } from "./ui/drawer";
import { Button } from "./ui/button";
import React from "react";
import { useCart } from "@/context/CartContext";
import { toast } from "sonner";

interface ProductCardProps {
    produto: ProdutoType
}

export default function ProductCard({ produto }: ProductCardProps) {
    const {addItem} = useCart(); 
    const [quantidade, setQuantidade] = React.useState(0);
    const [isDrawerOpen, setIsDrawerOpen] = React.useState(false);

    function onClick(adjustment: number) {
        setQuantidade(Math.max(0, quantidade + adjustment))
    }

    function handleSubmit(produto: ProdutoType) {
        addItem({...produto, quantidade: quantidade})
        toast.success("Item adicionado com sucesso")
        setIsDrawerOpen(false);
    }

    return (
        <Drawer open={isDrawerOpen} onOpenChange={setIsDrawerOpen}>
            <DrawerTrigger asChild>
                <Card className="flex flex-row p-5 items-center w-full">
                    <div className="w-[75px] h-[75px] rounded-xl overflow-hidden">
                        <Pizza className="w-full h-full"/>
                    </div>
                    <div className="w-full items-center gap-5 flex justify-between">
                        <div>
                            <h2>{produto.nome}</h2>
                            <p>R${produto.preco}</p>
                        </div>
                    </div>
                </Card>
            </DrawerTrigger>
            <DrawerContent>
                <div className="mx-auto w-full max-w-sm">
                    <DrawerHeader>
                        <DrawerTitle>{produto.nome}</DrawerTitle>
                        <DrawerDescription>{produto.descricao}</DrawerDescription>
                    </DrawerHeader>
                    <div className="flex items-center justify-center space-x-2 px-4 pb-0">
                        <Button
                            variant="outline"
                            size="icon"
                            className="h-8 w-8 shrink-0 rounded-full"
                            onClick={() => onClick(-1)}
                            disabled={quantidade <= 0}
                        >
                            <Minus />
                            <span className="sr-only">Decrease</span>
                        </Button>
                        <div className="flex-1 text-center">
                            <div className="text-7xl font-bold tracking-tighter">
                            {quantidade}
                            </div>
                            <div className="text-muted-foreground text-[0.70rem] uppercase">
                            {quantidade > 1 ? "Unidades" : "Unidade"}
                            </div>
                        </div>
                        <Button
                            variant="outline"
                            size="icon"
                            className="h-8 w-8 shrink-0 rounded-full"
                            onClick={() => onClick(1)}
                        >
                            <Plus />
                            <span className="sr-only">Increase</span>
                        </Button>
                    </div>
                    <DrawerFooter className="flex flex-col items-center justify-center">
                        <Button
                            onClick={() => handleSubmit(produto)}
                            className="w-fit disabled:cursor-not-allowed cursor-pointer"
                            disabled={quantidade <= 0}>
                                Adicionar ao carrinho
                        </Button>
                        <DrawerClose asChild>
                            <Button variant="outline">Cancelar</Button>
                        </DrawerClose>
                    </DrawerFooter>
                </div>
            </DrawerContent>
        </Drawer>
    )

}