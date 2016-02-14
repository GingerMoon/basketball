package basketball;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
    @Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.headers().disable()
        	.csrf().disable()
            .authorizeRequests()
            	.antMatchers("/admin/","/admin/**", "/account/","/account/**").hasRole("ADMIN")
            	.antMatchers("/home", "/").permitAll()
            	.antMatchers("/js/**", "/jquery-mobile/**", "/jquery-ui-1.11.4.custom/**", "/images/**", "/toastr/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
            	.loginProcessingUrl("/login")
                .loginPage("/")
                .permitAll()
                .and()
            .logout()
            	.logoutRequestMatcher( new AntPathRequestMatcher( "/logout" ) )
                .deleteCookies( "JSESSIONID" )
                .invalidateHttpSession( true )
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, UserDetailsService userDetailsService) throws Exception {
    	auth
    	.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
    }
}