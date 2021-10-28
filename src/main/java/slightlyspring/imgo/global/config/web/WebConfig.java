package slightlyspring.imgo.global.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new HttpRequestInterceptor())
        .order(1)
        .addPathPatterns("/**")
        .excludePathPatterns("/css/**", "/*.ico", "/error");

    registry.addInterceptor(new LoginCheckInterceptor())
        .order(2)
        .addPathPatterns("/til/write", "/til/analyze");
  }

}
