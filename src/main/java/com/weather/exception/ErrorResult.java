package com.weather.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by godong9 on 2017. 7. 29..
 */

@Data
@AllArgsConstructor
public class ErrorResult {
    private String message;
}