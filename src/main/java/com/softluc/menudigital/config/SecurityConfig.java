package com.softluc.menudigital.config;

import com.softluc.menudigital.jwt.JwtUtils;
import com.softluc.menudigital.jwt.JwtValidator;
import com.softluc.menudigital.servicio.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(http ->{
                    //Config los end publicos
                    http.requestMatchers(HttpMethod.GET, "/producto/**").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/producto/getAllByName/**").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/categoria").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/images/**").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/plan").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/provincia").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/localidad/**").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/usuario/localidad/**").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/usuario/provincia/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/auth/login").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/auth/register").permitAll();

                    //Config los end priv
                    http.anyRequest().authenticated();
                })
                .cors( cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtValidator(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:5173", "http://149.50.148.20:3000", "http://149.50.148.20:5173"));
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
     public AuthenticationProvider authenticationProvider(UserDetailServiceImp userDetailService){
         DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
         provider.setPasswordEncoder(passwordEncoder());
         provider.setUserDetailsService(userDetailService);
         return provider;
     }

     @Bean
     public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
     }
        
}
