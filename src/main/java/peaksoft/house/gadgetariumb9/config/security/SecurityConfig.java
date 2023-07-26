package peaksoft.house.gadgetariumb9.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors(cors -> cors.configurationSource(request -> {
      var corsConfiguration = new CorsConfiguration();
      corsConfiguration.addAllowedOrigin("*");
      corsConfiguration.addAllowedMethod("*");
      corsConfiguration.addAllowedHeader("*");
      return corsConfiguration;
    }))
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests((authorizeHttpRequests) ->
            authorizeHttpRequests
                .requestMatchers(
                        "/",
                    "/api/v1/auth/**",
                    "/swagger-ui/**",
                    "v3/api-docs/**",
                    "/api/**"
                )
                .permitAll()
                .anyRequest()
                .authenticated())
        .sessionManagement((sessionManagement) ->
            sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
