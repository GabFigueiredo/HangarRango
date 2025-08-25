import { Label } from "@/components/ui/label";
import { PedidoResponse } from "@/types/order/Pedido";
import { Input } from "@/components/ui/input";
import { DatePicker } from "@/components/ui/date-picker";
import { Table } from "@tanstack/react-table";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";

interface OrderFilterFieldsProps {
  table: Table<PedidoResponse>;
}

export default function OrderFilterFields({ table }: OrderFilterFieldsProps) {
    return (
        <div className="flex flex-col md:flex-row gap-5 items-center py-4">
          <div className="flex flex-col gap-2 flex-1 w-full">
            <Label htmlFor="clienteNome">Nome</Label>
            <Input
              placeholder="Busque por nome"
              value={
                (table.getColumn("clienteNome")?.getFilterValue() as string) ?? ""
              }
              onChange={(event) =>
                table.getColumn("clienteNome")?.setFilterValue(event.target.value)
              }
              className="w-full py-2"
            />
          </div>
          <div className="flex flex-col gap-2 flex-1 w-full">
            <Label htmlFor="status">Status do pedido</Label>
            <Select
              value={
                (table.getColumn("status")?.getFilterValue() as string) ??
                ""
              }
              onValueChange={(event) =>
                table.getColumn("status")?.setFilterValue(event)
              }
            >
              <SelectTrigger className="w-full">
                <SelectValue placeholder="Marcado" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="PREPARANDO">Preparando</SelectItem>
                <SelectItem value="CONCLUIDO">Concluído</SelectItem>
              </SelectContent>
            </Select>
          </div>
          <div className="flex flex-col gap-2 flex-1 w-full">
            <Label htmlFor="formaPagamento">Forma de Pagamento</Label>
            <Select
              value={
                (table.getColumn("formaPagamento")?.getFilterValue() as string) ??
                ""
              }
              onValueChange={(event) =>
                table.getColumn("formaPagamento")?.setFilterValue(event)
              }
            >
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
          </div>
          <div className="flex flex-col gap-2 flex-1 w-full">
            <Label htmlFor="status_pedido">Status do Pedido</Label>
            <Select
              value={
                (table.getColumn("status")?.getFilterValue() as string) ?? ""
              }
              onValueChange={(event) =>
                table.getColumn("status")?.setFilterValue(event)
              }
            >
              <SelectTrigger className="w-full">
                <SelectValue placeholder="Pendente" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="CONCLUIDO">Concluído</SelectItem>
                <SelectItem value="PREPARANDO">Preparando</SelectItem>
              </SelectContent>
            </Select>
          </div>
          <div className="flex flex-col gap-2 flex-1 w-full">
            <Label htmlFor="createdAt">Data</Label>
            <DatePicker
              value={table.getColumn("createdAt")?.getFilterValue() as Date | undefined}
              onChange={(event) => table.getColumn("createdAt")?.setFilterValue(event)}
            />
          </div>
        </div>
    )
}