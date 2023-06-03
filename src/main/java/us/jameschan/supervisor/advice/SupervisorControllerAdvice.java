package us.jameschan.supervisor.advice;

import com.google.common.collect.ImmutableMap;
import io.micrometer.common.lang.NonNullApi;
import io.micrometer.common.lang.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import us.jameschan.overplay.BaseException;
import us.jameschan.supervisor.annotation.Message;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static us.jameschan.neater.StaticFunctions.let;

@NonNullApi
@ControllerAdvice
public class SupervisorControllerAdvice implements ResponseBodyAdvice<Object> {
    public static final Logger logger = LoggerFactory.getLogger(SupervisorControllerAdvice.class);

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Map<String, Object>> handleBaseException(final BaseException baseException) {
        final Map<String, Object> body = ImmutableMap.<String, Object>builder()
            .put("message", baseException.getMessage())
            .put("errorCode", baseException.getErrorCode())
            .build();

        final HttpHeaders headers = let(new HttpHeaders(), it -> it.setContentType(MediaType.APPLICATION_JSON));

        return new ResponseEntity<>(body, headers, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(final Exception exception) {
        final Map<String, Object> body = ImmutableMap.<String, Object>builder()
            .put("message", "Unknown Server Error.")
            .put("errorCode", "10000")
            .build();

        logger.error(exception.getMessage());
        exception.printStackTrace();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(body, headers, HttpStatusCode.valueOf(500));
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * This method is called after a controller method has been invoked successfully and before the response
     * body is written. This method allows for modifications to be made to the response body, such as adding
     * headers, transforming the body content, or returning an entirely different response.
     */
    @Override
    public Object beforeBodyWrite(
        @Nullable Object data,
        MethodParameter returnType,
        MediaType selectedContentType,
        Class<? extends HttpMessageConverter<?>> selectedConverterType,
        ServerHttpRequest request,
        ServerHttpResponse response) {
        if (data instanceof Map<?, ?> && ((Map<?, ?>) data).containsKey("errorCode")) {
            return data;
        }

        final HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        final HandlerMethod handlerMethod = (HandlerMethod) servletRequest.getAttribute(SupervisorInterceptor.REQUEST_ATTRIBUTE_HANDLER);
        final Method controllerMethod = handlerMethod.getMethod();

        return let(new HashMap<>(), it -> {
            final Message message = controllerMethod.getAnnotation(Message.class);
            if (message != null) {
                it.put("message", message.value());
            }

            final Deprecated deprecated = controllerMethod.getAnnotation(Deprecated.class);
            if (deprecated != null) {
                it.put("deprecated", "This API has been deprecated since: " + deprecated.since());
            }

            if (data != null) {
                it.put("data", data);
            }
        });
    }
}
