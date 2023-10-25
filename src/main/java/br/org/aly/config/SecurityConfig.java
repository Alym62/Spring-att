package br.org.aly.config;

import br.org.aly.services.UserServiceDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@Log4j2
public class SecurityConfig{
    private final UserServiceDetails userServiceDetails;

    public SecurityConfig(UserServiceDetails userServiceDetails) {
        this.userServiceDetails = userServiceDetails;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests( (authorize) -> authorize
                        .requestMatchers("/users/admin**").hasRole("ADMIN")
                        .requestMatchers("/users**").hasRole("USER")
                        .anyRequest()
                        .authenticated()
                ).formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        log.info("Password encoder{}", encoder.encode("123"));

        auth.userDetailsService(userServiceDetails).passwordEncoder(encoder);
    }
}
