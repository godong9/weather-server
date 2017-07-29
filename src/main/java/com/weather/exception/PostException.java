package com.weather.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by godong9 on 2017. 7. 29..
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostException extends RuntimeException {
    private String message = "처리 중 에러가 발생하였습니다.";
}
