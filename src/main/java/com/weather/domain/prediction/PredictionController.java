package com.weather.domain.prediction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

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
    public List<Prediction> predictionCrawling() throws URISyntaxException {
        List<Prediction> predictionList = predictionService.predictionCrawling();
        return predictionList;
    }

    @GetMapping("/predictions")
    public Prediction readPrediction(@RequestParam(name = "nx") int nx, @RequestParam(name = "ny") int ny) throws URISyntaxException {
        Prediction prediction = predictionService.readPrediction(nx, ny);
        return prediction;
    }

//    @GetMapping("/predictions/list")
//    public List<Prediction> readPredictionList() throws URISyntaxException {
//        // TODO: startNx, startNy, endNx, endNy 받아서 범위 검색
//
//    }
}
