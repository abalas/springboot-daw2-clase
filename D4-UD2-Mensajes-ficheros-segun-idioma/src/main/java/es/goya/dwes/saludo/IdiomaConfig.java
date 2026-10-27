package es.goya.dwes.saludo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class IdiomaConfig implements WebMvcConfigurer {

    // El método DEBE llamarse localeResolver: así sustituye al de Spring Boot
    @Bean
    public LocaleResolver localeResolver() {
        // Guarda en la sesión el idioma elegido con ?lang=.
        // Mientras no se elija, usa la cabecera Accept-Language del navegador.
        return new SessionLocaleResolver();
    }

    // Cambia el idioma cuando la URL lleva ?lang=es, ?lang=en, ?lang=fr...
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}