"use client";

import ProductCard from "@/components/product-card";
import { SiteHeader } from "@/components/site-header";
import { SidebarInset } from "@/components/ui/sidebar";
import { Skeleton } from "@/components/ui/skeleton";
import { GetAllAvailableProducts } from "@/service/product/get-all-available-products";
import { useQuery } from "@tanstack/react-query";

export default function CardapioPage() {
  const { data, isLoading, isSuccess } = useQuery({
    queryKey: ["availabe-products"],
    queryFn: GetAllAvailableProducts,
    staleTime: 1000 * 60 * 5,
  });

  return (
    <SidebarInset className="h-full">
      <SiteHeader name="Pedir" />
      <div className="flex flex-wrap gap-5 p-5">
        {isLoading ? (
          Array.from({length: 5}).map((_, i) => (
            <Skeleton key={i} className="w-full h-20" />
          ))
        ) : data && isSuccess ? (
          data.map((p) => {
            return <ProductCard key={p.id} produto={p} />;
          })
        ) : (
          <div className="flex flex-col w-full justify-center items-center gap-3 mt-5">
            <h1 className="text-4xl font-bold">Ops..</h1>
            <p>A cantina está fechada.</p>
          </div>
        )}
      </div>
    </SidebarInset>
  );
}
