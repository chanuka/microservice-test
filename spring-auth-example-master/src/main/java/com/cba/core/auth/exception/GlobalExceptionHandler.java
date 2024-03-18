package com.cba.core.auth.exception;

import com.cba.core.auth.dto.ExceptionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Locale;

/**
 * This is the class handle all the exceptions other than JWT token level authentication exceptions
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;


    @ExceptionHandler(JwtTokenException.class)
    public final ResponseEntity<ExceptionResponseDto> handleTokenRefreshException(TokenRefreshException tokenRefreshException,
                                                                                  WebRequest request) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(LocalDateTime.now(),
                tokenRefreshException.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto> handleAllOtherErrors(Exception exception,
                                                                     WebRequest request) {
        Locale currentLocale = LocaleContextHolder.getLocale();

        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(LocalDateTime.now(),
                messageSource.getMessage("GLOBAL_INTERNAL_SERVER_ERROR", null, currentLocale),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
