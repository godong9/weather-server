package com.weather.domain.prediction;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * Created by godong9 on 2017. 7. 29..
 */
@Data
@NoArgsConstructor
public class PredictionResult {
    private Long id;
    private WeatherCode weatherCode;
    private Integer code;
    private String temperature;
    private String humidity;
    private String rainProp;
    private Integer nx;
    private Integer ny;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Date baseDate;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Date predictionDate;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Date createdAt;
}
