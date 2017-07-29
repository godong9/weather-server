package com.weather.domain.prediction;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by godong9 on 2017. 7. 29..
 */

@Getter
@AllArgsConstructor
public enum WeatherCode {
    SUNNY(0), CLOUD(1), CLOUDY(2), RAIN(3), SNOW(4), THUNDER(5);

    private int code;
}
