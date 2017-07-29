package com.weather.domain.prediction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by godong9 on 2017. 7. 29..
 */

@Slf4j
@RestController
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    @GetMapping("/predictions/init")
    public Prediction readPredictionInit() throws URISyntaxException {
        // TODO: 초기 지도화면 정해진 좌표 10개정도 내려주기!
        return null;
    }

    @GetMapping("/predictions/crawling")
    public void readPredictionList() throws URISyntaxException {
        // TODO: nx, ny 전부 돌면서
        // predictionService.readPrediction(predictionRequestDto);
    }

    @PostMapping("/predictions")
    public Prediction readPrediction(@Valid @RequestBody PredictionRequestDto predictionRequestDto) throws URISyntaxException {
        Prediction prediction = predictionService.readPrediction(predictionRequestDto);
        return prediction;
    }
}
