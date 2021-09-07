package slightlyspring.imgo.domain.user.helper;


import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class AuthTypeConverter implements Converter<String, AuthType> {
  @Override
  public AuthType convert(String s) {
    return AuthType.valueOf(s.toUpperCase());
  }

}
