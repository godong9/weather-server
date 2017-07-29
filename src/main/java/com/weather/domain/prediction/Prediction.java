package com.weather.domain.prediction;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by godong9 on 2017. 7. 29..
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "predictions")
@EntityListeners({AuditingEntityListener.class})
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "weather_code")
    private WeatherCode weatherCode;

    @Column(name = "temperature")
    private String temperature;

    @Column(name = "humidity")
    private String humidity;

    @Column(name = "rain_prop")
    private String rainProp;

    @Column(name = "nx")
    private Integer nx;

    @Column(name = "ny")
    private Integer ny;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "base_date")
    private Date baseDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "prediction_date")
    private Date predictionDate;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;
}
