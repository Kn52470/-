package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.example.demo.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
		return new MvcRequestMatcher.Builder(introspector);
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {

		password();
		
		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/login").permitAll()//直リンクok
				.requestMatchers("/register").permitAll()//直リンクok
				.requestMatchers("/updatePass").permitAll()//直リンクok
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.requestMatchers(mvc.pattern("/login")).permitAll()
				.anyRequest().authenticated());

		//ログイン処理
		http.formLogin(login -> login
				.loginProcessingUrl("/login")
				.loginPage("/login")
				.failureUrl("/login?error")
				.usernameParameter("accountname")
				.passwordParameter("password")
				.defaultSuccessUrl("/list", true)
				.permitAll()

		).logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout"));

		

				return http.build();
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		PasswordEncoder encoder = passwordEncoder();

		auth
				.userDetailsService(userDetailsService)
				.passwordEncoder(encoder);
	}

	public static void password() {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        //暗号化したいパスワードを定義
        String rawPassword = "password123";

        //パスワードを暗号化
        String hashedPassword = passwordEncoder.encode(rawPassword);
        

    }

}