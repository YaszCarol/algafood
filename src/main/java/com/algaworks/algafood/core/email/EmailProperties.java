package com.algaworks.algafood.core.email;

import com.algaworks.algafood.core.storage.StorageProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {

    @NotNull
    private String remetente;

    private String destinatario;

    @NotNull
    private Impl impl = Impl.FAKE;

    public enum Impl {
        SMTP, FAKE, SANDBOX
    }
}
