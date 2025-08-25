"use client"

import {
  type Icon,
} from "@tabler/icons-react"

import {
  SidebarGroup,
  SidebarGroupLabel,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/components/ui/sidebar"
import Link from "next/link"
import { cn } from "@/lib/utils"
import { usePathname } from "next/navigation"
import { type LucideIcon } from "lucide-react";

export function NavDocuments({
  items,
}: {
  items: {
    name: string
    url: string
    icon: Icon | LucideIcon;
  }[]
}) {
  const pathname = usePathname();

  return (
    <SidebarGroup className="group-data-[collapsible=icon]:hidden">
      <SidebarGroupLabel>Cliente</SidebarGroupLabel>
      <SidebarMenu>
        {items.map((item) => (
          <Link href={item.url} key={item.name}>
            <SidebarMenuItem >
              <SidebarMenuButton className={cn(
                  "min-w-8 duration-200 ease-linear cursor-pointer",
                  "hover:bg-primary hover:text-primary-foreground",
                  pathname === item.url
                    ? "bg-primary text-primary-foreground"
                    : "bg-transparent text-foreground"
                )} asChild>
                <div>
                  <item.icon />
                  <span>{item.name}</span>
                </div>
              </SidebarMenuButton>
            </SidebarMenuItem>
          </Link>
        ))}
        <SidebarMenuItem>
        </SidebarMenuItem>
      </SidebarMenu>
    </SidebarGroup>
  )
}
