package it.cabsb.config;

import it.cabsb.security.SpringAuthenticationProvider;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Order(4)
@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"it.cabsb.security"})
@EnableTransactionManagement(proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private DataSource dataSource;
	
	@Autowired
	public SpringAuthenticationProvider springAuthenticationProvider;
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }

	@Override
	protected void configure(AuthenticationManagerBuilder auth)	throws Exception {
		auth.authenticationProvider(springAuthenticationProvider);
	}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
		.logout().logoutUrl("/j_spring_security_logout").invalidateHttpSession(true).logoutSuccessUrl("/?logout").deleteCookies("JSESSIONID")
		.and()
		.authorizeRequests().antMatchers("/control_panel/**").hasRole("USER")
		.and()
		.authorizeRequests().antMatchers("/api/ws/get-*/**").permitAll()
		.and()
		.formLogin().loginPage("/login").permitAll();
    }
  
}
