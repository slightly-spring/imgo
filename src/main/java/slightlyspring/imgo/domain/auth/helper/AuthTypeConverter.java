package slightlyspring.imgo.domain.auth.helper;


import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import slightlyspring.imgo.domain.auth.domain.AuthType;

@Configuration
public class AuthTypeConverter implements Converter<String, AuthType> {
  @Override
  public AuthType convert(String s) {
    return AuthType.valueOf(s.toUpperCase());
  }

}
