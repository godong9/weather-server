package com.weather.domain.post;

import com.weather.domain.prediction.WeatherCode;
import lombok.Data;

/**
 * Created by godong9 on 2017. 7. 29..
 */

@Data
public class PostForm {
    private Long predictionId;
    private Long userId;
    private WeatherCode weatherCode;
    private String text;
    private Integer nx;
    private Integer ny;
}
