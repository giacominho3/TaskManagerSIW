package it.uniroma3.siw.taskmanager.authentication;

import javax.activation.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static it.uniroma3.siw.taskmanager.model.Credentials.ADMIN_ROLE;; 

@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired 
	DataSource datasource;
	
	@Override
	protected void configure (HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/", "/index", "/login", "/users/registers").permitAll()
			.antMatchers(HttpMethod.POST, "/login", "/users/registers").permitAll()
			.antMatchers(HttpMethod.GET, "/admin").hasAnyAuthority(ADMIN_ROLE)
			.anyRequest().authenticated()
			.and().formLogin()
			.defaultSuccessUrl("/home")
			.and().logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl ("/index");
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication()
			.dataSource(this.datasource)
			.authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username = ?")
			.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username = ?");
	}

}
