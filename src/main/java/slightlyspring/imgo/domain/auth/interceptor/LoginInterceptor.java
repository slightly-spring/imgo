package slightlyspring.imgo.domain.auth.interceptor;

import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

  public static final String LOG_ID = "logId";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String requestURI = request.getRequestURI();

    String uuid = UUID.randomUUID().toString();
    request.setAttribute(LOG_ID, uuid);

    //@RequestMapping: HandlerMethod
    //정적 리소스: ResourceHttpRequestHandler
    if (handler instanceof HandlerMethod) {
      HandlerMethod hm = (HandlerMethod) handler;
    }

    log.info("Request [{}][{}][{}]", uuid, requestURI, handler);
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
    log.info("postHandle [{}]", modelAndView);

    HttpSession session = request.getSession(false);
    String userId = Optional.ofNullable(session.getAttribute("userId").toString()).orElse("0");
    if(modelAndView != null) {
      modelAndView.getModelMap().addAttribute("userId", userId);
    }
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    String requestURI = request.getRequestURI();
    String logId = (String) request.getAttribute(LOG_ID);
    log.info("RESPONSE [{}][{}]", logId, requestURI);
    if (ex != null) {
      log.error("afterCompletion error!!", ex);
    }
  }
}
