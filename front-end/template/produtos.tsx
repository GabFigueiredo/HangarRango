import { SiteHeader } from "@/components/site-header";
import { DataTable } from "@/components/ui/data-table";
import { SidebarInset } from "@/components/ui/sidebar";
import { useQuery } from "@tanstack/react-query";

export default function ProdutosPage() {
    // const {  } = useQuery({
    //     queryKey: ["produtos"],
    //     queryFn: 
    // })

    return (
        <SidebarInset className="h-full">
            <SiteHeader name="Gerenciamento de produtos"/>
            <div className="px-4 lg:px-6">
                {/* <DataTable columns={ProductColumns} data={data ?? []} FilterFields={ProductFilterFields} /> */}
            </div>
        </SidebarInset>
    )
}