'use client';

import { Button } from "@/components/ui/button";
import { DialogFooter } from "@/components/ui/dialog";
import { useCart } from "@/context/CartContext";
import { useClipboard } from "@/hooks/useClipboard";
import { Copy } from "lucide-react";
import Link from "next/link";
import { useSearchParams } from "next/navigation";

export default function Content() {
  const { handleCopy, isCopied } = useClipboard({});
  const { clearCart } = useCart();
  const params = useSearchParams();

  const type = params.get("type");
  const pixCopiaECola = params.get("pixCopiaECola");

  return (
    <div className="font-inter flex flex-col items-center gap-2 p-4 max-w-[400px] mx-auto">
      <div className="flex flex-col items-center justify-center">
        <h1 className="font-bold">Quase lá..</h1>
        <h2 className="text-center">
          Você precisa concluir o pagamento para o pedido começar a ser
          preparado.
        </h2>
      </div>
      <div className="flex flex-col items-center">
        {type === "PAGAMENTO_MANUAL" ? (
          <p className="text-center">Efetue o pagamento no balcão para que seu pedido seja produzido.</p>
        ) : (
          <div className="flex w-full justify-center flex-col items-center gap-2">
            <Button
              onClick={() => handleCopy(pixCopiaECola ?? "")}
              className="cursor-pointer"
              variant={"outline"}
            >
              {isCopied ? "Código copiado!" : "Copiar código PIX"}
              <Copy />
            </Button>
          </div>
        )}
      </div>
      <DialogFooter className="sm:justify-center">
        <div className="text-center text-[14px] text-gray-400">
          <p>Após concluir o pagamento,</p>
          <Link
            onClick={() => clearCart()}
            className="italic underline"
            href={"/cantina/meus-pedidos"}
          >
            Veja seu pedido
          </Link>
        </div>
      </DialogFooter>
    </div>
  );
}
