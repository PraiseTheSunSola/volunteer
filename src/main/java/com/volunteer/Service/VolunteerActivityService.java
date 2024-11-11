package com.volunteer.Service;

import com.opencsv.exceptions.CsvValidationException;
import com.volunteer.Entity.VolunteerActivity;
import com.volunteer.Repository.VolunteerActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
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
                activity.setSidoCd(nextLine[0]);
                activity.setGugunCd(nextLine[1]);
                activity.setProgrmRegistNo(nextLine[2]);
                activity.setProgrmSj(nextLine[3]);
                activity.setProgrmSttusSe(nextLine[4]);
                activity.setProgrmBgnde(nextLine[5]);
                activity.setProgrmEndde(nextLine[6]);
                activity.setActBeginTm(Integer.parseInt(nextLine[7]));
                activity.setActEndTm(Integer.parseInt(nextLine[8]));
                activity.setNoticeBgnde(nextLine[9]);
                activity.setNoticeEndde(nextLine[10]);
                activity.setRcritNmpr(Integer.parseInt(nextLine[11]));
                activity.setActWkdy(nextLine[12]);
                activity.setAppTotal(nextLine[13]);
                activity.setSrvcClCode(nextLine[14]);
                activity.setAdultPosblAt(nextLine[15]);
                activity.setYngbgsPosblAt(nextLine[16]);
                activity.setFamilyPosblAt(nextLine[17]);
                activity.setPbsvntPosblAt(nextLine[18]);
                activity.setGrpPosblAt(nextLine[19]);
                activity.setMnnstNm(nextLine[20]);
                activity.setNanmmbyNm(nextLine[21]);
                activity.setActPlace(nextLine[22]);
                activity.setNanmmbyNmAdmn(nextLine[23]);
                activity.setTelno(nextLine[24]);
                activity.setFxnum(nextLine[25]);
                activity.setEmail(nextLine[26]);
                activity.setPostAdres(nextLine[27]);
                activity.setProgrmCn(nextLine[28]);
                activity.setAreaLalo1(nextLine[29]);
                activity.setAreaLalo2(nextLine[30]);
                activity.setAreaLalo3(nextLine[31]);
                activity.setAreaAddress1(nextLine[32]);
                activity.setAreaAddress2(nextLine[33]);
                activity.setAreaAddress3(nextLine[34]);

                activities.add(activity);
            }

            repository.saveAll(activities);
            System.out.println("CSV data saved successfully.");

        } catch (CsvValidationException e) {
            System.out.println("Error parsing line : " + String.join(",", nextLine));
            e.printStackTrace();
        }
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
