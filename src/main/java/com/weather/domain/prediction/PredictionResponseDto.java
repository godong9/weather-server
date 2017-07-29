package com.weather.domain.prediction;

import lombok.Data;

import java.util.Date;

/**
 * Created by godong9 on 2017. 7. 29..
 */
@Data
public class PredictionResponseDto {
    private WeatherCode weatherCode;

    private String temperature;

    private String humidity;

    private String railProp;

    private Date predictionDate;
}
