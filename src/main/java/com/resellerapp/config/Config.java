package com.resellerapp.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class Config {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public ModelMapper createModelMapper(){
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        modelMapper.addConverter(mappingContext ->
                        LocalDate.parse(mappingContext.getSource(),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                String.class,
                LocalDate.class);

        return modelMapper;
    }

}
