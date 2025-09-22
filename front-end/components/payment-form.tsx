"use client";

import z from "zod";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "./ui/form";
import { FormaPagamento } from "@/enums/order/formaPagamento.enum";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Input } from "./ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "./ui/select";
import { Button } from "./ui/button";
import { useMutation } from "@tanstack/react-query";
import { CreateOrder } from "@/service/orders/create-order";
import { PedidoRequest, PedidoResponse } from "@/types/order/Pedido";
import { useCart } from "@/context/CartContext";
import { ProductToProductRequest } from "@/util/product-to-product-request";
import { StatusPagamento } from "@/enums/order/statusPagamento.enum";
import { useRouter } from "next/navigation";
import { DialogTitle } from "./ui/dialog";
import { CPFInput } from "./ui/cpf-input";
import { Cliente } from "@/types/client/client";
import { toast } from "sonner";

interface PaymentFormProps {
  setIsPaymentFormOpen: (open: boolean) => void;
  setIsAlertPaymentOpen: (open: boolean) => void;
  setPixCopiaECola: (pix: string) => void;
  setPaymentOption: (option: "PAGAMENTO_MANUAL" | "PIX") => void;
}

const formSchema = z.object({
  nome: z.string().min(2, {
    error: "Seu nome não pode estar vazio",
  }),
  sobrenome: z.string({ message: "O sobrenome não pode estar vazio" }),
  formaPagamento: z.enum(FormaPagamento, {
    error: "A Forma de pagamento não pode estar vazia",
  }),
  cellphone: z.string({ message: "O telefone não pode estar vazio" }),
  email: z.email({ error: "Email inválido" }),
  taxId: z.string({ error: "O CPF não pode estar vazio" }),
});

type FormSchemaType = z.infer<typeof formSchema>;

export default function PaymentForm(modal: PaymentFormProps) {
  const { addCompletedOrder, clearCart, orders, total } = useCart();
  const router = useRouter();

  const { mutate } = useMutation({
    mutationFn: (data: PedidoRequest) => CreateOrder(data),
    onSuccess: (data: PedidoResponse) => {
      toast.success("Seu pedido foi efetuado.");

      switch (data.formaPagamento) {
        case "PIX":
          modal.setPaymentOption("PIX");
          modal.setPixCopiaECola(data.abacateResponse.data?.brCode || "");
          break;
        case "CARTAO":
        case "DINHEIRO":
          modal.setPaymentOption("PAGAMENTO_MANUAL");
          modal.setPixCopiaECola("");
          break;
        case "MARCADO":
          router.push("/cantina/meus-pedidos");
          clearCart();
          break;
      }
      console.log("BATE AQUI");
      modal.setIsAlertPaymentOpen(true);
      modal.setIsPaymentFormOpen(false);
    },
    onError: (error) => console.log(error),
  });

  const form = useForm<FormSchemaType>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      nome: "",
      sobrenome: "",
      cellphone: "",
      email: "",
      taxId: "",
    },
  });

  function onSubmit(data: FormSchemaType) {
    // Formata o pedido para o tipo necessário do request
    const formattedOrders = ProductToProductRequest(orders);
    const nome = data.nome.trim() + ` ${data.sobrenome.trim()}`;

    const cliente: Cliente = {
      name: nome,
      cellphone: data.cellphone,
      email: data.email,
      taxId: data.taxId,
    };

    // Monta o objeto
    const requestObject: PedidoRequest = {
      cliente: cliente,
      formaPagamento: data.formaPagamento,
      preco: total,
      itens: formattedOrders,
      statusPagamento: StatusPagamento.PENDENTE,
    };

    // Faz a requisição
    mutate(requestObject);
  }

  return (
    <Form {...form}>
      <DialogTitle>Detalhes do pagamento</DialogTitle>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
        <FormField
          control={form.control}
          name="nome"
          render={({ field }) => (
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
          render={({ field }) => (
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
          name="cellphone"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Telefone</FormLabel>
              <FormControl>
                <Input {...field} />
              </FormControl>
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="email"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Email</FormLabel>
              <FormControl>
                <Input type="email" {...field} />
              </FormControl>
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="taxId"
          render={({ field }) => (
            <FormItem>
              <FormLabel>CPF</FormLabel>
              <FormControl>
                <CPFInput value={field.value} onChange={field.onChange} />
              </FormControl>
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="formaPagamento"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Forma de pagamento</FormLabel>
              <FormControl>
                <Select
                  value={String(field.value)}
                  onValueChange={field.onChange}
                >
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
  );
}
