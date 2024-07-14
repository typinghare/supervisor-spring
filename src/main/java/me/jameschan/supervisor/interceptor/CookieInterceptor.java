package me.jameschan.supervisor.interceptor;

import jakarta.servlet.http.Cookie;
import me.jameschan.supervisor.common.ContextKey;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.function.Consumer;

@Component
public final class CookieInterceptor implements WebGraphQlInterceptor {
    @Override
    @NonNull
    public Mono<WebGraphQlResponse> intercept(
        @NonNull final WebGraphQlRequest request, @NonNull final Chain chain) {
        final Consumer<? super WebGraphQlResponse> callback = response -> {
            // Get the cookie list from the context
            final var cookieList = (ArrayList<Cookie>) response
                .getExecutionInput()
                .getGraphQLContext()
                .getOrDefault(ContextKey.COOKIE_LIST, new ArrayList<Cookie>());

            // Add each cookie to the response header
            for (final Cookie cookie : cookieList) {
                final var responseCookie =
                    ResponseCookie.from(cookie.getName(), cookie.getValue()).build();
                response.getResponseHeaders()
                    .add(HttpHeaders.SET_COOKIE, responseCookie.toString());
            }
        };

        return chain.next(request).doOnNext(callback);
    }
}
