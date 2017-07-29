package com.weather.domain.prediction;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by godong9 on 2017. 7. 29..
 */
@Data
@NoArgsConstructor
public class PredictionResult {
    private Long Id;
    private WeatherCode weatherCode;
    private String temperature;
    private String humidity;
    private String rainProp;
    private Integer nx;
    private Integer ny;

    @JsonFormat()
    private Date baseDate;

    private Date predictionDate;

    private Date updatedAt;

    private Date createdAt;
}
