package com.metamafitness.fitnessbackend.config;

import com.metamafitness.fitnessbackend.exception.BusinessException;
import com.metamafitness.fitnessbackend.model.GenericEnum;
import com.metamafitness.fitnessbackend.security.JwtAuthorizationFilter;
import com.metamafitness.fitnessbackend.utils.JwtProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.List;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.DAYS;
import static org.springframework.security.config.http.SessionCreationPolicy.IF_REQUIRED;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Value("${cors.allowed-origins}")
    private List<String> corsAllowedOrigins;

    @Value("${cors.allowed-heathers}")
    private List<String> corsAllowedHeathers;

    @Value("${cors.allowed-methods}")
    private List<String> corsAllowedMethods;



    @Configuration
    @Order(1)
    public static class JwtSecurityConfiguration {

        @Value("${jwt.auth.white-list}")
        private List<String> jwtAuthWhiteList;

        @Value("${jwt.auth.admin}")
        private List<String> adminEndPointsList;

        @Value("${jwt.auth.trainer}")
        private List<String> trainerEndPointsList;

        @Value("${jwt.auth.user}")
        private List<String> userEndPointsList;



        private final JwtProvider jwtProvider;
        private final HandlerExceptionResolver handlerExceptionResolver;

        public JwtSecurityConfiguration(JwtProvider jwtProvider, HandlerExceptionResolver handlerExceptionResolver) {
            this.jwtProvider = jwtProvider;
            this.handlerExceptionResolver = handlerExceptionResolver;
        }

        @Bean
        public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception{

            http
                    .cors()
                    .and()
                    .csrf().disable()
                    .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .antMatcher("/api/**")
                    .authorizeHttpRequests((authorize) -> authorize
                            .antMatchers(adminEndPointsList.toArray(String[]::new)).hasRole(GenericEnum.RoleName.ADMIN.name())
                            .antMatchers(trainerEndPointsList.toArray(String[]::new)).hasRole(GenericEnum.RoleName.TRAINER.name())
                            .antMatchers(userEndPointsList.toArray(String[]::new)).hasRole(GenericEnum.RoleName.USER.name())
                            .antMatchers(jwtAuthWhiteList.toArray(String[]::new)).permitAll()
                            .anyRequest().authenticated()
                    )
                    .addFilterBefore(new JwtAuthorizationFilter(jwtProvider, jwtAuthWhiteList, handlerExceptionResolver), UsernamePasswordAuthenticationFilter.class);

            return http.build();
        }
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Configuration
    @Order(2)
    public static class FormSecurityConfiguration {

        @Value("${form.auth.white-list}")
        private List<String> formAuthWhiteList;

        @Value("${form.auth.block-list}")
        private List<String> formAuthBlockList;

        @Value("${form.login.remember-me.key}")
        private String rememberMeKey;

        @Value("${form.login.remember-me.token-validity-in-days}")
        private int rememberMeTokenValidityInDays;

        @Value("${form.success.url}")
        private String formSuccessUrl;

        @Value("${form.logout.cookies-to-clear}")
        private String[] formLogoutCookiesToClear;

        @Bean
        public SecurityFilterChain formFilterChain(HttpSecurity http) throws Exception {

            http
                    .csrf().disable()
                    .sessionManagement((session) -> session.sessionCreationPolicy(IF_REQUIRED))
                    .requestMatchers()
                    .antMatchers(Stream.concat(formAuthWhiteList.stream(), formAuthBlockList.stream()).toArray(String[]::new))
                    .and()
                    .authorizeHttpRequests((authorize) -> {
                        try {
                            authorize
                                    .antMatchers(formAuthWhiteList.toArray(String[]::new)).permitAll()
                                    .antMatchers(formAuthBlockList.toArray(String[]::new)).hasRole(GenericEnum.RoleName.DEV.name())
                                    .anyRequest().authenticated();
                        } catch (Exception e) {
                            throw new BusinessException(e.getMessage(), e.getCause(), null, null);
                        }
                    })
                    .formLogin((form) -> form.defaultSuccessUrl(formSuccessUrl))
                    .rememberMe(rememberMe -> rememberMe
                            .key(rememberMeKey)
                            .tokenValiditySeconds((int) DAYS.toSeconds(rememberMeTokenValidityInDays))
                    )
                    .logout((logout) -> logout
                            .logoutSuccessUrl(formSuccessUrl)
                            .invalidateHttpSession(true)
                            .deleteCookies(formLogoutCookiesToClear)
                    );

            return http.build();
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(corsAllowedOrigins);
        //or any domain that you want to restrict to
        configuration.setAllowedHeaders(corsAllowedHeathers);
        configuration.setAllowedMethods(corsAllowedMethods);
        //Add the method support as you like
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}