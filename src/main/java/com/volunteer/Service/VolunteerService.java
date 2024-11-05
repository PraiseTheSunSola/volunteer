package com.volunteer.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;


@Service
@Transactional
@RequiredArgsConstructor
public class VolunteerService {
    //API 요소 정의
    private static final String API_URL = "http://openapi.1365.go.kr/openapi/service/rest/VolunteerPartcptnService/getVltrPartcptnItem?serviceKey=" + ENCODING_KEY;
    private static final String ENCODING_KEY = "\"v1sezQ5BClYUr5Pxq7wsg5z5SdAQizcZ%2BvKZRjRsYcO7HX7LPW6sZiGBjset4%2Bnn5pe3cHKktStB%2FF9nByYXJw%3D%3D";
    private static final String DB_URL = "jdbc:mysql://121.125.164.219:3306/burnout";
    private static final String USER = "burnout";
    private static final String PASSWORD = "1234";

    public void fetchAndSaveData(){
        try{//API 호출 준비
            URL url = new URL(API_URL.replace("ENCODING_KEY", ENCODING_KEY));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //응답 받기
            int responseCode = conn.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader((new InputStreamReader(conn.getInputStream())));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while((inputLine = in.readLine())!=null){
                    response.append(inputLine);
                }
                in.close();

                //JSON 피싱

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
