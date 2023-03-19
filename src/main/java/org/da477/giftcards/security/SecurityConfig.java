package org.da477.giftcards.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    private final UserDetailsService userDetailService;

    //https://bcrypt-generator.com/
    public static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder(11);

    @Autowired(required = true)
    public SecurityConfig(UserDetailsService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()

                .antMatchers("/api/v1/cards/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/auth/admin/**").hasRole("ADMIN")
                .antMatchers("/cards/**").hasRole("ADMIN")

                .antMatchers("/").permitAll()
                .anyRequest()
                .authenticated()
                .and()

                .formLogin()
                .loginPage("/auth/login").permitAll()
                .defaultSuccessUrl("/", true)
                .failureUrl("/auth/login?error")

                .usernameParameter("email")
                .passwordParameter("password")

                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))

                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/")
                .and()
                // custom 403 access denied handler
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        ;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
        daoAuthProvider.setPasswordEncoder(PASSWORD_ENCODER);
        daoAuthProvider.setUserDetailsService(userDetailService);
        return daoAuthProvider;
    }
}
