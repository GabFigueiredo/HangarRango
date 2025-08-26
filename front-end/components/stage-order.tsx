import { CardDescription } from "./ui/card";
import { Button } from "./ui/button";
import { CircleCheck } from "lucide-react";
import { PedidoResponse, PreparacaoResponse } from "@/types/order/Pedido";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { setOrderStatus } from "@/service/orders/set-orders-status";
import { PedidoStatus } from "@/enums/order/pedidoStatus.enum";
import { toast } from "sonner";
import { Separator } from "@radix-ui/react-dropdown-menu";

interface StageOrderProps {
     Pedido: PedidoResponse
}

export default function StageOrder({ Pedido }: StageOrderProps) {
    const queryClient = useQueryClient();
    
    const { mutate } = useMutation({
        mutationFn: () => setOrderStatus({
            clientId: Pedido.id,
            pedidoStatus: PedidoStatus.CONCLUIDO
        }),
        onSuccess: () => {
            // Atualiza o cache removendo o pedido concluído
            queryClient.setQueryData<PreparacaoResponse[]>(["pedidos-pendentes"], (oldData: PreparacaoResponse[] | undefined) => {
                if (!oldData) return [];
                return oldData.filter(p => p.id !== Pedido.id);
            });
            toast.success("Pedido concluído com sucesso!");
        },
        onError: () => {
            toast.error("Não foi possível concluir o pedido");
        }
    })

    function handleOrderConcludes() {
        mutate()
    }
    
    return (
        <div className="bg-card text-card-foreground flex flex-col rounded-xl border shadow-sm min-h-[200px]">
            <div className="bg-transparent w-full h-fit border-b rounded-xl p-3 flex items-center justify-between">
                <CardDescription className="flex text-foreground gap-1 items-center">
                    <p className="text-3xl font-semibold ">{Pedido.numeroPedido.toString().padStart(3, "0")}</p>
                    <Separator />
                    <p className="text-sm text-primary w-min">{Pedido.clienteNome}</p>
                </CardDescription>
                <Button onClick={handleOrderConcludes} variant={"outline"}>
                    <CircleCheck />
                    <p>Concluir</p>
                </Button>
            </div>
            <div className="p-3 w-full h-full">
                {Pedido.itens.map(item => {
                    return (
                        <div key={item.nomeProduto} className="flex items-center justify-between">
                            <p className="text-xl md:text-xl">{item.nomeProduto}</p>
                            <p className="text-xl md:text-xl text-primary">1x</p>
                        </div>
                    )
                })}
            </div>
        </div>
    )
}