package com.sist.nono.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.sist.nono.auth.PrincipalDetailService;

@Configuration // 빈등록
@EnableWebSecurity //필터로 등록 ==> 시큐리티가 활성화되어있는데 관련된 설정들은 해당 파일에서 할 것이다
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정주소로 접근을하면 권한 및 인증을 미리 체크할 것이다
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private PrincipalDetailService detailService;


	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	@Bean // return값을 스프링이 관리한다
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}


	// 시큐리티가 대신 로그인해줄때 pwd를 가로채기를 하는데 해당 pwd가 어떻게 해쉬화되어있는지 알아야 같은 해쉬로 암호화하여 db에 있는 해쉬와 비교가능
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(detailService).passwordEncoder(encodePWD());
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
			.csrf().disable() //csrf토큰 비활성화(테스트시 비활성화가편함)
			.authorizeRequests()
			.antMatchers("/","/account/**","/js/**","/css/**","/fonts/**","/plugin/**","/scripts/**","/image/**").permitAll()
			.anyRequest().authenticated()
		.and()
			.formLogin()
			.loginPage("/account/login")
			.loginProcessingUrl("/account/loginSubmit") //시큐리티가 해당 주소로 요청하는 로그인을 가로채서 대신 로그인 해준다
			.defaultSuccessUrl("/");
		http
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.invalidateHttpSession(true); //로그아웃 페이지들어가면 저장되어 있는 session 모두 파기

	}

	//	public void configureGlobal(AuthenticationManagerBuilder auth) 
	//	  throws Exception {
	//	    auth.jdbcAuthentication()
	//	      .dataSource(dataSource)
	//	      .passwordEncoder(encodePWD())
	//	      .usersByUsernameQuery("select cu_id, cu_pwd " // 로그인처리
	//	        + "from user "
	//	        + "where cu_id = ?")
	//	      .authoritiesByUsernameQuery("select cu_id, role " // 권한처리
	//	        + "from user "
	//	        + "where cu_id = ?");
	//	}

}