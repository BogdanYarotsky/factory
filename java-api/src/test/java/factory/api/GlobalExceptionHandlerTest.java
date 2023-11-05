package factory.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MockController.class)
public class GlobalExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MockController mockController;

    @Test
    void whenNoSuchElementExceptionThrown_thenRespondWithStatusNotFound() throws Exception {
        String exceptionMessage = "Element not found";
        doThrow(new NoSuchElementException(exceptionMessage)).when(mockController).mockMethod();

        mockMvc.perform(get("/mock")) // Replace with the endpoint that throws the exception
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value("Not Found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value(exceptionMessage));
    }

    @Test
    void whenGenericExceptionThrown_thenRespondWithStatusInternalServerError() throws Exception {
        String exceptionMessage = "An error occurred";
        doThrow(new RuntimeException(exceptionMessage)).when(mockController).mockMethod();

        mockMvc.perform(get("/mock")) // Replace with the endpoint that throws the exception
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value("Internal Server Error"))
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.detail").value(exceptionMessage));
    }
}
