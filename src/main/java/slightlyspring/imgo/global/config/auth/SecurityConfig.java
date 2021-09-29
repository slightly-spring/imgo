package slightlyspring.imgo.global.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import slightlyspring.imgo.domain.auth.service.CustomOAuth2UserService;

/**
 * class SecurityConfig extends WebSecurityConfigurerAdapter
 * spring-security 설정
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // spring security 설정 활성화
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CustomOAuth2UserService customOAuth2UserService;
//  private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
//  private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeRequests()
            .antMatchers("/css/**", "/img/**", "/js/**", "/**").permitAll()
            .antMatchers("/til/write", "/til/analyze").hasRole("USER")
            .anyRequest().permitAll()
        .and()
          .logout()
            .logoutUrl("/auth/logout")
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
        .and()
          .oauth2Login()
            .userInfoEndpoint()
            .userService(customOAuth2UserService)
            .and()
            .failureUrl("/loginFailure");
  }

}
