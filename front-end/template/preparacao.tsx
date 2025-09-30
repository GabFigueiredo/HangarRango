'use client'

import { SiteHeader } from "@/components/site-header";
import StageOrder from "@/components/stage-order";
import { SidebarInset } from "@/components/ui/sidebar";
import { usePedidos } from "@/hooks/usePedidos";
import { getAllPreparingOrders } from "@/service/orders/get-all-preparing-orders";
import { useQuery } from "@tanstack/react-query";

export default function PreparacaoPage() {
    const token = localStorage.getItem("USER_TOKEN")

    const { data: response } = useQuery({
      queryKey: ["pedidos-pendentes"],
      queryFn: () => getAllPreparingOrders(token ?? ""),
      refetchOnWindowFocus: false,
    refetchOnReconnect: false,
    refetchInterval: false,
    staleTime: Infinity,

    });

    usePedidos()

    return (
    <SidebarInset className="h-screen">
        <SiteHeader name="Área de preparação"/>
        <div className="@container/main font-inter flex flex-1 flex-col gap-2 p-5">
            <div className="">
                <ul className="grid grid-cols-1 md:grid-cols-2 gap-5">
                    {response?.map((pedido) => (
                    <li key={pedido.id}>
                        <StageOrder Pedido={pedido}/>
                    </li>
                    ))}
                </ul>
            </div>
        </div>
    </SidebarInset>
  );
}