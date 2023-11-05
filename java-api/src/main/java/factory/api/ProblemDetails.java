package factory.api;
import org.springframework.lang.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public record ProblemDetails(
        String title,
        int status,
        String path,
        String timestamp,
        @Nullable String detail
)
{
    public static ProblemDetails fromHttpStatus(HttpStatus httpStatus, String path) {
        return new ProblemDetails(
                httpStatus.getReasonPhrase(),
                httpStatus.value(),
                path,
                LocalDateTime.now().toString(),
                null
        );
    }

    public ProblemDetails withDetail(@NonNull String detail) {
        return new ProblemDetails(
                title,
                status,
                path,
                timestamp,
                detail
        );
    }

}
