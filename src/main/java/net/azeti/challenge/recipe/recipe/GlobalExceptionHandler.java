package net.azeti.challenge.recipe.recipe;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import net.azeti.challenge.recipe.weather.WeatherServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    @ResponseBody
    public Map<String, String> handleInvalidFormatException(InvalidFormatException ife) {
        Map<String, String> errors = new HashMap<>();

        Class<?> targetType = ife.getTargetType();

        if (targetType.isEnum()) {
            String validValues = Arrays.stream(targetType.getEnumConstants())
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));

            errors.put("field", ife.getPath().get(0).getFieldName());
            errors.put("message", ife.getValue() + " is not a valid value. Accepted values are: " + validValues);
        } else {
            errors.put("field", ife.getPath().get(0).getFieldName());
            errors.put("message", ife.getValue() + " is not a valid value for the field type " + targetType.getSimpleName());
        }
        return errors;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return response;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(WeatherServiceException.class)
    public Map<String, String> handleWeatherServiceException(WeatherServiceException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return errors;
    }
}