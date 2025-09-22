import { DialogTitle } from "@radix-ui/react-dialog";
import { DialogContent, DialogDescription, DialogFooter, DialogHeader } from "./ui/dialog";
import Link from "next/link";
import { Badge } from "./ui/badge";
import { useClipboard } from "@/hooks/useClipboard";
import { Button } from "./ui/button";
import { Copy } from "lucide-react";
import { useCart } from "@/context/CartContext";

interface AlertPaymentProps {
    type: "PIX" | "PAGAMENTO_MANUAL";
    pixCopiaECola: string;
}

export default function AlertPayment(props: AlertPaymentProps) {
    const { handleCopy, isCopied } = useClipboard({})
    const { clearCart } = useCart();
    
    return (
        <DialogContent>
            <DialogHeader className="flex flex-col items-center jutify-center">
                <DialogTitle>Quase lá..</DialogTitle>
                <DialogDescription className="text-center">Você precisa concluir o pagamento para o pedido começar a ser preparado.</DialogDescription>
            </DialogHeader>
            <div>
                {props.type === "PAGAMENTO_MANUAL" ?
                    <p>Efetue o pagamento balcão para que seu pedido seja produzido</p>
                    :
                    <div className="flex w-full justify-center flex-col items-center gap-2">
                        <Button onClick={() => handleCopy(props.pixCopiaECola)} className="cursor-pointer" variant={"outline"}>
                            {isCopied ? "Código copiado!" : "Copiar código PIX"}
                            <Copy />
                        </Button>
                    </div>
                }
            </div>
            <DialogFooter className="sm:justify-center">
                <div className="text-center text-[14px] text-gray-400">
                    <p>Após concluir o pagamento,</p>
                    <Link onClick={() => clearCart()} className="italic underline" href={"/cantina/meus-pedidos"}>Veja seu pedido</Link>
                </div>
            </DialogFooter>
        </DialogContent>
    )
}