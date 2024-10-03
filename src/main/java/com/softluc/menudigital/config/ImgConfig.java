package com.softluc.menudigital.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImgConfig implements WebMvcConfigurer {

    @Value("${ruta.imagenes}")
    private String rutaImagenes;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if(!rutaImagenes.endsWith("/")){
            rutaImagenes += "/";
        }

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:"+ rutaImagenes);

    }
}
