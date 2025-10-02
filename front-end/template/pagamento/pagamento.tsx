'use client';

import { SiteHeader } from "@/components/site-header";
import { Suspense } from "react";
import Content from "./content"; 
import { SidebarInset } from "@/components/ui/sidebar";

export default function Pagamento() {
  return (
    <SidebarInset className="h-full">
      <SiteHeader name="Meus pedidos" />
      <Suspense fallback={<div>Carregando pagamento...</div>}>
        <Content />
      </Suspense>
    </SidebarInset>
  );
}
