package modelos.unchainedgames.security;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.repository.IUsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@AllArgsConstructor
public class MecanismoSeguridad {

    private final IUsuarioRepository repositorio;

    @Bean
    public UserDetailsService userDetailsService() {
        return repositorio :: findTopByEmailEquals;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(GetPasswordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder GetPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
