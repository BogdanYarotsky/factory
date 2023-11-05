package factory.api;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public ResponseEntity<ProblemDetails> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus httpStatus = getHttpStatus(status);
        String requestPath = request.getRequestURI();
        ProblemDetails errorDetails = ProblemDetails.fromHttpStatus(httpStatus, requestPath);
        return new ResponseEntity<>(errorDetails, httpStatus);
    }

    private static HttpStatus getHttpStatus(Object status) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; // Default to 500 Internal Server Error
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            httpStatus = HttpStatus.valueOf(statusCode);
        }
        return httpStatus;
    }
}

