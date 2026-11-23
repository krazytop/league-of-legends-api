package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.exception;

import com.krazytop.leagueoflegends.domain.exception.InvalidSearchCriteriaException;
import com.krazytop.leagueoflegends.domain.exception.ResourceNotFoundException;
import com.krazytop.leagueoflegends.domain.exception.SummonerAlreadyInBoardException;
import com.krazytop.leagueoflegends.domain.exception.TooManySummonersException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleNotFound() {
        // Arrange
        ResourceNotFoundException ex = new ResourceNotFoundException("Le board board-123 n'existe pas");

        // Act
        ProblemDetail result = handler.handleNotFound(ex);

        // Assert
        assertThat(result.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(result.getDetail()).isEqualTo("Le board board-123 n'existe pas");
    }

    @Test
    void handleSummonerAlreadyInBoard() {
        // Arrange
        SummonerAlreadyInBoardException ex = new SummonerAlreadyInBoardException("board-123", "puuid-1234");

        // Act
        ProblemDetail result = handler.handleSummonerAlreadyInBoard(ex);

        // Assert
        assertThat(result.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(result.getDetail()).isEqualTo(ex.getMessage());
    }

    @Test
    void handleBadRequest_tooManySummoners() {
        // Arrange
        TooManySummonersException ex = new TooManySummonersException("board-123", "puuid-11");

        // Act
        ProblemDetail result = handler.handleBadRequest(ex);

        // Assert
        assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getDetail()).isEqualTo(ex.getMessage());
    }

    @Test
    void handleBadRequest_invalidSearchCriteria() {
        // Arrange
        InvalidSearchCriteriaException ex = new InvalidSearchCriteriaException("Le filtre 'role' nécessite de renseigner le 'puuid' de l'invocateur");

        // Act
        ProblemDetail result = handler.handleBadRequest(ex);

        // Assert
        assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getDetail()).isEqualTo("Le filtre 'role' nécessite de renseigner le 'puuid' de l'invocateur");
    }

    @Test
    @SuppressWarnings("unchecked")
    void handleConstraintViolation() {
        // Arrange
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        Path path = mock(Path.class);
        when(path.toString()).thenReturn("getBoard.boardId");
        when(violation.getPropertyPath()).thenReturn(path);
        when(violation.getMessage()).thenReturn("ne doit pas être vide");
        ConstraintViolationException ex = new ConstraintViolationException(Set.of(violation));

        // Act
        ProblemDetail result = handler.handleConstraintViolation(ex);

        // Assert
        assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getDetail()).isEqualTo("La validation des paramètres a échoué");
        assertThat(result.getProperties()).containsKey("errors");
        List<Map<String, String>> errors = (List<Map<String, String>>) result.getProperties().get("errors");
        assertThat(errors).containsExactly(Map.of(
                "field", "getBoard.boardId",
                "message", "ne doit pas être vide"
        ));
    }

    @Test
    @SuppressWarnings("unchecked")
    void handleMethodArgumentNotValid() {
        // Arrange
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("boardUpdateRequestDTO", "name", "ne doit pas être vide");
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        HttpHeaders headers = new HttpHeaders();
        WebRequest request = mock(WebRequest.class);

        // Act
        ResponseEntity<Object> response = handler.handleMethodArgumentNotValid(ex, headers, HttpStatus.BAD_REQUEST, request);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isInstanceOf(ProblemDetail.class);

        ProblemDetail problem = (ProblemDetail) response.getBody();
        assertThat(problem.getDetail()).isEqualTo("La validation du corps de la requête a échoué");
        List<Map<String, String>> errors = (List<Map<String, String>>) problem.getProperties().get("errors");
        assertThat(errors).containsExactly(Map.of(
                "field", "name",
                "message", "ne doit pas être vide"
        ));
    }

    @Test
    void handleAllUncaughtException() {
        // Arrange
        Exception ex = new RuntimeException("Erreur technique inattendue");

        // Act
        ProblemDetail result = handler.handleAllUncaughtException(ex);

        // Assert
        assertThat(result.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(result.getDetail()).isEqualTo("Une erreur interne inattendue est survenue");
    }
}