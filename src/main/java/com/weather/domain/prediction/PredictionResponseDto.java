package com.weather.domain.prediction;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by godong9 on 2017. 7. 29..
 */
@Data
@NoArgsConstructor
public class PredictionResponseDto {
    private WeatherCode weatherCode;

    private String temperature;

    private String humidity;

    private String railProp;

    private String predictionDate;
}
