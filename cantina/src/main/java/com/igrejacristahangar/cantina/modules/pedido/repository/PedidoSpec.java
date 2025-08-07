package com.igrejacristahangar.cantina.modules.pedido.repository;


import com.igrejacristahangar.cantina.modules.pedido.dto.FiltroPedidoDTO;
import com.igrejacristahangar.cantina.modules.pedido.enums.FORMA_PAGAMENTO;
import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS;
import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS_PAGAMENTO;
import com.igrejacristahangar.cantina.modules.pedido.model.Pedido;
import com.igrejacristahangar.cantina.modules.pedido.model.Pedido_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class PedidoSpec {

    private static Specification<Pedido> filtrarPorNome(String nome) {

        return (root, query, builder) -> {

            if (Objects.nonNull(nome)) {
                return builder.equal(root.get(Pedido_.CLIENTE_NOME), nome);
            }

            return null;
        };
    }

    private static Specification<Pedido> filtrarPorDataDeEfetuamento(LocalDate data) {

        return (root, query, builder) -> {

            if (Objects.nonNull(data)) {
                LocalDateTime inicioDoDia = data.atStartOfDay(); // 00:00
                LocalDateTime fimDoDia = data.atTime(LocalTime.MAX); // 23:59:59.999999999

                return builder.between(root.get(Pedido_.CREATED_AT), inicioDoDia, fimDoDia);
            }

            return null;
        };
    }

    private static Specification<Pedido> filtrarPorStatusDoPedido(STATUS status) {

        return (root, query, builder) -> {

            if (Objects.nonNull(status)) {
                return builder.equal(root.get(Pedido_.status), status);
            }

            return null;
        };
    }

    private static Specification<Pedido> filtrarPorFormaDePagamento(FORMA_PAGAMENTO formaPagamento) {

        return (root, query, builder) -> {

            if (Objects.nonNull(formaPagamento)) {
                return builder.equal(root.get(Pedido_.formaPagamento), formaPagamento);
            }

            return null;
        };
    }

    private static Specification<Pedido> filtrarPorStatusDePagamento(STATUS_PAGAMENTO statusPagamento) {

        return (root, query, builder) -> {

            if (Objects.nonNull(statusPagamento)) {
                return builder.equal(root.get(Pedido_.statusPagamento), statusPagamento);
            }

            return null;
        };
    }

    public static Specification<Pedido> filtrarPedidoPorTodosOsCampos(FiltroPedidoDTO filtro) {
        return filtrarPorNome(filtro.getNome())
                .and(filtrarPorDataDeEfetuamento(filtro.getData())
                        .and(filtrarPorStatusDoPedido(filtro.getPedidoStatus()).
                                and(filtrarPorFormaDePagamento(filtro.getFormaPagamento())
                                        .and(filtrarPorStatusDePagamento(filtro.getStatusPagamento())))));
}

}