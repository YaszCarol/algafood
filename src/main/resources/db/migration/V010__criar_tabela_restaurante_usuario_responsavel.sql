CREATE TABLE restaurante_usuario_responsavel(
    restaurante_id bigint not null,
    usuario_id bigint not null,

    CONSTRAINT pk_restaurante_usuario_responsavel
        PRIMARY KEY (restaurante_id, usuario_id),

    CONSTRAINT fk_restaurante_usuario_restaurante
        FOREIGN KEY (restaurante_id)
        REFERENCES restaurante(id),

    CONSTRAINT fk_restaurante_usuario_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario(id)

) engine=InnoDB default charset=utf8;

