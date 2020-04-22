package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.exception.ErrorType.*;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    private final MessageSource messageSource;

    @Autowired
    public ExceptionInfoHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    //  http://stackoverflow.com/a/22358422/548473
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo handleError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false, DATA_NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e, Locale locale) {
        if (ValidationUtil.getRootCause(e).toString().contains("meals_unique_user_datetime_idx")) {
            String errorMessage = messageSource.getMessage("Meal.AlreadyExists", null, locale);
            return new ErrorInfo(req.getRequestURL(), VALIDATION_ERROR, List.of(errorMessage));
        }
        if (ValidationUtil.getRootCause(e).toString().contains("users_unique_email_idx")) {
            String errorMessage = messageSource.getMessage("User.AlreadyExists", null, locale);
            return new ErrorInfo(req.getRequestURL(), VALIDATION_ERROR, List.of(errorMessage));
        }
        return logAndGetErrorInfo(req, e, true, DATA_ERROR);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ErrorInfo illegalRequestDataError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    public ErrorInfo handleMethodArgumentNotValidException(HttpServletRequest req, Exception e) {
        List<String> errorDetails = getErrorDetails(((MethodArgumentNotValidException) e).getBindingResult());
        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR, errorDetails);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    public ErrorInfo handleConstraintViolationException(HttpServletRequest req, Exception e, Locale locale) {
        String errorMessage = messageSource.getMessage(e.getMessage(), null, locale);
        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR, List.of(errorMessage));
    }


    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    public ErrorInfo handleBindException(HttpServletRequest req, Exception e) {
        List<String> errorDetails = getErrorDetails(((BindException) e).getBindingResult());
        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR, errorDetails);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true, APP_ERROR);
    }

    //    https://stackoverflow.com/questions/538870/should-private-helper-methods-be-static-if-they-can-be-static
    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType, List<String> errorMessage) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        if (errorMessage == null) {
            return new ErrorInfo(req.getRequestURL(), errorType, List.of(e.getMessage()));
        } else {
            return new ErrorInfo(req.getRequestURL(), errorType, errorMessage);
        }
    }

    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType) {
        return logAndGetErrorInfo(req, e, logException, errorType, null);
    }

    private List<String> getErrorDetails(BindingResult errors) {

        return errors.getFieldErrors().stream()
                .map(fe -> String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
    }
}