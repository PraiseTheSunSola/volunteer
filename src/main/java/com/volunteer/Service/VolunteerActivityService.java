package com.volunteer.Service;

import com.volunteer.Entity.VolunteerActivity;
import com.volunteer.Repository.VolunteerActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;



@Service
public class VolunteerActivityService {

    @Autowired
    private VolunteerActivityRepository repository;

    public void fetchAndSaveVolunteerData() {

        int progrmNo = 3168582;
        int maxAttempts = 100000; // 데이터가 없는 번호가 반복될 경우 종료 조건
        int emptyResponseCount = 0;


        while (emptyResponseCount < maxAttempts) {
            try {
                // URL 구성
                StringBuilder urlBuilder = new StringBuilder("http://openapi.1365.go.kr/openapi/service/rest/VolunteerPartcptnService/getVltrPartcptnItem");
                urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=v1sezQ5BClYUr5Pxq7wsg5z5SdAQizcZ%2BvKZRjRsYcO7HX7LPW6sZiGBjset4%2Bnn5pe3cHKktStB%2FF9nByYXJw%3D%3D"); // 실제 서비스 키 입력
                urlBuilder.append("&" + URLEncoder.encode("progrmRegistNo", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(progrmNo), "UTF-8")); // 프로그램 등록번호


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

                // XML 파싱
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new ByteArrayInputStream(sb.toString().getBytes()));

                document.getDocumentElement().normalize();

                NodeList itemList = document.getElementsByTagName("item");
                if (itemList.getLength() == 0) {
                    System.out.println("No items found.");
                    emptyResponseCount++;
                    progrmNo++;
                    continue;
                }

                // 각 <item> 요소를 반복하여 VolunteerActivity 객체에 매핑
                for (int i = 0; i < itemList.getLength(); i++) {
                    Node itemNode = itemList.item(i);
                    if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element itemElement = (Element) itemNode;

                        String progrmRegistNo = getTagValue(itemElement, "progrmRegistNo");

                        // 중복 확인
                        if (repository.existsByProgrmRegistNo(progrmRegistNo)) {
                            System.out.println("Duplicate data found for progrmRegistNo: " + progrmRegistNo);
                            continue; // 이미 존재하면 건너뜀
                        }

                        // VolunteerActivity 객체 생성 및 설정
                        VolunteerActivity activity = new VolunteerActivity();
                        activity.setProgrmRegistNo(getTagValue(itemElement, "progrmRegistNo"));
                        activity.setActWkdy(getTagValue(itemElement, "actWkdy"));
                        activity.setAppTotal(getTagValue(itemElement, "appTotal"));
                        activity.setSrvcClCode(getTagValue(itemElement, "srvcClCode"));
                        activity.setAdultPosblAt(getTagValue(itemElement, "adultPosblAt"));
                        activity.setYngbgsPosblAt(getTagValue(itemElement, "yngbgsPosblAt"));
                        activity.setMnnstNm(getTagValue(itemElement, "mnnstNm"));
                        activity.setNanmmbyNm(getTagValue(itemElement, "nanmmbyNm"));
                        activity.setActPlace(getTagValue(itemElement, "actPlace"));
                        activity.setNanmmbyNmAdmn(getTagValue(itemElement, "nanmmbyNmAdmn"));
                        activity.setTelno(getTagValue(itemElement, "telno"));
                        activity.setFxnum(getTagValue(itemElement, "fxnum"));
                        activity.setPostAdres(getTagValue(itemElement, "postAdres"));
                        activity.setEmail(getTagValue(itemElement, "email"));
                        activity.setProgrmCn(getTagValue(itemElement, "progrmCn"));
                        activity.setResultCode(getTagValue(itemElement, "resultCode"));
                        activity.setResultMsg(getTagValue(itemElement, "resultMsg"));
                        activity.setNumOfRows(parseInteger(itemElement, "numOfRows"));
                        activity.setPageNo(parseInteger(itemElement, "pageNo"));
                        activity.setTotalCount(parseInteger(itemElement, "totalCount"));
                        activity.setProgrmSj(getTagValue(itemElement, "progrmSj"));
                        activity.setProgrmSttusSe(getTagValue(itemElement, "progrmSttusSe"));
                        activity.setProgrmBgnde(getTagValue(itemElement, "progrmBgnde"));
                        activity.setProgrmEndde(getTagValue(itemElement, "progrmEndde"));
                        activity.setActBeginTm(parseInteger(itemElement, "actBeginTm"));
                        activity.setActEndTm(parseInteger(itemElement, "actEndTm"));
                        activity.setNoticeBgnde(getTagValue(itemElement, "noticeBgnde"));
                        activity.setNoticeEndde(getTagValue(itemElement, "noticeEndde"));
                        activity.setRcritNmpr(parseInteger(itemElement, "rcritNmpr"));
                        activity.setSidoCd(getTagValue(itemElement, "sidoCd"));
                        activity.setGugunCd(getTagValue(itemElement, "gugunCd"));
                        // 데이터베이스에 저장
                        repository.save(activity);
                    }
                }

                System.out.println("Data inserted successfully for page: " + progrmNo);
                progrmNo++; //다음 프로그램 넘버로 이동

            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        if (emptyResponseCount >= maxAttempts) {
            System.out.println("Reached maximum attempts without data. Stopping process.");
        }

    }



    private String getTagValue(Element element, String tagName) {
        Node node = element.getElementsByTagName(tagName).item(0);
        return node != null ? node.getTextContent() : ""; // 값이 없을 경우 빈 문자열 반환
    }

    private int parseInteger(Element element, String tagName) {
        String textValue = getTagValue(element, tagName); // getTagValue 메서드를 이용해 값을 가져옴
        if (!textValue.isEmpty()) {
            try {
                return Integer.parseInt(textValue);
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer for tag: " + tagName + " with value: " + textValue);
            }
        }
        return 0; // 기본값 0 반환
    }



}
