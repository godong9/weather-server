package com.weather.domain.prediction;

import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



/**
 * Created by godong9 on 2017. 7. 29..
 */

@Service
public class PredictionService {

    @Autowired
    private PredictionRepository predictionRepository;

    private String baseUrl = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2";

    private String ServiceKey = "%2BaK44ICKBp5y4KlIjv3tYRMb2QyCAtghncqxCvC4Q2kHIjJJ86oXXHijjCFJeAOUmwbe9cs1r1rQWyu5EZS6bQ%3D%3D";



    public PredictionResponseDto readPrediction(PredictionRequestDto predictionRequestDto){
        PredictionResponseDto predictionResponseDto = new PredictionResponseDto();
        RestTemplate restTemplate = new RestTemplate();

        DateTime dt = new DateTime();
        String base_date = dt.toString("yyyymmdd");

        int hour = dt.getHourOfDay();
        String base_time = hour + "30";

        String result = restTemplate.getForObject(this.baseUrl + "/ForecastTimeDate?ServiceKey=" +  this.ServiceKey +
                "&base_date=" + base_date + "&base_time=" + base_time + "&nx=" + predictionRequestDto.getNx() + "&ny=" +
                predictionRequestDto.getNy() + "_type=json", String.class);

        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonResult = (JSONObject) parser.parse(result);
            JSONArray jsonArray = (JSONArray) parser.parse(jsonResult.get("item").toString());
            String code = "0";

            for(int i=0; i < jsonArray.size(); i++){
                JSONObject jsonObject = (JSONObject) parser.parse(jsonArray.get(i).toString());
                String category = jsonObject.get("category").toString();
                int value = Integer.parseInt(jsonObject.get("fcstValue").toString());

                if(category.equals("SKY")){
                    if(value == 1)
                        code = "0";
                    else if(value == 2 || value == 3)
                        code = "1";
                    else if(value == 4)
                        code = "2";
                }else if(category.equals("PTY")){

                    if(value == 2 || value ==3)
                        code = "3";
                    else if(value == 1)
                        code = "4";
                }else if(category.equals("LGT")){
                    if(value == 2 || value == 3)
                        code = "5";
                }

            }


        } catch (ParseException e) {
            e.printStackTrace();
        }



        return predictionResponseDto;
    }


}
