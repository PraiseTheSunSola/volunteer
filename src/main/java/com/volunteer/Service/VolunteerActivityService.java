package com.volunteer.Service;

import com.opencsv.exceptions.CsvValidationException;
import com.volunteer.Entity.VolunteerActivity;
import com.volunteer.Repository.VolunteerActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVReader;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Service
public class VolunteerActivityService {

    @Autowired
    private VolunteerActivityRepository repository;

    public void saveCsvToDatabase() throws IOException {
        String filePath = "C:\\Users\\DW\\Desktop\\openapi_봉사참여정보상세조회.csv";

        String[] nextLine = null;
        try (Reader reader = new FileReader(filePath);
             CSVReader csvReader = new CSVReader(reader)) {

            List<VolunteerActivity> activities = new ArrayList<>();

            // 첫 줄을 헤더로 가정하여 건너뜁니다.
            csvReader.readNext();

            while ((nextLine = csvReader.readNext()) != null) {
                String progrmRegistNo = nextLine[0];

                // 중복 확인
                if (repository.existsByProgrmRegistNo(progrmRegistNo)) {
                    System.out.println("Duplicate data found for progrmRegistNo: " + progrmRegistNo);
                    continue; // 이미 존재하면 건너뜀
                }

                VolunteerActivity activity = new VolunteerActivity();
                activity.setSidoCd(Integer.parseInt(nextLine[1]));
                activity.setGugunCd(Integer.parseInt(nextLine[2]));
                activity.setProgrmRegistNo(Integer.parseInt(nextLine[3]));
                activity.setProgrmSj(nextLine[4]);
                activity.setProgrmSttusSe(Integer.parseInt(nextLine[5]));

                // 날짜 문자열을 LocalDate로 변환
                activity.setProgrmBgnde(parseLocalDate(nextLine[6]));
                activity.setProgrmEndde(parseLocalDate(nextLine[7]));
                activity.setActBeginTm(Integer.parseInt(nextLine[8]));
                activity.setActEndTm(Integer.parseInt(nextLine[9]));
                activity.setNoticeBgnde(Integer.parseInt(nextLine[10]));
                activity.setNoticeEndde(Integer.parseInt(nextLine[11]));
                activity.setRcritNmpr(Integer.parseInt(nextLine[12]));
                activity.setActWkdy(Integer.parseInt(nextLine[13]));
                activity.setAppTotal(Integer.parseInt(nextLine[14]));
                activity.setSrvcClCode(Integer.parseInt(nextLine[15]));
                activity.setAdultPosblAt(nextLine[16]);
                activity.setYngbgsPosblAt(nextLine[17]);
                activity.setFamilyPosblAt(nextLine[18]);
                activity.setPbsvntPosblAt(nextLine[19]);
                activity.setGrpPosblAt(nextLine[20]);
                activity.setMnnstNm(nextLine[21]);
                activity.setNanmmbyNm(nextLine[22]);
                activity.setActPlace(nextLine[23]);
                activity.setNanmmbyNmAdmn(nextLine[24]);
                activity.setTelno(nextLine[25]);
                activity.setFxnum(nextLine[26]);
                activity.setEmail(nextLine[27]);
                activity.setPostAdres(nextLine[28]);
                activity.setProgrmCn(nextLine[29]);
                activity.setAreaLalo1(nextLine[30]);
                activity.setAreaLalo2(nextLine[31]);
                activity.setAreaLalo3(nextLine[32]);
                activity.setAreaAddress1(nextLine[33]);
                activity.setAreaAddress2(nextLine[34]);
                activity.setAreaAddress3(nextLine[35]);

                activities.add(activity);
            }

            repository.saveAll(activities);
            System.out.println("CSV data saved successfully.");

        } catch (CsvValidationException e) {
            System.out.println("Error parsing line : " + String.join(",", nextLine));
            e.printStackTrace();
        }
    }

    // 날짜 문자열을 LocalDate로 변환하는 메서드
    private LocalDate parseLocalDate(String dateString) {
        if (dateString != null && !dateString.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd"); // 날짜 형식이 "yyyyMMdd"일 경우
                return LocalDate.parse(dateString, formatter);
            } catch (Exception e) {
                System.out.println("Invalid date format for: " + dateString);
            }
        }
        return null; // 날짜 형식이 잘못되었거나 값이 없는 경우 null 반환
    }

    private String getTagValue(Element element, String tagName) {
        Node node = element.getElementsByTagName(tagName).item(0);
        return node != null ? node.getTextContent() : null; // 값이 없을 경우 null 반환
    }

    private int parseInteger(Element element, String tagName) {
        String textValue = getTagValue(element, tagName);
        if (textValue != null && !textValue.isEmpty()) {
            try {
                return Integer.parseInt(textValue);
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer for tag: " + tagName + " with value: " + textValue);
            }
        }
        return 0;
    }
}
