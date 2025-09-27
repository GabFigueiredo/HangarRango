"use client"

import * as React from "react"
import {
  IconInnerShadowTop,
} from "@tabler/icons-react"

import {
  Sidebar,
  SidebarContent,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/components/ui/sidebar"
import { NavDocuments } from "./nav-documents"
import { Pizza, ShoppingBag, Users } from "lucide-react"
import Link from "next/link"

const data = {
  client: [
    {
      name: "Pedir",
      url: "/cantina",
      icon: Pizza,
    },
    {
      name: "Carrinho",
      url: "/cantina/carrinho",
      icon: ShoppingBag,
    },
    {
      name: "Meus pedidos",
      url: "/cantina/meus-pedidos",
      icon: Users,
    },
  ],
}

export function ClientSidebar({ ...props }: React.ComponentProps<typeof Sidebar>) {
  return (
    <Sidebar collapsible="offcanvas" {...props}>
      <SidebarHeader>
        <SidebarMenu>
          <SidebarMenuItem>
            <SidebarMenuButton
              asChild
              className="data-[slot=sidebar-menu-button]:!p-1.5"
            >
              <Link href="/">
                <IconInnerShadowTop className="!size-5" />
                <span className="text-base font-semibold">Hangar Rango</span>
              </Link>
            </SidebarMenuButton>
          </SidebarMenuItem>
        </SidebarMenu>
      </SidebarHeader>
      <SidebarContent>
        <NavDocuments items={data.client}/>
      </SidebarContent>
    </Sidebar>
  )
}
