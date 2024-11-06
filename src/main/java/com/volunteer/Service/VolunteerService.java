package com.volunteer.Service;

import com.volunteer.Entity.VolunteerParticipation;
import com.volunteer.Entity.VolunteerParticipationDetails;
import com.volunteer.Repository.VolunteerParticipationRepository;
import com.volunteer.Repository.VolunteerParticipationDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VolunteerService {
    private static final String ENCODING_KEY = "v1sezQ5BClYUr5Pxq7wsg5z5SdAQizcZ%2BvKZRjRsYcO7HX7LPW6sZiGBjset4%2Bnn5pe3cHKktStB%2FF9nByYXJw%3D%3D";
    private static final String BASE_API_URL  = "http://openapi.1365.go.kr/openapi/service/rest/VolunteerPartcptnService/getVltrPartcptnItem?serviceKey=" + ENCODING_KEY;

    private final VolunteerParticipationRepository participationRepository;
    private final VolunteerParticipationDetailsRepository detailsRepository;

    public void fetchAndSaveData() {
        // 프로그램 ID 리스트 정의 (테스트를 위해 임의의 ID로 설정)
        List<String> programIds = Arrays.asList("3043657", "2817512", "2817513");

        for (String programId : programIds) {
            try {
                // URL 빌더를 사용하여 API URL 생성
                StringBuilder urlBuilder = new StringBuilder(BASE_API_URL);
                urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + ENCODING_KEY);
                urlBuilder.append("&" + URLEncoder.encode("progrmRegistNo", "UTF-8") + "=" + URLEncoder.encode(programId, "UTF-8"));

                URL url = new URL(urlBuilder.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type", "application/json");
                System.out.println("Response code: " + conn.getResponseCode());

                // API 응답 처리
                BufferedReader rd;
                if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                rd.close();
                conn.disconnect();

                // API 응답 XML 출력
                System.out.println("API 응답 데이터: " + sb.toString());

                // XML 파싱 및 데이터 저장 처리 (추가 구현 필요)
                // XML 파싱 로직을 여기에서 추가하여 VolunteerParticipation 엔티티에 저장합니다.

            } catch (Exception e) {
                System.out.println("오류 발생: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private String getElementValue(Element parent, String tagName) {
        return parent.getElementsByTagName(tagName).item(0) != null
                ? parent.getElementsByTagName(tagName).item(0).getTextContent()
                : null;
    }

    private LocalDate parseLocalDate(String date) {
        return date != null ? LocalDate.parse(date) : null;
    }

    private LocalTime parseLocalTime(String time) {
        return time != null ? LocalTime.parse(time) : null;
    }

    private int parseInt(String number) {
        return number != null ? Integer.parseInt(number) : 0;
    }

    private char parseChar(String text) {
        return (text != null && text.length() == 1) ? text.charAt(0) : 'N';
    }
}
