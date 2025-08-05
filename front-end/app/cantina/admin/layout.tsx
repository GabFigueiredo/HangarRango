'use client'

import { AppSidebar } from "@/components/app-sidebar";
import { SidebarProvider } from "@/components/ui/sidebar";
import { ReactNode } from "react";

interface LayoutProps {
    children: ReactNode;

}
export default function Layout({ children }: LayoutProps) {
    return (
            <SidebarProvider>
                <AppSidebar variant="inset" />
                <main className="w-full">
                    {children}
                </main>
            </SidebarProvider>
    )
}