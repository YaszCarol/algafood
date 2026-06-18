package com.algaworks.algafood.core.jackson;

import org.springframework.boot.jackson.JacksonComponent;
import org.springframework.data.domain.Page;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;



@JacksonComponent
public class PageJsonSerializer extends StdSerializer<Page<?>> {

    public PageJsonSerializer() {
        super(Page.class, false);
    }

    @Override
    public void serialize(Page<?> value, JsonGenerator gen, SerializationContext serializers) {
        gen.writeStartObject();
        gen.writePOJOProperty("conteudo", value.getContent());
        gen.writeNumberProperty("size", value.getSize());
        gen.writeNumberProperty("totalElements", value.getTotalElements());
        gen.writeNumberProperty("totalPages", value.getTotalPages());
        gen.writeNumberProperty("number", value.getNumber());
        gen.writeEndObject();
    }
}