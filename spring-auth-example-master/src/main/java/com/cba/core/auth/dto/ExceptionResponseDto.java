package com.cba.core.auth.dto;

import java.time.LocalDateTime;

/**
 * this is immutable
 * all args constructor,getters,setters,hashcode,equals,toString
 */
public record ExceptionResponseDto(LocalDateTime timestamp,
                                   String message,
                                   String details) {

}
