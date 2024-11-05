package com.volunteer.Service;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Iterator;

@Service
@Transactional
@RequiredArgsConstructor
public class VolunteerService {
    // API 요소 정의
    private static final String ENCODING_KEY = "v1sezQ5BClYUr5Pxq7wsg5z5SdAQizcZ%2BvKZRjRsYcO7HX7LPW6sZiGBjset4%2Bnn5pe3cHKktStB%2FF9nByYXJw%3D%3D";
    private static final String API_URL = "http://openapi.1365.go.kr/openapi/service/rest/VolunteerPartcptnService/getVltrPartcptnItem?serviceKey=" + ENCODING_KEY;
    private static final String DB_URL = "jdbc:mysql://121.125.164.219:3306/burnout";
    private static final String USER = "burnout";
    private static final String PASSWORD = "1234";

    public void fetchAndSaveData() {
        try {
            // API 호출 준비
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 응답 받기
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // JSON 파싱
                JSONObject jsonObject = new JSONObject(response.toString());
                JSONArray dataArray = jsonObject.getJSONArray("items");

                // DB에 데이터 저장
                Connection dbConn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

                // 배열 반복
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject item = dataArray.getJSONObject(i);

                    // 동적 컬럼 및 값 설정하기
                    StringBuilder columns = new StringBuilder();
                    StringBuilder placeholders = new StringBuilder();
                    for (Iterator<String> it = item.keys(); it.hasNext(); ) {
                        String key = it.next();
                        columns.append(key).append(", ");
                        placeholders.append("?, ");
                    }

                    // SQL 구문에서 마지막 쉼표 제거
                    String sql = "INSERT INTO Volunteer (" + columns.substring(0, columns.length() - 2) + ") VALUES (" + placeholders.substring(0, placeholders.length() - 2) + ")";
                    PreparedStatement pstmt = dbConn.prepareStatement(sql);

                    // 각 키에 대한 값 설정
                    int index = 1;
                    for (Iterator<String> it = item.keys(); it.hasNext(); ) {
                        String key = it.next();
                        pstmt.setObject(index++, item.get(key));
                    }
                    pstmt.executeUpdate();
                }

                System.out.println("데이터 추가 성공!");
                dbConn.close();
            } else {
                System.out.println("에러: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
