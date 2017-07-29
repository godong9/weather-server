package com.weather.domain.prediction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by godong9 on 2017. 7. 29..
 */

@Transactional(readOnly = true)
@Service
public class PredictionService {

}
