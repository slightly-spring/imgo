package slightlyspring.imgo.domain.auth.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
    String requestURI = request.getRequestURI();

    log.info("인증체크 인터셉터 실행 {}", requestURI);
    HttpSession session = request.getSession(false);
    if (session==null || session.getAttribute("userId")==null){
      log.info("로그인 하지 않은 사용자 요청");
      //로그인으로 redirect
      response.sendRedirect("/");
      return false;
    }

    return true;
  }
}
