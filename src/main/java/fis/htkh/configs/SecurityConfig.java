package fis.htkh.configs;

import fis.htkh.services.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Autowired
    private IPermissionService permissionService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login").anonymous()
            .antMatchers("/static/**").permitAll()
            .antMatchers("/notfound").anonymous()
            .antMatchers("/", "/admin/**", "/api/**").authenticated().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
                    FilterInvocationSecurityMetadataSource newSource = new CustomSecurityMetadataSource(permissionService);
                    AccessDecisionManager accessDecisionManager = new CustomAccessDecisionManager();
                    fsi.setSecurityMetadataSource(newSource);
                    fsi.setAccessDecisionManager(accessDecisionManager);
                    return fsi;
                }
            })
            .anyRequest().permitAll()
            .and().formLogin().loginPage("/login")
            .defaultSuccessUrl("/admin/home").usernameParameter("username").passwordParameter("password")
            .failureUrl("/login?error").and().logout().logoutSuccessUrl("/login?logout")
            .and().exceptionHandling().accessDeniedPage("/notfound")
            .and().csrf().ignoringAntMatchers("/api/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
}
