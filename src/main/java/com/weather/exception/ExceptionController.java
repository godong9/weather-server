package com.weather.exception;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @apiDefine BadRequestError
 *
 * @apiError BadRequestError Bad request error
 *
 * @apiErrorExample Error-Response:
 *     HTTP/1.1 400 Bad Request
 *     {
 *       "message": "Error Message"
 *     }
 */

/**
 * Created by gd.godong9 on 2017. 4. 6.
 */

@Slf4j
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResult> handleException(Exception exception) throws Exception {
        log.error("[Exception] {}\n{}", exception.getMessage(), Throwables.getStackTraceAsString(exception));
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON_UTF8).body(new ErrorResult("서버 에러 발생!"));
    }
}
