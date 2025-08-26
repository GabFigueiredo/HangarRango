"use client";

import CartItem from "@/components/cart-item";
import PaymentForm from "@/components/payment-form";
import { SiteHeader } from "@/components/site-header";
import { Button } from "@/components/ui/button";
import { Dialog, DialogContent, DialogTrigger } from "@/components/ui/dialog";
import { Separator } from "@/components/ui/separator";
import { SidebarInset } from "@/components/ui/sidebar";
import { useCart } from "@/context/CartContext";
import Link from "next/link";

export default function CarrinhoPage() {
  const { orders, total } = useCart();

  return (
    <SidebarInset className="h-full">
      <SiteHeader name="Carrinho" />
      {orders.length >= 1 ? (
        <>
          <div className="p-4 flex mx-auto flex-col w-full gap-5 items-center justify-center max-w-[400px]">
            {orders.map((order) => {
              return <CartItem key={order.id} produto={order} />;
            })}
          </div>
          <Separator orientation="horizontal" />
          <div className="w-full flex flex-col gap-3 max-w-[400px] mx-auto p-4">
            <div className="flex items-center justify-between">
              <p>Valor total:</p>
              <h2>R${total}</h2>
            </div>
              <Dialog>
                <DialogTrigger asChild>
                  <Button className="w-full">Fazer o pedido</Button>
                </DialogTrigger>
                <DialogContent>
                  <PaymentForm />
                </DialogContent>
              </Dialog>
          </div>
        </>
      ) : (
        <div className="flex flex-col w-full justify-center items-center gap-3 mt-5">
          <h1 className="text-4xl font-bold">Ops..</h1>
          <p>Você não colocou nenhum item no carrinho.</p>
          <Link href={"/cantina"}>
            <Button>Ir ao cardápio</Button>
          </Link>
        </div>
      )}
    </SidebarInset>
  );
}
