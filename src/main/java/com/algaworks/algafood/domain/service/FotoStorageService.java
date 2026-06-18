package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    void armazenar(NovaFoto novaFoto);

    void substituir(String nomeArquivoAntigo, NovaFoto novaFoto);

    void remover(String nomeArquivo);

    FotoRecuperada recuperar(String nomeArquivo);

    default String gerarNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID().toString() + "_" + nomeOriginal;
    }

    @Builder
    @Getter
    @Setter
    class NovaFoto {
        private String contentType;
        private String nomeArquivo;
        private InputStream inputStream;
    }

    @Builder
    @Getter
    @Setter
    class FotoRecuperada {
        private InputStream inputStream;
        private String url;

        public boolean temUrl(){
            return getUrl() != null;
        }

        public boolean temInputStream(){
            return getInputStream() != null;
        }
    }
}
