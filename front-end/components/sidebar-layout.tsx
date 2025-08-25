'use client'

import { ReactNode } from "react";
import { SidebarProvider } from "./ui/sidebar";

interface SidebarLayoutProps {
  children: ReactNode;
  sidebar: ReactNode;
}

export default function SideBarLayout({ children, sidebar }: SidebarLayoutProps) {
  return (
    <SidebarProvider
      style={
        {
          "--sidebar-width": "calc(var(--spacing) * 72)",
          "--header-height": "calc(var(--spacing) * 12)",
        } as React.CSSProperties
      }
    >
      {sidebar}
      <main className="w-full">{children}</main>
    </SidebarProvider>
  );
}