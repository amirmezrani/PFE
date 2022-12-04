package tn.isimg.pfe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tn.isimg.pfe.security.JwtAuthenticationEntryPoint;
import tn.isimg.pfe.security.JwtAuthenticationFilter;
import tn.isimg.pfe.security.JwtAuthenticationFilterMedecin;
import tn.isimg.pfe.security.MedecinDetailsService;

@Configuration
@Order(2)
public class SecurityConfigMedecin extends WebSecurityConfigurerAdapter {

        @Autowired
        MedecinDetailsService medecinDetailsService;


        @Autowired
        private JwtAuthenticationEntryPoint unauthorizedHandler;
        @Autowired
        private PasswordEncoder passwordEncoder;
        @Bean
        public JwtAuthenticationFilterMedecin jwtAuthenticationFilterMedecin() {
            return new JwtAuthenticationFilterMedecin();
        }

        @Override
        public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
            authenticationManagerBuilder
                    .userDetailsService(medecinDetailsService)
                    .passwordEncoder(passwordEncoder);
        }

        @Bean("authenticationManagerBeanMedecin")
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .cors()
                    .and()
                    .csrf()
                    .disable()
                    .exceptionHandling()
                    .authenticationEntryPoint(unauthorizedHandler)
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .requestMatchers()
                    .antMatchers("/api/medecins")
                    .and()
                    .requestMatchers()
                    .antMatchers("/api/medecins/**")
                    .and()
                    .authorizeRequests()
                    .antMatchers("/",
                            "/favicon.ico",
                            "/**/*.png",
                            "/**/*.gif",
                            "/**/*.svg",
                            "/**/*.jpg",
                            "/**/*.html",
                            "/**/*.css",
                            "/**/*.js")
                    .permitAll()
                    .antMatchers(HttpMethod.POST, "/api/medecins")
                    .permitAll()
                    .antMatchers(HttpMethod.GET, "/api/medecins")
                    .permitAll()
                    .antMatchers("/api/villes")
                    .permitAll()
                    .antMatchers( "/api/specialites")
                    .permitAll()
                    .antMatchers("/api/auth/**/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated();

            // Add our custom JWT security filter
            http.addFilterBefore(jwtAuthenticationFilterMedecin(), UsernamePasswordAuthenticationFilter.class);

        }
    }



