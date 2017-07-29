package com.weather.domain.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.weather.domain.prediction.WeatherCode;
import lombok.Data;

import java.util.Date;

/**
 * Created by godong9 on 2017. 7. 29..
 */

@Data
public class PostResult {
    private Long id;
    private Long predictionId;
    private Long userId;
    private WeatherCode weatherCode;
    private Integer code;
    private String text;
    private Integer nx;
    private Integer ny;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Date createdAt;
}
