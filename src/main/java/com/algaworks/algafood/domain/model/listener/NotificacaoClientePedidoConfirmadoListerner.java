package com.algaworks.algafood.domain.model.listener;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoConfirmadoListerner {

    @Autowired
    private EnvioEmailService envioEmailService;

    @TransactionalEventListener
    //@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    //@EventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {

        Pedido pedido = event.getPedido();

         EnvioEmailService.Mensagem mensagem = EnvioEmailService.Mensagem.builder()
                .destinatario("yascarolmspworks@gmail.com\n")
                .assunto(pedido.getRestaurante().getNome() + "-" + " Pedido Confirmado")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .build();

        envioEmailService.enviar(mensagem);
    }
}
