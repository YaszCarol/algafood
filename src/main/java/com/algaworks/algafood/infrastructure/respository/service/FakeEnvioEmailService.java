package com.algaworks.algafood.infrastructure.respository.service;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

@Slf4j
public class FakeEnvioEmailService implements EnvioEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private FreeMarkerConfig freeMarkerConfig;

    @Override
    public void enviar(Mensagem mensagem) {

        try {
            String corpo = processarTemplate(mensagem);

            log.info("\n[FAKE E-MAIL] Para: {}\nAssunto: {}\nCorpo:\n{}",
                    mensagem.getDestinatarios(),
                    mensagem.getAssunto(),
                    corpo);

        } catch (Exception e) {
            throw new EmailException("Nao foi possivel enviar e-mail", e);
        }
    }

    private String processarTemplate(Mensagem mensagem) {
        try {
            Template template = freeMarkerConfig.getConfiguration().getTemplate(mensagem.getCorpo());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
        } catch (Exception e) {
            throw new EmailException("Nao foi possivel gerar o template", e);
        }
    }
}
