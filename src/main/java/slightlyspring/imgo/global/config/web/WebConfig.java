package slightlyspring.imgo.global.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import slightlyspring.imgo.domain.auth.interceptor.LoginCheckInterceptor;
import slightlyspring.imgo.domain.auth.interceptor.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoginInterceptor())
        .order(1)
//        .addPathPatterns("/**")
        .addPathPatterns("/user/**")
        .excludePathPatterns("/css/**", "/*.ico", "/error");

    registry.addInterceptor(new LoginCheckInterceptor())
        .order(2)
        .addPathPatterns("/user/**");
  }

}
