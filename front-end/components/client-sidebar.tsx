"use client"

import * as React from "react"
import {
  IconInnerShadowTop,
} from "@tabler/icons-react"

import { NavUser } from "@/components/nav-user"
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/components/ui/sidebar"
import { NavDocuments } from "./nav-documents"
import { Pizza, ShoppingBag, Users } from "lucide-react"

const data = {
  user: {
    name: "Roberta",
    email: "roberta@igrejacristahangar.com",
    avatar: "/avatars/shadcn.jpg",
  },
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
      url: "/cantina/cliente",
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
              <a href="#">
                <IconInnerShadowTop className="!size-5" />
                <span className="text-base font-semibold">Hangar Rango</span>
              </a>
            </SidebarMenuButton>
          </SidebarMenuItem>
        </SidebarMenu>
      </SidebarHeader>
      <SidebarContent>
        <NavDocuments items={data.client}/>
      </SidebarContent>
      <SidebarFooter>
        <NavUser user={data.user} />
      </SidebarFooter>
    </Sidebar>
  )
}
