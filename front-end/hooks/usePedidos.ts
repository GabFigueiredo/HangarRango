"use client";

import React from "react";
import { Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";

import { useQueryClient } from "@tanstack/react-query";
import { PreparacaoResponse } from "@/types/order/Pedido";

const apiUrl = process.env.NEXT_PUBLIC_API_URL;

export function usePedidos(wsUrl = `${apiUrl}/preparacao`) {
  const queryClient = useQueryClient();

  React.useEffect(() => {
    const socket = new SockJS(wsUrl);
    const client = Stomp.over(socket);

    function AtualizarLista(Pedido: PreparacaoResponse) {
      queryClient.setQueryData(
        ["pedidos-pendentes"],
        (oldData?: PreparacaoResponse[]) => {
          if (!oldData) return [Pedido];

          const index = oldData.findIndex((item) => item.id === Pedido.id);

          if (index === -1) {
            return [...oldData, Pedido];
          } else {
            // Se já existe, atualiza o item
            const updated = [...oldData];
            updated[index] = Pedido;
            return updated;
          }
        }
      );
    }

    client.connect({}, () => {
      console.log("Conectado ao WebSocket!");

      client.subscribe("/cantina/preparacao", (message) => {
        const receivedMessage = JSON.parse(message.body);
        AtualizarLista(receivedMessage);
      });
    });

    return () => {
      if (client.connected) {
        client.disconnect(() => {
          console.log("Desconectado do WebSocket");
        });
      }
    };
  }, [wsUrl, queryClient]);
}
