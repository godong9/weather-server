package com.weather.domain.prediction;

import com.weather.domain.post.Post;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by godong9 on 2017. 7. 29..
 */

@Slf4j
@RestController
public class PredictionController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PredictionService predictionService;

    @GetMapping("/predictions/init")
    public Prediction readPredictionInit() throws URISyntaxException {
        // TODO: 초기 지도화면 정해진 좌표 10개정도 내려주기!
        return null;
    }

    @GetMapping("/predictions/crawling")
    public void predictionCrawling() throws URISyntaxException {
        // TODO: nx, ny 전부 돌면서
        // predictionService.readPrediction(predictionRequestDto);
    }

    @PostMapping("/predictions")
    public Prediction readPrediction(@Valid @RequestBody PredictionRequestDto predictionRequestDto) throws URISyntaxException {
        Prediction prediction = predictionService.readPrediction(predictionRequestDto);
        return prediction;
    }

    /**
     * @api {get} /predictions/list Get Prediction list
     * @apiName PredictionList
     * @apiGroup Prediction
     *
     * @apiDescription start_nx, start_ny, end_nx, end_ny 이용해서 검색
     *
     * @apiParam {Number} start_nx 시작 X 좌표
     * @apiParam {Number} start_ny 시작 Y 좌표
     * @apiParam {Number} end_nx 끝 X 좌표
     * @apiParam {Number} end_ny 끝 Y 좌표
     *
     * @apiExample {curl} Example:
     *      http://localhost:9000/predictions/list?start_nx=120&start_ny=30&end_nx=125&end_ny=35
     *
     * @apiSuccess {Number} id 예보 id
     * @apiSuccess {Number} code 날씨 코드
     * @apiSuccess {String} temperature 온도
     * @apiSuccess {String} humidity 습도
     * @apiSuccess {String} rainProp 강수확률
     * @apiSuccess {Date} base_date 기상청 발표 시각
     * @apiSuccess {Date} prediction_date 기상청 예보 시각
     * @apiSuccess {Date} created_at 생성 시각
     */
    @GetMapping("/predictions/list")
    public List<PredictionResult> readPredictionList(
            @RequestParam("start_nx") int startNx,
            @RequestParam("start_ny") int startNy,
            @RequestParam("end_nx") int endNx,
            @RequestParam("end_ny") int endNy
    ) throws URISyntaxException {
        List<Prediction> predictionList = predictionService.findByNxGreaterThanAndNyGreaterThanAndNxLessThanAndNyLessThan(
                startNx, startNy, endNx, endNy
        );

        return predictionList.stream()
                .map(this::getPredictionResult)
                .collect(Collectors.toList());
    }

    private PredictionResult getPredictionResult(Prediction prediction) {
        PredictionResult predictionResult = modelMapper.map(prediction, PredictionResult.class);
        predictionResult.setCode(prediction.getWeatherCode().getCode());

        return predictionResult;
    }
}
