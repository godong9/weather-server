package com.weather.domain.prediction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URISyntaxException;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by godong9 on 2017. 7. 29..
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/prediction")
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    @RequestMapping(method = RequestMethod.POST)
    public Prediction readPrediction(@Valid @RequestBody PredictionRequestDto predictionRequestDto) throws URISyntaxException {
        Prediction prediction = this.predictionService.readPrediction(predictionRequestDto);
        return prediction;
    }
}
