package slightlyspring.imgo.global.config.web;

import java.util.Objects;
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
public class HttpRequestInterceptor implements HandlerInterceptor {

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

    String userId = Optional.of(request)
            .map(r -> r.getSession(false))
            .map(s -> s.getAttribute("userId"))
            .map(Objects::toString)
            .orElse("0");

    if(modelAndView != null && !userId.equals("0")) {
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
