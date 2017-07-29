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
    private String predictionTemperature;
    private String predictionHumidity;
    private String predictionRainProp;
    private Long userId;
    private String userNickname;
    private WeatherCode weatherCode;
    private Integer code;
    private String text;
    private Integer nx;
    private Integer ny;
    private Boolean isLiked;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Date baseDate;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Date predictionDate;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Date createdAt;
}
