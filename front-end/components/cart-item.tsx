import { ProdutoType } from "@/types/Produto";
import { Card, CardContent, CardFooter, CardHeader } from "./ui/card"
import { Button } from "./ui/button";
import { Minus, Plus } from "lucide-react";
import { useCart } from "@/context/CartContext";
import React from "react";

interface CartItemProps {
    produto: ProdutoType;
}

export default function CartItem({ produto } : CartItemProps) {
    const [amount, setAmount] = React.useState(produto.quantidade);
    
    const { changeItemAmount, removeItem } = useCart();    


    function handleChangeItemAmount(value: number) {
        setAmount(prev => prev + value);
        changeItemAmount(produto, value);
    }

    function handleRemoveItem() {
        removeItem(produto);
    }

    return (
        <Card className="flex">
            <CardHeader className="flex flex-center justify-between">
                <h2>{produto.nome}</h2>
                <span>R${produto.preco}</span>
            </CardHeader>
            <CardContent>
                <p>{produto.descricao}</p>
            </CardContent>
            <CardFooter className="flex flex-center justify-between">
                <div className="flex h-min items-center border rounded-md">
                    <Button onClick={() => handleChangeItemAmount(-1)} size="icon" variant='outline'>
                        <Minus />
                    </Button>
                    <p className="mx-3">{amount}</p>
                    <Button onClick={() => handleChangeItemAmount(1)} size="icon" variant='outline'>
                        <Plus />
                    </Button>
                </div>
                <Button onClick={() => handleRemoveItem()} variant='destructive' >Remover</Button>
            </CardFooter>
        </Card>
    )
}