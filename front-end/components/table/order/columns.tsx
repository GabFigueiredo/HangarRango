import { PedidoResponse } from "@/types/order/Pedido";
import { IconCircleCheckFilled, IconLoader } from "@tabler/icons-react";
import { ColumnDef } from "@tanstack/react-table";
import { Badge } from "../../ui/badge";
import { Label } from "../../ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "../../ui/select";
import { format } from "date-fns";
export const OrderColumns: ColumnDef<PedidoResponse>[] = [
  {
    accessorKey: "clienteNome",
    header: "Nome do cliente",
  },
  {
    accessorKey: "status",
    header: "Status do pedido",
    cell: ({ row }) => (
        <Badge variant="outline" className="text-muted-foreground px-1.5">
            {row.original.status === "CONCLUIDO" ? (
            <IconCircleCheckFilled className="fill-green-500 dark:fill-green-400" />
            ) : (
            <IconLoader />
            )}
            {row.original.status}
        </Badge>
    ),
  },
  {
    accessorKey: "preco",
    header: "Preço",
    cell: ({ row }) => {
        const amount = parseFloat(row.getValue("preco"))
        const formatted = new Intl.NumberFormat("pt-BR", {
            style: "currency",
            currency: "BRL",
        }).format(amount)

        return <p>{formatted}</p>
    }
  },
  {
    accessorKey: "formaPagamento",
    header: "Forma de Pagamento",
    cell: ({ row }) => (
        <div className="w-16">
            <Badge variant="outline" className="text-muted-foreground px-1.5">
                {row.original.formaPagamento}
            </Badge>
        </div>
    ),
  },
  {
    accessorKey: "statusPagamento",
    header: "Status do pagamento",
        cell: ({ row }) => {

      const isDone = row.original.statusPagamento === "EFETUADO"

      if (isDone) {
        return <Badge variant="outline" className="text-muted-foreground px-1.5">
          <IconCircleCheckFilled className="fill-green-500 dark:fill-green-400" />
          {row.original.statusPagamento}
        </Badge>
        
      }

      return (
        <>
          <Label className="sr-only">
            Status do pedido
          </Label>
          <Select>
            <SelectTrigger
              className="w-38 **:data-[slot=select-value]:block **:data-[slot=select-value]:truncate"
              size="sm"
            >
              <SelectValue placeholder="Pendente" />
            </SelectTrigger>
            <SelectContent align="end">
              <SelectItem value="CONCLUIDO">Concluído</SelectItem>
            </SelectContent>
          </Select>
        </>
      )
    },
  },
  {
    accessorKey: "createdAt",
    header: "Data",
    cell: ({ row }) => {
      const data = row.original.createdAt;
      return format(new Date(data), "dd/MM/yyyy");
    },
    filterFn: (row, columnId, filterValue: Date | undefined) => {
      if (!(filterValue instanceof Date) || isNaN(filterValue.getTime())) {
        return true; // se o filtro não for uma data válida, mantém a linha
      }

      const cellDate = new Date(row.getValue(columnId));

      return (
        cellDate.getFullYear() === filterValue.getFullYear() &&
        cellDate.getMonth() === filterValue.getMonth() &&
        cellDate.getDate() === filterValue.getDate()
      );
    },
  },
]


