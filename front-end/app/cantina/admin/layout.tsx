"use client";

import { AppSidebar } from "@/components/app-sidebar";
import { SidebarProvider } from "@/components/ui/sidebar";
import { ReactNode } from "react";

interface LayoutProps {
  children: ReactNode;
}
export default function Layout({ children }: LayoutProps) {
  return (
    <SidebarProvider
      style={
        {
          "--sidebar-width": "calc(var(--spacing) * 72)",
          "--header-height": "calc(var(--spacing) * 12)",
        } as React.CSSProperties
      }
    >
      <AppSidebar variant="inset" />
      <main className="w-full">{children}</main>
    </SidebarProvider>
  );
}
