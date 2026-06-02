package com.algaworks.algafood.api.controller.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),

    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),

    REGRA_DE_NEGOCIO("/regra-nao-atendida", "Regra de negócio nao atendida"),

    PARAMETRO_INVALIDO("/parametro-url-invalido", "Parâmetro de URL inválido"),

    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),

    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),

    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso");

    private String title;
    private String uri;

    ProblemType(String path, String title){
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
    }

}
