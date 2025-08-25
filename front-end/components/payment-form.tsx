import z from "zod";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "./ui/form";
import { FormaPagamento } from "@/enums/order/formaPagamento.enum";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Input } from "./ui/input";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "./ui/select";
import { Button } from "./ui/button";

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
    const form = useForm<FormSchemaType>({
            resolver: zodResolver(formSchema),
            defaultValues: {
                nome: "",
                sobrenome: "",
            },
        })
    
    function onSubmit() {
        
    }

    return (
        <Form {...form}>
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