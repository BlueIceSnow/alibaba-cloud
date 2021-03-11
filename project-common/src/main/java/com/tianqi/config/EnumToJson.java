package com.tianqi.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.tianqi.ReqStatusEnum;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * 将枚举类型转换为JSON
 * @author yuantianqi
 */
@Configuration
public class EnumToJson {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer enumCustomizer() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.serializerByType(ReqStatusEnum.class, new JsonSerializer<ReqStatusEnum>() {
            @Override
            public void serialize(ReqStatusEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeStartObject();
                gen.writeNumberField("code", value.getCode());
                gen.writeStringField("message", value.getMessage());
                gen.writeEndObject();


            }
        });
    }
}
