package us.jameschan.supervisor.advice;

import io.micrometer.common.lang.NonNullApi;
import io.micrometer.common.lang.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NonNullApi
@Component
public class SupervisorInterceptor implements HandlerInterceptor {
    public static final String REQUEST_ATTRIBUTE_HANDLER = "REQUEST_ATTRIBUTE_HANDLER";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // This method is called before the controller method is executed
        // You can modify the request or do some validation here
        request.setAttribute(REQUEST_ATTRIBUTE_HANDLER, handler);

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final String currentTime = LocalDateTime.now().format(formatter);

        final String uri = request.getRequestURI();
        final String queryString = request.getQueryString();
        final String httpMethod = request.getMethod();

        final StringBuilder builder = new StringBuilder();
        builder.append("[").append(currentTime).append("] ");
        builder.append("Server received a request: ");
        builder.append("<").append(httpMethod.toUpperCase()).append(">").append(uri);
        if (queryString != null) builder.append("?").append(queryString);

        System.out.println(builder);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) {
        // This method is called after the controller method is executed, but before the response is sent back to the client
        // You can modify the response or the model here
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception exception) {
        // This method is called after the response has been sent back to the client
        // You can perform some cleanup here
    }
}
