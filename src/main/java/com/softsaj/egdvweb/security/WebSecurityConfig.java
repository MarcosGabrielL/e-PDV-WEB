/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.security;

/**
 *
 * @author Marcos
 */
import com.softsaj.egdvweb.security.JwtFilter;
import com.softsaj.egdvweb.security.CustomUserDetailsService;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    
    
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;
    
       
@Bean
public RestTemplate restTemplate() {
    return new RestTemplate();
}

@Bean
public CloseableHttpClient httpClient() {
    HttpClientBuilder builder = HttpClientBuilder.create();
    //builder.setEverything(everything); // configure it
    CloseableHttpClient httpClient = builder.build();
    return httpClient;
}
    

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());


        http
	  .csrf().disable()
                .authorizeRequests()
                .antMatchers("/forgot_password/**","/reset_password/**",  "/process_register/**", "/message/**").permitAll()
                .antMatchers("/users","/perfispagamento/**","/vendedores/**","/islogged/**", 
                             "/*","/cinefilos/**", "/textoes/**","/register","/user/**",
                             "/auth/**","/files/**", "/file/**","/auth/**","/resultpagos/**",
                             "/preferences/**",
                             "/eventos/**","/vendidos/**","/files/**", "/filelist/**", "/download/**",
                             "/create/**","/generic/**","/notifications/**","/produtos/**", 
                             "/user/**", "/cinefilos/**", "/textoes/**", "/uploadFile/**", "/file/**",
		             "/loja/**", "/vendas/**", "/fretes/**", "/dominios/**","/register" ).authenticated()
                .antMatchers("/authenticate")
                .permitAll().anyRequest().authenticated()
                .and().exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);;
    }
    
     
}