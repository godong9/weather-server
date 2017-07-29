package com.weather.domain.prediction;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by godong9 on 2017. 7. 29..
 */

@Getter
@AllArgsConstructor
public enum WeatherCode {
    SUNNY(1), CLOUD(2), CLOUDY(3), RAIN(4), SNOW(5), THUNDER(6);

    private int code;
}
