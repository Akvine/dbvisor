package ru.akvine.dbvisor.exceptions.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.akvine.compozit.commons.dto.ErrorResponse;
import ru.akvine.dbvisor.constants.ErrorCodes;
import ru.akvine.dbvisor.exceptions.InsertValuesException;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({InsertValuesException.class})
    public ResponseEntity<?> handleInsertValuesException(InsertValuesException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCodes.INSERT_VALUES_ERROR,
                exception.getMessage()
        );
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
