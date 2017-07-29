package com.weather.domain.post;

import com.weather.domain.prediction.Prediction;
import com.weather.domain.prediction.WeatherCode;
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
@Table(name = "post_stats")
@EntityListeners({AuditingEntityListener.class})
public class PostStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prediction_id")
    private Prediction prediction;

    @Column(name = "nx")
    private Integer nx;

    @Column(name = "ny")
    private Integer ny;

    @Column(name = "weather_code")
    private WeatherCode weatherCode;

    @Column(name = "count")
    private Integer count;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;
}
