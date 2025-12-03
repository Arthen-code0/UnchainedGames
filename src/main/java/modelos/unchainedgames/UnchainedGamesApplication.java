package modelos.unchainedgames;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class UnchainedGamesApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnchainedGamesApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/product/**")  // o "/api/**" si usan ese patrón
                        .allowedOrigins("http://localhost:4200");
            }
        };
    }
}
