package fi.moviemanager.config;

import fi.moviemanager.repository.KayttajaVarasto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class TietoturvaAsetukset {
    @Bean
    PasswordEncoder salasananKoodaaja() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService kayttajanTietopalvelu(KayttajaVarasto kayttajaVarasto) {
        return kayttajatunnus -> kayttajaVarasto.findByKayttajatunnus(kayttajatunnus)
                .map(kayttaja -> User.withUsername(kayttaja.getUsername())
                        .password(kayttaja.getPassword())
                        .roles(kayttaja.getRole())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Käyttäjää ei löytynyt."));
    }

    @Bean
    DaoAuthenticationProvider todennusPalveluntarjoaja(
            UserDetailsService kayttajanTietopalvelu, PasswordEncoder salasananKoodaaja) {
        DaoAuthenticationProvider palveluntarjoaja = new DaoAuthenticationProvider();
        palveluntarjoaja.setUserDetailsService(kayttajanTietopalvelu);
        palveluntarjoaja.setPasswordEncoder(salasananKoodaaja);
        return palveluntarjoaja;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                             DaoAuthenticationProvider todennusPalveluntarjoaja)
            throws Exception {
        http.authenticationProvider(todennusPalveluntarjoaja)
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/elokuvat", "/elokuvat/**", "/login",
                        "/css/**", "/api/elokuvat", "/api/elokuvat/**", "/h2-console/**").permitAll()
                .requestMatchers("/admin/**", "/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/admin", true)
                .permitAll()
            )
            .logout(logout -> logout.logoutSuccessUrl("/elokuvat?logout").permitAll())
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }
}
