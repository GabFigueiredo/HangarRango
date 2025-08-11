import { AppSidebar } from "@/components/app-sidebar"
import { SectionCards } from "@/components/section-cards"
import { SiteHeader } from "@/components/site-header"
import { OrderColumns } from "@/components/table/order/columns"
import OrderFilterFields from "@/components/table/order/order-filter-fields"
import { DataTable } from "@/components/ui/data-table"
import {
  SidebarInset,
} from "@/components/ui/sidebar"
import { getAllOrders } from "@/service/orders/get-all-orders"
import { useQuery } from "@tanstack/react-query"


export default function DashboardPage() {
    const { data } = useQuery({
    queryKey: ["orders"],
    queryFn: () => getAllOrders(),
    });
  
    return (
        <>
        <AppSidebar variant="inset" />
        <SidebarInset>
            <SiteHeader />
            <div className="flex flex-1 flex-col">
            <div className="@container/main flex flex-1 flex-col gap-2">
                <div className="flex flex-col gap-4 py-4 md:gap-6 md:py-6">
                <SectionCards />
                </div>
                <div className="px-4 lg:px-6">
                    <DataTable columns={OrderColumns} data={data ?? []} FilterFields={OrderFilterFields} />
                </div>
            </div>
            </div>
        </SidebarInset>
        </>

  )
}
