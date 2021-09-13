package slightlyspring.imgo.global.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * class SecurityConfig extends WebSecurityConfigurerAdapter
 * spring-security 설정
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // spring security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//  private final CustomOAuth2UserService customOAuth2UserService;
//  private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
//  private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        // URL 별 권한 관리 설정 - antMatchers() 를 사용해 권한 별 설정이 가능. 현재는 그냥 모두 열어놓음.
        .authorizeRequests()
          .antMatchers("/oauth_login").permitAll()
          .antMatchers("/user/*").hasRole("USER")
          .antMatchers("/user/profile/*").permitAll()
          .anyRequest().authenticated()
          .and()
        .oauth2Login()
          .loginPage("/oauth_login")
          .defaultSuccessUrl("/loginSuccess")
          .failureUrl("/loginFailure");
  }

}
