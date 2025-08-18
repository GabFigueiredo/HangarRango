"use client"
 
import { zodResolver } from "@hookform/resolvers/zod"
import { useForm } from "react-hook-form"
import { z } from "zod"
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from "./ui/form"
import { Input } from "./ui/input"
import { Button } from "./ui/button"
import { Textarea } from "./ui/textarea"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "./ui/select"
import { useMutation, useQueryClient } from "@tanstack/react-query"
import { CreateProduct } from "@/service/product/create-product"
import { toast } from "sonner"
import { ProdutoType } from "@/types/Produto"
 
const formSchema = z.object({
  nome: z.string().min(2, {
    message: "Nome do produto tem que ter no mínimo 2 caracteres.",
  }),
    preco: z.number().positive({
        error: "A quantidade a ser vendida não pode ser negativa"
    }),
    descricao: z.string(),
    quantidade: z.number(),
    status: z.boolean({
        error: "O valor tem que ser sim ou não"
    })
})

type FormSchemaType = z.infer<typeof formSchema>

export default function CreateProductDialog() {
    const form = useForm<FormSchemaType>({
        resolver: zodResolver(formSchema),
        defaultValues: {
            nome: "",
            preco: 0,
            descricao: "",
            quantidade: 0,
            status: true,
        },
    })

    const queryClient = useQueryClient();

    const { mutate, data } = useMutation<ProdutoType, Error, ProdutoType>({
        mutationFn: CreateProduct,
          onSuccess: (newProduct) => {
            console.log(data);
            queryClient.setQueryData<ProdutoType[]>(["produtos"], (oldData) => {
            if (!oldData) return [newProduct];
            return [...oldData, newProduct];
            });
        },
        onError: () => toast.error("Não foi possível criar o produto."),
    })

    function onSubmit(data: FormSchemaType) {
        mutate(data)
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
                                <Input placeholder="Produto" {...field} />
                            </FormControl>
                            <FormDescription>
                                Nome que vai aparecer aos clientes.
                            </FormDescription>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="preco"
                    render={({field}) => (
                        <FormItem>
                            <FormLabel>Preço</FormLabel>
                            <FormControl>
                                <Input
                                    type="number"
                                    {...field}
                                    value={field.value || ""}
                                    onChange={(e) => field.onChange(Number(e.target.value))}
                                />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="descricao"
                    render={({field}) => (
                        <FormItem>
                            <FormLabel>Descrição</FormLabel>
                            <FormControl>
                                <Textarea placeholder="Descrição breve do produto"  {...field}/>
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <div className="flex gap-2">
                    <FormField
                        control={form.control}
                        name="quantidade"
                        render={({field}) => (
                            <FormItem>
                                <FormLabel>Quantidade</FormLabel>
                                <FormControl>
                                    <Input
                                        type="number"
                                        {...field}
                                        value={field.value || ""}     
                                        onChange={(e) => field.onChange(Number(e.target.value))}
                                    />
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />
                    <FormField
                        control={form.control}
                        name="status"
                        render={({field}) => (
                            <FormItem>
                                <FormLabel>Status</FormLabel>
                                <FormControl>
                                    <Select value={String(field.value)} 
                                        onValueChange={(val) => field.onChange(val === "true")}>
                                        <SelectTrigger className="w-[180px]">
                                            <SelectValue />
                                        </SelectTrigger>
                                        <SelectContent>
                                            <SelectItem value="true">Ativo</SelectItem>
                                            <SelectItem value="false">Inativo</SelectItem>
                                        </SelectContent>
                                    </Select>
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />
                </div>
                <Button type="submit">Criar</Button>                
            </form>
        </Form>
    )

}   