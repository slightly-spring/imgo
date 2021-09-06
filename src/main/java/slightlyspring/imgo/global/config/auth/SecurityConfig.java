package slightlyspring.imgo.global.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * SecurityConfig extends WebSecurityConfigurerAdapter
 * spring-security 설
 */
@RequiredArgsConstructor
@EnableWebSecurity // spring security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CustomOAuth2UserService customOAuth2UserService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .headers().frameOptions().disable()
        .and()
          // URL 별 권한 관리 설정 - antMatchers() 를 사용해 권한 별 설정이 가능. 현재는 그냥 모두 열어놓음.
          .authorizeRequests()
          .anyRequest().permitAll()// 수정 필요
        .and()
          .logout()
            .logoutSuccessUrl("/")
        .and()
          .oauth2Login()
            .userInfoEndpoint()
              .userService(customOAuth2UserService); // 소셜 로그인 성공시, 후속조치를 진행할 인터페이스 구현체 등록
  }

}
