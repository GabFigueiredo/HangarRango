import { DatePicker } from "./ui/date-picker";
import { Input } from "./ui/input";
import { Button } from "./ui/button";
import { Search } from "lucide-react";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "./ui/select";
import { Label } from "./ui/label";

export default function SearchOrdersForm() {
  return (
    <form className="flex flex-col md:flex-row w-full gap-2 items-end">
        <div className="flex flex-col gap-2 flex-1">
            <Label htmlFor="nome">Nome</Label>
            <Input className="w-full" placeholder="Nome" />
        </div>
        <div className="flex flex-col gap-2 flex-1">
            <Label htmlFor="dia">Dia</Label>
            <DatePicker />
        </div>
        <div className="flex flex-col gap-2 flex-1">
            <Label htmlFor="status_pedido">Status do Pedido</Label>
            <Select>
                <SelectTrigger className="w-full">
                <SelectValue placeholder="Pendente" />
                </SelectTrigger>
                <SelectContent>
                <SelectItem value="light">Concluído</SelectItem>
                <SelectItem value="dark">Pendente</SelectItem>
                <SelectItem value="system">Cancelado</SelectItem>
                </SelectContent>
            </Select>
        </div>
        <div className="flex flex-col gap-2 flex-1">
            <Label htmlFor="status_pedido">Forma de Pagamento</Label>
            <Select>
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
        <div className="flex flex-col gap-2 flex-1">
            <Label htmlFor="status_pedido">Status de Pagamento</Label>
            <Select>
                <SelectTrigger className="w-full">
                <SelectValue placeholder="Pendentee" />
                </SelectTrigger>
                <SelectContent>
                <SelectItem value="light">Concluído</SelectItem>
                <SelectItem value="dark">Pendente</SelectItem>
                </SelectContent>
            </Select>   
        </div>
      <Button type="submit" className="" variant="outline">
        <Search />
        <p>Pesquisar</p>
      </Button>
    </form>
  );
}
