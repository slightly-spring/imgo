package slightlyspring.imgo.global.config.auth;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import slightlyspring.imgo.global.config.LettuceConfig;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityInitializer() {
        super(SecurityConfig.class, LettuceConfig.class);
    }

}