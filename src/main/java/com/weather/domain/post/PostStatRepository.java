package com.weather.domain.post;

import com.weather.domain.prediction.WeatherCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by godong9 on 2017. 7. 29..
 */

@Repository
public interface PostStatRepository extends JpaRepository<PostStat, Long> {
    List<PostStat> findByPredictionId(Long predictionId);
    PostStat findByPredictionIdAndWeatherCode(Long predictionId, WeatherCode weatherCode);
}
