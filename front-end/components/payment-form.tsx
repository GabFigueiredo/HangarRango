'use client'

import z from "zod";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "./ui/form";
import { FormaPagamento } from "@/enums/order/formaPagamento.enum";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Input } from "./ui/input";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "./ui/select";
import { Button } from "./ui/button";
import { useMutation } from "@tanstack/react-query";
import { CreateOrder } from "@/service/orders/create-order";
import { PedidoRequest, PedidoResponse } from "@/types/order/Pedido";
import { useCart } from "@/context/CartContext";
import { ProductToProductRequest } from "@/util/product-to-product-request";
import { StatusPagamento } from "@/enums/order/statusPagamento.enum";
import { useRouter } from "next/navigation";
import { DialogTitle } from "./ui/dialog";

const formSchema = z.object({
  nome: z.string().min(2, {
    message: "Seu nome não pode estar vazio",
  }),
    sobrenome: z.string({message: "O sobrenome não pode estar vazio"}),
    formaPagamento: z.enum(FormaPagamento, {
        message: "A Forma de pagamento não pode estar vazia"
    }),
})

type FormSchemaType = z.infer<typeof formSchema>

export default function PaymentForm() {
    const { addCompletedOrder, clearCart } = useCart();
    const router = useRouter();

    const { mutate } = useMutation({
        mutationFn: (data: PedidoRequest) => CreateOrder(data),
        onSuccess: (data: PedidoResponse) => {
            router.push("/cantina/meus-pedidos")
            addCompletedOrder(data)
            clearCart();
        },
        onError: (error) => console.log(error)
    })

    const { orders, total } = useCart()

    const form = useForm<FormSchemaType>({
        resolver: zodResolver(formSchema),
        defaultValues: {
            nome: "",
            sobrenome: "",
        },
    })
    
    function onSubmit(data: FormSchemaType) {
        // Se for pix, é direcionado para página de pix
        if (data.formaPagamento === "PIX") {
            return;
        }
        
        // Formata o pedido para o tipo necessário do request
        const formattedOrders = ProductToProductRequest(orders);
        const nome = data.nome.trim() + ` ${data.sobrenome.trim()}`

        // Monta o objeto
        const requestObject: PedidoRequest = {
            clienteNome: nome,
            formaPagamento: data.formaPagamento,
            preco: total,
            itens: formattedOrders,
            statusPagamento: StatusPagamento.PENDENTE
        }
        
        // Faz a requisição
        mutate(requestObject);
    }

    return (
        <Form {...form}>
            <DialogTitle>
                Detalhes do pagamento
            </DialogTitle>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
                <FormField
                    control={form.control}
                    name="nome"
                    render={({field}) => (
                        <FormItem>
                            <FormLabel>Nome</FormLabel>
                            <FormControl>
                                <Input placeholder="Nome" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="sobrenome"
                    render={({field}) => (
                        <FormItem>
                            <FormLabel>Sobrenome</FormLabel>
                            <FormControl>
                                <Input placeholder="Primeiro Sobrenome" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                        control={form.control}
                        name="formaPagamento"
                        render={({field}) => (
                            <FormItem>
                                <FormLabel>Forma de pagamento</FormLabel>
                                <FormControl>
                                    <Select value={String(field.value)} 
                                        onValueChange={field.onChange}>
                                        <SelectTrigger className="w-full">
                                            <SelectValue />
                                        </SelectTrigger>
                                        <SelectContent>
                                            <SelectItem value="PIX">Pix</SelectItem>
                                            <SelectItem value="CARTAO">Cartão</SelectItem>
                                            <SelectItem value="DINHEIRO">Dinheiro</SelectItem>
                                            <SelectItem value="MARCADO">Marcar</SelectItem>
                                        </SelectContent>
                                    </Select>
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />
                    <Button className="w-full">Próximo</Button>
            </form>
        </Form>
    )
}