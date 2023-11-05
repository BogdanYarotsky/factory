package factory.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetails> handleAllExceptions(Exception exception, WebRequest request) {
        return createProblemDetailsResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception, request);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetails> handleNoSuchElementException(NoSuchElementException exception, WebRequest request) {
        return createProblemDetailsResponse(HttpStatus.NOT_FOUND, exception, request);
    }

    private ResponseEntity<ProblemDetails> createProblemDetailsResponse(HttpStatus status, Exception exception, WebRequest request) {
        String path = request.getDescription(false).replace("uri=", "");
        ProblemDetails errorDetails = ProblemDetails
                .fromHttpStatus(status, path)
                .withDetail(exception.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/problem+json");
        return new ResponseEntity<>(errorDetails, headers, status);
    }
}
