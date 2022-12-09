package leo.fr.demospringmvc.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{

		PasswordEncoder passwordEncoder = passwordEncoder();
		
		System.out.println("Le voici : " + passwordEncoder.encode("1234"));	

		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("Select username as principal, "
					+ "				password as credentials, "
					+ "				active from users  where username=?")
			.authoritiesByUsernameQuery("Select  username as principal,"
					+ "					role as role from users_roles "
					+ "					where username=?")
			.passwordEncoder(passwordEncoder)
			.rolePrefix("ROLE_");
		
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {		

		http.formLogin().loginPage("/login");	
		http.authorizeRequests().antMatchers("/save**/**", "/delete**/**" , "/form**/**" ).hasRole("ADMIN");
		http.authorizeRequests().antMatchers("/patients**/**" ).hasRole("USER");
		http.authorizeRequests().antMatchers("/users**/**", "/login", "/webjars/**" ).permitAll();
			
		http.authorizeRequests().anyRequest().authenticated();
		http.exceptionHandling().accessDeniedPage("/notAuthorized");	
	}
	
	/*
	 *      @Bean
     * 		public MyBean myBean() {
     *   		 // instantiate and configure MyBean obj
     *   		 return obj;
     *		}
	 */	
	@Bean 
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
