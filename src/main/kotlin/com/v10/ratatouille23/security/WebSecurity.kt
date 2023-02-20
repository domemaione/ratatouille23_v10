package com.v10.ratatouille23.security

import com.v10.ratatouille23.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurity(
    private val userService: UserService,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val jwtCustomManager: JWTCustomManager,
): WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http?.cors()?.and()?.csrf()?.disable()?.exceptionHandling()
            ?.and()?.authorizeRequests()
            ?.antMatchers(HttpMethod.POST, "/api/auth/signup")?.permitAll()
          //  ?.antMatchers(HttpMethod.POST, "/api/auth/signup/op/resetpassword")?.permitAll()
            ?.antMatchers(HttpMethod.GET, "/api/auth/validate/user/**")?.permitAll()
            ?.antMatchers(HttpMethod.GET, "/api/auth/check/token/*")?.permitAll()
            ?.antMatchers(HttpMethod.GET, "/api/user/all/*")?.permitAll()
            ?.antMatchers(HttpMethod.GET, "/api/user/*")?.permitAll()
            ?.antMatchers(HttpMethod.GET, "/api/restaurant/{id}")?.permitAll()
            ?.anyRequest()?.authenticated()
            ?.and()
            ?.addFilter(JWTAuthenticationFilter(authenticationManager(), jwtCustomManager))
            ?.addFilter(JWTAuthorizationFilter(authenticationManager(), jwtCustomManager, userService))
            ?.sessionManagement()
            ?.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userService)?.passwordEncoder(bCryptPasswordEncoder)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val source = UrlBasedCorsConfigurationSource()
        val corsConfiguration = CorsConfiguration().applyPermitDefaultValues()
        source.registerCorsConfiguration("/**", corsConfiguration)
        return source
    }
}