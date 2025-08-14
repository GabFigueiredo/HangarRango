import { CardDescription } from "./ui/card";
import { Button } from "./ui/button";
import { CircleCheck } from "lucide-react";
import { PreparacaoResponse } from "@/types/order/Pedido";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { setOrderStatus } from "@/service/orders/set-orders-status";
import { PedidoStatus } from "@/enums/order/pedidoStatus.enum";
import { toast } from "sonner";

interface StageOrderProps {
     Pedido: PreparacaoResponse
}

export default function StageOrder({ Pedido }: StageOrderProps) {
    const queryClient = useQueryClient();
    
    const { mutate, isSuccess, isError } = useMutation({
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

    if (isSuccess) {
        queryClient
        toast.success("Pedido concluído com sucesso!")
    }

    if (isError) {
        toast.error("Não foi possível concluir o pedido")
    }
    
    return (
        <div className="bg-card text-card-foreground flex flex-col rounded-xl border shadow-sm min-h-[200px]">
            <div className="bg-transparent w-full h-fit border-b rounded-xl p-3 flex items-center justify-between">
                <CardDescription className="flex text-foreground text-3xl font-semibold gap-1">
                    <p>{Pedido.numeroPedido.toString().padStart(3, "0")}</p>
                </CardDescription>
                <Button onClick={handleOrderConcludes} variant={"outline"}>
                    <CircleCheck />
                    <p>Concluir</p>
                </Button>
            </div>
            <div className="p-3 w-full h-full">
                {Pedido.listaProdutos.map(item => {
                    return (
                        <div key={item.nome} className="flex items-center justify-between">
                            <p className="text-xl md:text-xl">{item.nome}</p>
                            <p className="text-xl md:text-xl text-primary">1x</p>
                        </div>
                    )
                })}
            </div>
        </div>
    )
}


// *:data-[slot=card]:from-primary/5 *:data-[slot=card]:to-card dark:*:data-[slot=card]:bg-card @container/card flex flex-col flex-1