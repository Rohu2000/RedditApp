package com.example.redditapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MediaConfig implements WebMvcConfigurer {
    @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/uploads/**")
                    .addResourceLocations("/home/anukrishna/Documents/SpringBoot/REDDIT_APP/src/main/resources/static/media/")
                    .setCachePeriod(0);
        }

        @Bean
        public MultipartResolver multipartResolver() {
            return new StandardServletMultipartResolver();
        }


}
