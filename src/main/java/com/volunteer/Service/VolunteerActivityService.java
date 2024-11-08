package com.volunteer.Service;

import com.volunteer.Entity.Member;
import com.volunteer.Entity.VolunteerActivity;
import com.volunteer.Repository.MemberRepository;
import com.volunteer.Repository.VolunteerActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Service
public class VolunteerActivityService {

    @Autowired
    private VolunteerActivityRepository repository;
    private MemberRepository memberRepository;

    public void fetchAndSaveVolunteerData() {

        int pageNo = 1;
        int numOfRows = 10; // 한 페이지당 가져올 데이터 수
        boolean hasMoreData = true;

        while (hasMoreData) {
            try {
                // URL 구성
                StringBuilder urlBuilder = new StringBuilder("http://openapi.1365.go.kr/openapi/service/rest/VolunteerPartcptnService/getVltrPartcptnItem");
                urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=v1sezQ5BClYUr5Pxq7wsg5z5SdAQizcZ%2BvKZRjRsYcO7HX7LPW6sZiGBjset4%2Bnn5pe3cHKktStB%2FF9nByYXJw%3D%3D"); // 실제 서비스 키 입력
                urlBuilder.append("&" + URLEncoder.encode("progrmRegistNo", "UTF-8") + "=" + URLEncoder.encode("3168582", "UTF-8")); // 프로그램 등록번호


                URL url = new URL(urlBuilder.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type", "application/json");

                System.out.println("Response code: " + conn.getResponseCode());

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

                System.out.println("API Response: " + sb.toString());

                // XML 파싱하여 totalCount 확인
                if (sb.toString().contains("<totalCount>0</totalCount>")) {
                    System.out.println("No more volunteer activity data available.");
                    hasMoreData = false;
                    continue;
                }

                // VolunteerActivity 객체 생성 및 API 응답 데이터로 설정
                VolunteerActivity activity = new VolunteerActivity();
                // 실제 데이터 매핑 코드 추가
                // 예: activity.setProgrmRegistNo(parsedProgrmRegistNo);
                //     activity.setActWkdy(parsedActWkdy);

                Member member = memberRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("Member not found"));
                activity.setMember(member);

                repository.save(activity);
                System.out.println("Data inserted successfully");

                // 다음 페이지를 위해 pageNo 증가
                pageNo++;

            } catch (Exception e) {
                e.printStackTrace();
                hasMoreData = false;
            }
        }
    }

}
