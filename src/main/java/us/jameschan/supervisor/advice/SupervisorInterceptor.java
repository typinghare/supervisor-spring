package us.jameschan.supervisor.advice;

import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

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

        // Enable CORS (Cross-Origin Resource Sharing).
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me, token");

        return true;
    }
}
