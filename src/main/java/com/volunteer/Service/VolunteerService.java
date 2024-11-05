package com.volunteer.Service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import org.w3c.dom.Element;

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
        System.out.println("API 호출 시작");

        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            System.out.println("API 응답코드 : "+responseCode);

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream responseStream = conn.getInputStream();
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(responseStream);
                doc.getDocumentElement().normalize();

                NodeList itemList = doc.getElementsByTagName("item");
                System.out.println("XML 파싱 성공, item 수: " + itemList.getLength());


                try (Connection dbConn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
                    System.out.println("데이터베이스 연결 성공");

                    for(int i = 0; i < itemList.getLength(); i++){
                        Element item = (Element) itemList.item(i);

                        String provinceCode = item.getElementsByTagName("sidoCd").item(0) !=null ? item.getElementsByTagName("sidoCd").item(0).getTextContent() : null;
                        String cityCode = item.getElementsByTagName("gugunCd").item(0) !=null ? item.getElementsByTagName("gugunCd").item(0).getTextContent() : null;
                    }


                    // 두 번째 for문: Volunteer_participation 테이블에 저장
                    for (int i = 0; i < itemList.getLength(); i++) {
                        Element item = (Element) itemList.item(i);

                        String programId = item.getElementsByTagName("progrmRegistNo").item(0) != null ? item.getElementsByTagName("progrmRegistNo").item(0).getTextContent() : null;
                        String title = item.getElementsByTagName("progrmSj").item(0) != null ? item.getElementsByTagName("progrmSj").item(0).getTextContent() : null;
                        String organization = item.getElementsByTagName("nanmmbyNm").item(0) != null ? item.getElementsByTagName("nanmmbyNm").item(0).getTextContent() : null;
                        String startDate = item.getElementsByTagName("progrmBgnde").item(0) != null ? item.getElementsByTagName("progrmBgnde").item(0).getTextContent() : null;
                        String endDate = item.getElementsByTagName("progrmEndde").item(0) != null ? item.getElementsByTagName("progrmEndde").item(0).getTextContent() : null;
                        String provinceCode = item.getElementsByTagName("sidoCd").item(0) != null ? item.getElementsByTagName("sidoCd").item(0).getTextContent() : null;
                        String adultAvailable = item.getElementsByTagName("adultPosblAt").item(0) != null ? item.getElementsByTagName("adultPosblAt").item(0).getTextContent() : null;
                        String youthAvailable = item.getElementsByTagName("yngbgsPosblAt").item(0) != null ? item.getElementsByTagName("yngbgsPosblAt").item(0).getTextContent() : null;
                        String serviceCategory = item.getElementsByTagName("srvcClCode").item(0) != null ? item.getElementsByTagName("srvcClCode").item(0).getTextContent() : null;
                        String activityPlace = item.getElementsByTagName("actPlace").item(0) != null ? item.getElementsByTagName("actPlace").item(0).getTextContent() : null;
                        String activityStartTime = item.getElementsByTagName("actBeginTm").item(0) != null ? item.getElementsByTagName("actBeginTm").item(0).getTextContent() : null;
                        String activityEndTime = item.getElementsByTagName("actEndTm").item(0) != null ? item.getElementsByTagName("actEndTm").item(0).getTextContent() : null;
                        String noticeStartDate = item.getElementsByTagName("noticeBgnde").item(0) != null ? item.getElementsByTagName("noticeBgnde").item(0).getTextContent() : null;
                        String noticeEndDate = item.getElementsByTagName("noticeEndde").item(0) != null ? item.getElementsByTagName("noticeEndde").item(0).getTextContent() : null;
                        String detailsUrl = item.getElementsByTagName("url").item(0) != null ? item.getElementsByTagName("url").item(0).getTextContent() : null;
                        System.out.println("Program ID: " + programId + ", Title: " + title);



                        String sql = "INSERT INTO Volunteer_participation (program_id, title, organization, start_date, end_date, province_code, adult_available, youth_available, service_category, activity_place, activity_start_time, activity_end_time, notice_start_date, notice_end_date, details_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement pstmt = dbConn.prepareStatement(sql);
                        pstmt.setString(1, programId);
                        pstmt.setString(2, title);
                        pstmt.setString(3, organization);
                        pstmt.setString(4, startDate);
                        pstmt.setString(5, endDate);
                        pstmt.setString(6, provinceCode);
                        pstmt.setString(7, adultAvailable);
                        pstmt.setString(8, youthAvailable);
                        pstmt.setString(9, serviceCategory);
                        pstmt.setString(10, activityPlace);
                        pstmt.setString(11, activityStartTime);
                        pstmt.setString(12, activityEndTime);
                        pstmt.setString(13, noticeStartDate);
                        pstmt.setString(14, noticeEndDate);
                        pstmt.setString(15, detailsUrl);
                        pstmt.executeUpdate();
                    }

                    // 세 번째 for문: Volunteer_participation_details 테이블에 저장
                    for (int i = 0; i < itemList.getLength(); i++) {
                        Element item = (Element) itemList.item(i);

                        String programId = item.getElementsByTagName("progrmRegistNo").item(0) != null ? item.getElementsByTagName("progrmRegistNo").item(0).getTextContent() : null;
                        String recruitmentCapacity = item.getElementsByTagName("rcritNmpr").item(0) != null ? item.getElementsByTagName("rcritNmpr").item(0).getTextContent() : null;
                        String activityWeekdays = item.getElementsByTagName("actWkdy").item(0) != null ? item.getElementsByTagName("actWkdy").item(0).getTextContent() : null;
                        String applicationCount = item.getElementsByTagName("appTotal").item(0) != null ? item.getElementsByTagName("appTotal").item(0).getTextContent() : null;
                        String groupAvailable = item.getElementsByTagName("grpPosblAt").item(0) != null ? item.getElementsByTagName("grpPosblAt").item(0).getTextContent() : null;
                        String mainOrganization = item.getElementsByTagName("mnnstNm").item(0) != null ? item.getElementsByTagName("mnnstNm").item(0).getTextContent() : null;
                        String registeringOrganization = item.getElementsByTagName("nanmmbyNm").item(0) != null ? item.getElementsByTagName("nanmmbyNm").item(0).getTextContent() : null;
                        String contactName = item.getElementsByTagName("nanmmbyNmAdmn").item(0) != null ? item.getElementsByTagName("nanmmbyNmAdmn").item(0).getTextContent() : null;
                        String contactPhone = item.getElementsByTagName("telno").item(0) != null ? item.getElementsByTagName("telno").item(0).getTextContent() : null;
                        String contactFax = item.getElementsByTagName("fxnum").item(0) != null ? item.getElementsByTagName("fxnum").item(0).getTextContent() : null;
                        String contactAddress = item.getElementsByTagName("postAdres").item(0) != null ? item.getElementsByTagName("postAdres").item(0).getTextContent() : null;
                        String contactEmail = item.getElementsByTagName("email").item(0) != null ? item.getElementsByTagName("email").item(0).getTextContent() : null;
                        String description = item.getElementsByTagName("progrmCn").item(0) != null ? item.getElementsByTagName("progrmCn").item(0).getTextContent() : null;

                        String sqlDetails = "INSERT INTO Volunteer_participation_details (program_id, recruitment_capacity, activity_weekdays, application_count, group_available, main_organization, registering_organization, contact_name, contact_phone, contact_fax, contact_address, contact_email, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement pstmtDetails = dbConn.prepareStatement(sqlDetails);
                        pstmtDetails.setString(1, programId);
                        pstmtDetails.setInt(2, recruitmentCapacity != null ? Integer.parseInt(recruitmentCapacity) : 0);
                        pstmtDetails.setString(3, activityWeekdays);
                        pstmtDetails.setInt(4, applicationCount != null ? Integer.parseInt(applicationCount) : 0);
                        pstmtDetails.setString(5, groupAvailable);
                        pstmtDetails.setString(6, mainOrganization);
                        pstmtDetails.setString(7, registeringOrganization);
                        pstmtDetails.setString(8, contactName);
                        pstmtDetails.setString(9, contactPhone);
                        pstmtDetails.setString(10, contactFax);
                        pstmtDetails.setString(11, contactAddress);
                        pstmtDetails.setString(12, contactEmail);
                        pstmtDetails.setString(13, description);
                        pstmtDetails.executeUpdate();
                    }
                    System.out.println("데이터 추가 성공!");
                }
            } else {
                System.out.println("에러: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            System.out.println("오류 발생");
            e.printStackTrace();
        }
    }


}
