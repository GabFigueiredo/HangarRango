'use client'

import { ProdutoType } from "@/types/Produto";
import React, { createContext, useContext } from "react";
import { produce } from "immer"  

interface CartProviderProps {
    children: React.ReactNode;
}

interface CartContextType {
    orders: ProdutoType[]
    addItem: (newProduct: ProdutoType) => void;
    removeItem: (productToRemove: ProdutoType) => void;
    changeItemAmount: (productToChange: ProdutoType, amount: number) => void;
    total: number;
}

const CartContext = createContext<CartContextType | undefined>(undefined);

export default function CartProvider({ children }: CartProviderProps) {
    const [orders, setOrders] = React.useState<ProdutoType[]>([]);

    function addItem(newProduct: ProdutoType) {
        const nextState = produce(orders, draft => {
            const existingProduct = draft.find(p => p.id === newProduct.id);
            if (existingProduct) {
                existingProduct.quantidade += newProduct.quantidade;
                return;
            }
            draft.push(newProduct);
        })
        setOrders(nextState)
    }

    function removeItem(productToRemove: ProdutoType) {
        const nextState = produce(orders, draft => {
            const index = draft.findIndex(p => p.id === productToRemove.id);
            if (index !== -1) {
                draft.splice(index, 1);
            }
        });

        setOrders(nextState)
    }

    function changeItemAmount(productToChange: ProdutoType, amount: number) {
        const nextState = produce(orders, draft => {
            const product = draft.find(p => p.id === productToChange.id);

                if (!product) return;

                // se zerar ou ficar negativo, remove
                if (product.quantidade + amount <= 0) {
                    const index = draft.findIndex(p => p.id === productToChange.id);
                    if (index !== -1) {
                        draft.splice(index, 1);
                    }
                    return;
                }
                
                product.quantidade += amount;
            })

        setOrders(nextState)
    }

    const total = React.useMemo(() => {
        return orders.reduce((acc, item) => acc + item.preco * item.quantidade, 0);
    }, [orders]);

    return (
        <CartContext.Provider value={{orders, addItem, removeItem, changeItemAmount, total}}>
            {children}
        </CartContext.Provider>
    )
}

export function useCart() {
    const context = useContext(CartContext);
    if (!context) throw new Error("useCart deve ser usado dentro de CartProvider");
    return context;
}