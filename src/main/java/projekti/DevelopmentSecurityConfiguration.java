package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class DevelopmentSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    
    /*
    @Override
    public void configure(WebSecurity sec) throws Exception {
        // Pyyntöjä ei tarkasteta, jos tästä rivistä kommentit veke
        // sec.ignoring().antMatchers("/**");                        
    }
    */
    
    @Override
    protected void configure(HttpSecurity sec) throws Exception {
        sec.authorizeRequests()
                .antMatchers("/index.html").permitAll()
                .antMatchers("/tervetuloa").permitAll()
                .antMatchers("/rekisteroidy").permitAll()
                .anyRequest().authenticated().and()
                .formLogin().permitAll().and()
                .formLogin().defaultSuccessUrl("/kayttajat").and()
                .logout().permitAll();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /*
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails kayttaja = User.withDefaultPasswordEncoder()
                .username("testi")
                .password("salasana")
                .authorities("USER")
                .build();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(kayttaja);
        return manager;
    }
    */
}
