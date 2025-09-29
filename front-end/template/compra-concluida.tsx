'use client'

import { SiteHeader } from "@/components/site-header";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
} from "@/components/ui/card";
import { Separator } from "@/components/ui/separator";
import { SidebarInset } from "@/components/ui/sidebar";
import { useCart } from "@/context/CartContext";
import Link from "next/link";

export default function CompraConcluida() {
  const { completedOrders } = useCart();

  return (
    <SidebarInset className="h-full">
      <SiteHeader name="Meus pedidos" />
      <div className="w-full font-inter px-4 flex flex-col items-center justify-center">
        {completedOrders.length > 0 ? (
          completedOrders.map((order) => {
            return (
              <Card
                className="max-w-[400px]  flex flex-col gap-3 justify-center w-full items-center mt-5"
                key={order.id}
              >
                <CardHeader className="w-full flex flex-col items-center justify-center">
                  <p className="text-[12px]">Número do pedido:</p>
                  <h1 className="text-4xl font-bold">{order.numeroPedido}</h1>
                </CardHeader>
                <Separator />
                <CardContent>
                  {order.itens.map((item) => {
                    return (
                      <div
                        key={item.nomeProduto}
                        className="flex gap-2 items-center justify-between"
                      >
                        <p className="">{item.nomeProduto}</p>
                        <p className="text-primary">x{item.quantidade}</p>
                      </div>
                    );
                  })}
                </CardContent>
                <Separator />
                <CardFooter>
                  <div className="flex items-center justify-between gap-1">
                    <p>Preço:</p>
                    <h2 className="text-primary">R${order.preco}</h2>
                  </div>
                </CardFooter>
              </Card>
            );
          })
        ) : (
          <div className="flex flex-col w-full justify-center items-center gap-3 mt-5">
            <h1 className="text-4xl font-bold">Ops..</h1>
            <p>Você ainda não concluiu um pedido.</p>
            <Link href={"/cantina"}>
              <Button>Ir ao cardápio</Button>
            </Link>
          </div>
        )}
      </div>
    </SidebarInset>
  );
}
