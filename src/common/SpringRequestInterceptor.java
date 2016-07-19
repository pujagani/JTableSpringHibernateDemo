package common;

import controller.*;
import org.springframework.web.method.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.handler.*;

import javax.servlet.http.*;
/*
    preHandle and postHandle method is called before and after any request is processed.
    If handler is pointing to BaseController. Call preHandle and postHandle on the BaseController.
*/

public class SpringRequestInterceptor extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Object bean = handlerMethod.getBean();
            if (bean instanceof BaseController) {
                BaseController controller = (BaseController) bean;
                return controller.preHandle(request, response, handler);
            }
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Object bean = handlerMethod.getBean();
            if (bean instanceof BaseController) {
                BaseController controller = (BaseController) bean;
                controller.postHandle(request, response, handler, modelAndView);
            }
        }
    }
}
