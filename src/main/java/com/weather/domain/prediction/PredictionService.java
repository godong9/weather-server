package com.weather.domain.prediction;

import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.transaction.annotation.Transactional;
/**
 * Created by godong9 on 2017. 7. 29..
 */

@Transactional(readOnly = true)
@Service
public class PredictionService {

    @Autowired
    private PredictionRepository predictionRepository;

    private String baseUrl = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2";

    private String ServiceKey = "%2BaK44ICKBp5y4KlIjv3tYRMb2QyCAtghncqxCvC4Q2kHIjJJ86oXXHijjCFJeAOUmwbe9cs1r1rQWyu5EZS6bQ%3D%3D";

    public Prediction findOne(Long id) {
        return predictionRepository.findOne(id);
    }

    public List<PredictionResult> readPredictionInit(){
        List<PredictionResult> predictionResultList = new ArrayList<>();
        int[] xList = {60, 98, 89, 55, 58, 67, 102, 73, 52, 61, 62};
        int[] yList = {127, 76, 90, 124, 74, 100, 84, 134, 38, 121, 123};

        for(int i = 0; i < xList.length; i++){
            Prediction prediction = this.predictionRepository.findByNxAndNy(xList[i], yList[i]);
            PredictionResult predictionResult = new PredictionResult();

            predictionResult.setId(prediction.getId());
            predictionResult.setWeatherCode(prediction.getWeatherCode());
            predictionResult.setHumidity(prediction.getHumidity());
            predictionResult.setRainProp(prediction.getRainProp());
            predictionResult.setNx(prediction.getNx());
            predictionResult.setNy(prediction.getNy());
            predictionResult.setBaseDate(prediction.getBaseDate());
            predictionResult.setPredictionDate(prediction.getPredictionDate());
            predictionResult.setCreatedAt(prediction.getCreatedAt());

            predictionResultList.add(predictionResult);
        }

        return predictionResultList;
    }

    @Transactional(readOnly = false)
    public List<PredictionResult> predictionCrawling() throws URISyntaxException {
        List<PredictionResult> predictionResultList = new ArrayList<>();
        List<Integer> xList = new ArrayList<>();
        List<Integer> yList = new ArrayList<>();

        try {
            File csv = new File("/Users/gain/weather-server/src/main/resources/nxny.csv");
            BufferedReader br = new BufferedReader(new FileReader(csv));
            String line = "";

            while ((line = br.readLine()) != null){
                String[] token = line.split(",", -1);
                xList.add(Integer.parseInt(token[0]));
                yList.add(Integer.parseInt(token[1]));
            }
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < xList.size(); i++){
            predictionResultList.add(this.readPrediction(xList.get(i), yList.get(i)));
        }
        return predictionResultList;
    }

    @Transactional(readOnly = false)
    public PredictionResult readPrediction(int nx,  int ny) throws URISyntaxException {
        Prediction prediction = new Prediction();
        Map<String, Integer> map = new HashMap<>();
        Map<String, Float> resultMap = new HashMap<>();

        RestTemplate restTemplate = new RestTemplate();
        DateTime dt = new DateTime();
        String base_date = dt.toString("yyyyMMdd");

        int hour = dt.getHourOfDay();
        int minute = dt.getMinuteOfHour();


        if (minute < 30) {
            hour = hour - 1;
            System.out.println(hour);
            if (hour < 0) {
                hour = 23;
                System.out.println(hour);
                int dd = Integer.parseInt(base_date.substring(6, 8)) - 1;
                base_date = base_date.substring(0, 6) + dd;
            }
        }

        String base_time = "";

        if(hour < 10)
            base_time = "0" + hour + "30";
        else
            base_time = hour + "30";

        String timeUrl = this.baseUrl + "/ForecastTimeData?ServiceKey=" + this.ServiceKey + "&base_date=" + base_date + "&base_time=" +
                base_time + "&nx=" + nx + "&ny=" + ny + "&numOfRows=30&_type=json";

        if (hour == 2) {
            hour = 23;
            int dd = Integer.parseInt(base_date.substring(6, 8)) - 1;
            base_date = base_date.substring(0, 6) + dd;
        } else if (hour % 3 == 0)
            hour = hour - 1;
        else if (hour % 3 == 1)
            hour = hour - 2;

        base_time = hour + "00";

        String spaceUrl = this.baseUrl + "/ForecastSpaceData?ServiceKey=" + this.ServiceKey + "&base_date=" + base_date + "&base_time=" +
                base_time + "&nx=" + nx + "&ny=" + ny + "&numOfRows=30&_type=json";

        URI timeUri = new URI(timeUrl);
        URI spaceUri = new URI(spaceUrl);

        String timeResult = restTemplate.getForObject(timeUri, String.class);
        String spaceResult = restTemplate.getForObject(spaceUri, String.class);

        JSONParser parser = new JSONParser();

        try {
            JSONObject timeObject = (JSONObject) parser.parse(timeResult);
            JSONObject timeResponse = (JSONObject) timeObject.get("response");
            JSONObject body = (JSONObject) timeResponse.get("body");
            JSONObject timeItems = (JSONObject) body.get("items");
            JSONArray timeJsonArray = (JSONArray) timeItems.get("item");

            JSONObject spaceObject = (JSONObject) parser.parse(spaceResult);
            JSONObject spaceRsponse = (JSONObject) spaceObject.get("response");
            JSONObject spaceBody = (JSONObject) spaceRsponse.get("body");
            JSONObject spaceItems = (JSONObject) spaceBody.get("items");
            JSONArray spaceJsonArray = (JSONArray) spaceItems.get("item");

            JSONObject rainProp = (JSONObject) spaceJsonArray.get(0);
            prediction.setRainProp(rainProp.get("fcstValue").toString());
            System.out.println(rainProp.get("fcstValue").toString());

            JSONObject object = (JSONObject) parser.parse(timeJsonArray.get(0).toString());
            map.put(object.get("category").toString(), 1);

            String bsDate = object.get("baseDate").toString();
            String pdDate = object.get("fcstDate").toString();

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Date baseDate = format.parse(bsDate);
            Date predictionDate = format.parse(pdDate);

            prediction.setBaseDate(new Timestamp(baseDate.getTime()));
            prediction.setPredictionDate(new Timestamp(predictionDate.getTime()));
            prediction.setNx(Integer.parseInt(object.get("nx").toString()));
            prediction.setNy(Integer.parseInt(object.get("ny").toString()));


            for (int i = 1; i < timeJsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) parser.parse(timeJsonArray.get(i).toString());
                String category = jsonObject.get("category").toString();
                float value = Float.valueOf(jsonObject.get("fcstValue").toString());

                if (map.containsKey(category)) {
                    if (map.get(category) < 2)
                        resultMap.put(category, value);
                    map.put(category, map.get(category) + 1);
                } else {
                    map.put(category, 1);
                }
            }

            float skyValue = resultMap.get("SKY");

            if (skyValue == 1)
                prediction.setWeatherCode(WeatherCode.SUNNY);
            else if (skyValue == 2 || skyValue == 3)
                prediction.setWeatherCode(WeatherCode.CLOUD);
            else if (skyValue == 4)
                prediction.setWeatherCode(WeatherCode.CLOUDY);

            float rainSnowValue = resultMap.get("PTY");

            if (rainSnowValue == 1)
                prediction.setWeatherCode(WeatherCode.RAIN);
            else if (rainSnowValue == 2 || rainSnowValue == 3)
                prediction.setWeatherCode(WeatherCode.SNOW);

            float thunderValue = resultMap.get("LGT");

            if (thunderValue == 2 || thunderValue == 3)
                prediction.setWeatherCode(WeatherCode.THUNDER);

            prediction.setTemperature(resultMap.get("T1H").toString());
            prediction.setHumidity(resultMap.get("REH").toString());

            this.predictionRepository.save(prediction);

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        Prediction tmpPrediction = this.predictionRepository.findByNxAndNy(prediction.getNx(), prediction.getNy());

        PredictionResult predictionResult = new PredictionResult();

        predictionResult.setId(tmpPrediction.getId());
        predictionResult.setWeatherCode(tmpPrediction.getWeatherCode());
        predictionResult.setHumidity(tmpPrediction.getHumidity());
        predictionResult.setRainProp(tmpPrediction.getRainProp());
        predictionResult.setNx(tmpPrediction.getNx());
        predictionResult.setNy(tmpPrediction.getNy());
        predictionResult.setBaseDate(tmpPrediction.getBaseDate());
        predictionResult.setPredictionDate(tmpPrediction.getPredictionDate());
        predictionResult.setCreatedAt(tmpPrediction.getCreatedAt());

        return predictionResult;
    }

    public List<Prediction> findByNxGreaterThanAndNyGreaterThanAndNxLessThanAndNyLessThan(int startNx, int startNy, int endNx, int endNy) {
        return predictionRepository.findByNxGreaterThanAndNyGreaterThanAndNxLessThanAndNyLessThan(
                startNx, startNy, endNx, endNy
        );
    }
}
