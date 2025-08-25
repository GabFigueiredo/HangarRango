import { AdminSidebar } from "@/components/admin-sidebar";
import SideBarLayout from "@/components/sidebar-layout";
import { ReactNode } from "react";

interface LayoutProps {
    children: ReactNode;
}

export default function Layout({children}: LayoutProps) {
    return (
        <SideBarLayout sidebar={<AdminSidebar />}>
            {children}
        </SideBarLayout>
    )
}