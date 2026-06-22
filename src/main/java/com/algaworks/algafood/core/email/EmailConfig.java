package com.algaworks.algafood.core.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infrastructure.respository.service.FakeEnvioEmailService;
import com.algaworks.algafood.infrastructure.respository.service.SendBoxEnvioEmailService;
import com.algaworks.algafood.infrastructure.respository.service.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailService(){
        if(EmailProperties.Impl.SMTP.equals(emailProperties.getImpl())){
            return new SmtpEnvioEmailService();
        }else if(EmailProperties.Impl.SANDBOX.equals(emailProperties.getImpl())){
            return new SendBoxEnvioEmailService();
        }
        return new FakeEnvioEmailService();
    }
}
