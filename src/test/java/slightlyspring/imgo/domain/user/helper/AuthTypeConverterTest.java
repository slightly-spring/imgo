package slightlyspring.imgo.domain.user.helper;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class AuthTypeConverterTest {

  @Test
  void convertStringToAuthType() {
    //given
    AuthTypeConverter authTypeConverter = new AuthTypeConverter();
    String s = "google";
    AuthType t = AuthType.GOOGLE;

    //when
    AuthType convert = authTypeConverter.convert(s);

    //then
    assertThat(convert).isEqualTo(t);
  }
}