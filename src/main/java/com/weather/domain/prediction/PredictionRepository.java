package com.weather.domain.prediction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by godong9 on 2017. 7. 29..
 */

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Long> {
    List<Prediction> findByNxGreaterThanAndNyGreaterThanAndNxLessThanAndNyLessThan(
            int startNx, int startNy, int endNx, int endNy
    );

    Prediction findByNxAndNy(int nx, int ny);
}
