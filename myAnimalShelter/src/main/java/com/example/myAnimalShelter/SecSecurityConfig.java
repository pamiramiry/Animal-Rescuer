package com.example.myAnimalShelter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig {

    private CustomShelterDetailsService userDetailService;
    @Autowired
    public SecSecurityConfig(CustomShelterDetailsService userDetailService){
        this.userDetailService = userDetailService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


   @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

       http.headers().frameOptions().disable();
                    ///////
       http.csrf().disable()
               .authorizeHttpRequests()
               .requestMatchers(HttpMethod.POST, "/auth/register/**")
               .permitAll()
               .requestMatchers(HttpMethod.GET, "/auth/register/**")
               .permitAll()
               .requestMatchers(HttpMethod.POST, "/auth/login/**")
               .authenticated()
               .requestMatchers(HttpMethod.GET, "/auth/login/**")
               .authenticated()
               .requestMatchers(HttpMethod.DELETE, "/auth/login/**")
               .authenticated()
               .anyRequest().permitAll()
               .and().formLogin().defaultSuccessUrl("/auth/login/user", true).and().logout().logoutSuccessUrl("/").and().httpBasic();
       return http.build();

      /* http.csrf().disable()
               .authorizeHttpRequests()
               .requestMatchers("/**")
               .permitAll()
               .anyRequest().authenticated()
               .and().httpBasic();
       return http.build();*/

    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
