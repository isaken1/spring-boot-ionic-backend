package com.isaackennedy.curso.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaackennedy.curso.domain.PagamentoComBoleto;
import com.isaackennedy.curso.domain.PagamentoComCartao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder() {
            public void configure(ObjectMapper mapper) {
                mapper.registerSubtypes(PagamentoComBoleto.class);
                mapper.registerSubtypes(PagamentoComCartao.class);
                super.configure(mapper);
            }
        };
    }
}
