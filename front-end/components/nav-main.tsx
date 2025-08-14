"use client"

import { type Icon } from "@tabler/icons-react"

import {
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/components/ui/sidebar"
import { usePathname } from "next/navigation"
import Link from "next/link"
import React from "react"
import { cn } from "@/lib/utils"

export function NavMain({ items, }: {
  items: {
    title: string
    url: string
    icon?: Icon
  }[]
})

{
  const pathname = usePathname();

  return (
    <SidebarGroup>
      <SidebarGroupLabel>Admin</SidebarGroupLabel>
      <SidebarGroupContent className="flex flex-col gap-2">
        <SidebarMenu>
          {items.map((item) => (
            <SidebarMenuItem key={item.title}>
              <Link href={item.url}>
                <SidebarMenuButton
                  className={cn(
                    "min-w-8 duration-200 ease-linear cursor-pointer",
                    "hover:bg-primary hover:text-primary-foreground",
                    pathname === item.url || pathname.includes(item.url)
                      ? "bg-primary text-primary-foreground"
                      : "bg-transparent text-foreground"
                  )}
                  tooltip={item.title}
                  >
                  {item.icon && <item.icon />}
                  <span>{item.title}</span>
                </SidebarMenuButton>
              </Link>
            </SidebarMenuItem>
          ))}
        </SidebarMenu>
      </SidebarGroupContent>
    </SidebarGroup>
  )
}