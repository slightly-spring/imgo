package slightlyspring.imgo.global.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CustomOAuth2UserService customOAuth2UserService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .headers().frameOptions().disable()
        .and()
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
