package org.example.search_advance.inteceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    private static final String START_TIME = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {
        long start = System.currentTimeMillis();
        req.setAttribute(START_TIME, start);

        log.info("--> Incoming request: {} {} | Params: {}",
                req.getMethod(),
                req.getRequestURI(),
                req.getQueryString());

        return true; // Nếu false → chặn request
    }


    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView modelAndView) {
        log.info("--> Controller processing finished for {} {}", req.getMethod(), req.getRequestURI());
    }


    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex) {

        long start = (long) req.getAttribute(START_TIME);
        long duration = System.currentTimeMillis() - start;

        log.info("<-- Response completed: {} {} | Status: {} | Time: {} ms",
                req.getMethod(),
                req.getRequestURI(),
                res.getStatus(),
                duration);

        if (ex != null) {
            log.error(" Exception during request: {}", ex.getMessage(), ex);
        }
    }
}
