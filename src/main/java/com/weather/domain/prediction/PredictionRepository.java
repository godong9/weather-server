package com.weather.domain.prediction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by godong9 on 2017. 7. 29..
 */
@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Long> {
}
