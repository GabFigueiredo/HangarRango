'use client'

import { SiteHeader } from "@/components/site-header"
import { OrderColumns } from "@/components/table/order/columns"
import OrderFilterFields from "@/components/table/order/order-filter-fields"
import { DataTable } from "@/components/ui/data-table"
import {
  SidebarInset,
} from "@/components/ui/sidebar"
import { Skeleton } from "@/components/ui/skeleton"
import { getAllOrders } from "@/service/orders/get-all-orders"
import { useQuery } from "@tanstack/react-query"
import React from "react"

export default function DashboardPage() {
    const token = localStorage.getItem("USER_TOKEN")

    const { data: response, isLoading, isError } = useQuery({
        queryKey: ["orders"],
        queryFn: () => getAllOrders(token ?? ""),
        refetchOnWindowFocus: false,
        refetchOnReconnect: false,
        refetchInterval: 1800000,
        staleTime: Infinity, 
    });

    return (
        <>
        <SidebarInset className="h-full">
            <SiteHeader name="Financeiro" />
            <div className="flex flex-1 flex-col font-inter">
                <div className="@container/main flex flex-1 flex-col gap-2">
                    {/* <div className="flex flex-col gap-4 py-4 md:gap-6 md:py-6">
                        <SectionCards />
                    </div> */}
                    <div className="px-4 lg:px-6">
                        {isLoading ?
                            <div className="flex flex-wrap gap-5 p-5">
                                {Array.from({length: 8}).map((_, i) => (
                                    <Skeleton key={i} className="w-full h-20" />
                                ))}
                            </div>
                        : isError ?
                            <p>Não foi possível buscar os pedidos</p>
                        :
                            <DataTable columns={OrderColumns} data={response ?? []} FilterFields={OrderFilterFields} />
                        }
                    </div>
                </div>
            </div>
        </SidebarInset>
        </>

  )
}
