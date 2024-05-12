package me.jameschan.supervisor.aop;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import me.jameschan.supervisor.exception.ResourceException;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class SupervisorExceptionResolver extends DataFetcherExceptionResolverAdapter {
    @Override
    protected GraphQLError resolveToSingleError(
        @NonNull Throwable ex,
        @NonNull DataFetchingEnvironment env
    ) {
        if (ex instanceof ResourceException) {
            final var extensions = new HashMap<String, Object>();
            extensions.put("code", ((ResourceException) ex).getErrorCode());

            return GraphqlErrorBuilder.newError()
                .errorType(ErrorType.NOT_FOUND)
                .message(ex.getMessage())
                .extensions(extensions)
                .build();
        } else {
            return GraphQLError.newError()
                .errorType(ErrorType.INTERNAL_ERROR)
                .message(ex.getMessage())
                .build();
        }
    }
}
