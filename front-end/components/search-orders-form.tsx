'use client'

import { DatePicker } from "./ui/date-picker";
import { Input } from "./ui/input";
import { Button } from "./ui/button";
import { Search } from "lucide-react";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "./ui/select";
import { Label } from "./ui/label";
import { Controller, useForm } from "react-hook-form"
import z from "zod";
import { zodResolver } from "@hookform/resolvers/zod"
import { PedidoStatus } from "@/enums/order/pedidoStatus.enum";
import { FormaPagamento } from "@/enums/order/formaPagamento.enum";
import { StatusPagamento } from "@/enums/order/statusPagamento.enum";
import { useMutation } from "@tanstack/react-query";
import { SearchClientsByFilter } from "@/service/orders/search-clients-by-filter";

const SearchFilterSchema = z.object({
    nome: z.string().optional(),
    data: z.date().optional(),
    pedidoStatus: z.enum(PedidoStatus).optional(),
    formaPagamento: z.enum(FormaPagamento).optional(),
    statusPagamento: z.enum(StatusPagamento).optional()
})

export type SearchFilterType = z.infer<typeof SearchFilterSchema>

export default function SearchOrdersForm() {
    const { mutate } = useMutation({
        mutationFn: SearchClientsByFilter,
        onSuccess: (data) => console.log(data),
    })

    const { handleSubmit, register, control } = useForm<SearchFilterType>({
        resolver: zodResolver(SearchFilterSchema),
        mode: "onSubmit"
    })

    async function handleFormAction(dados: SearchFilterType) {
        mutate(dados);
    }

    return (
    <form onSubmit={handleSubmit(handleFormAction)}className="flex flex-col md:flex-row w-full gap-2 items-end">
        <div className="flex flex-col gap-2 flex-1">
            <Label htmlFor="nome">Nome</Label>
            <Input className="w-full" placeholder="Nome" {...register("nome")} />
        </div>
        <div className="flex flex-col gap-2 flex-1">
            <Label htmlFor="dia">Dia</Label>
            <Controller
                name="data"
                control={control}
                render={({ field }) => (
                    <DatePicker value={field.value} onChange={field.onChange} />
                )}
            />
        </div>
        <div className="flex flex-col gap-2 flex-1">
            <Label htmlFor="status_pedido">Status do Pedido</Label>
            <Controller
                name="pedidoStatus"
                control={control}
                render={({ field }) => (
                    <Select value={field.value ?? ""} onValueChange={field.onChange}>
                    <SelectTrigger className="w-full">
                        <SelectValue placeholder="Pendente" />
                    </SelectTrigger>
                    <SelectContent>
                        <SelectItem value="CONCLUIDO">Concluído</SelectItem>
                        <SelectItem value="PREPARANDO">Pendente</SelectItem>
                    </SelectContent>
                    </Select>
                )}
                />
        </div>
        <div className="flex flex-col gap-2 flex-1">
            <Label htmlFor="formaPagamento">Forma de Pagamento</Label>
            <Controller
                name="formaPagamento"
                control={control}
                render={({ field }) => (
                    <Select value={field.value ?? ""} onValueChange={field.onChange}>
                        <SelectTrigger className="w-full">
                            <SelectValue placeholder="Marcado" />
                        </SelectTrigger>
                        <SelectContent>
                            <SelectItem value="PIX">Pix</SelectItem>
                            <SelectItem value="CARTAO">Cartão</SelectItem>
                            <SelectItem value="DINHEIRO">Dinheiro</SelectItem>
                            <SelectItem value="MARCADO">Marcado</SelectItem>
                        </SelectContent>
                    </Select>
                )}
            />

        </div>
        <div className="flex flex-col gap-2 flex-1">
            <Label htmlFor="statusPagamento">Status de Pagamento</Label>
            <Controller
                name="statusPagamento"
                control={control}
                render={({ field }) => (
                    <Select value={field.value ?? ""} onValueChange={field.onChange}>
                        <SelectTrigger className="w-full">
                            <SelectValue placeholder="Pendente" />
                        </SelectTrigger>
                        <SelectContent>
                            <SelectItem value="EFETUADO">Efetuado</SelectItem>
                            <SelectItem value="PENDENTE">Pendente</SelectItem>
                        </SelectContent>
                    </Select>   
                )}
            />
        </div>
      <Button type="submit" className="" variant="outline">
        <Search />
        <p>Pesquisar</p>
      </Button>
    </form>
  );
}
