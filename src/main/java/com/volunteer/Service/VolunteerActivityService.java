package com.volunteer.Service;

import com.volunteer.Entity.VolunteerActivity;
import com.volunteer.Repository.VolunteerActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVReader;



@Service
public class VolunteerActivityService {

    @Autowired
    private VolunteerActivityRepository repository;

    public void saveCsvToDatabase(String filePath) throws IOException {
        try (Reader reader = new FileReader(filePath);
             CSVReader csvReader = new CSVReader(reader)) {

            List<VolunteerActivity> activities = new ArrayList<>();
            String[] nextLine;

            // Assuming first line is header
            csvReader.readNext();

            while ((nextLine = csvReader.readNext()) != null) {
                String progrmRegistNo = nextLine[0];

                // 중복 확인
                if (repository.existsByProgrmRegistNo(progrmRegistNo)) {
                    System.out.println("Duplicate data found for progrmRegistNo: " + progrmRegistNo);
                    continue; // 이미 존재하면 건너뜀
                }

                VolunteerActivity activity = new VolunteerActivity();
                activity.setProgrmRegistNo(progrmRegistNo);
                activity.setActWkdy(nextLine[1]);
                activity.setAppTotal(nextLine[2]);
                activity.setSrvcClCode(nextLine[3]);
                activity.setAdultPosblAt(nextLine[4]);
                activity.setYngbgsPosblAt(nextLine[5]);
                activity.setMnnstNm(nextLine[6]);
                activity.setNanmmbyNm(nextLine[7]);
                activity.setActPlace(nextLine[8]);
                activity.setNanmmbyNmAdmn(nextLine[9]);
                activity.setTelno(nextLine[10]);
                activity.setFxnum(nextLine[11]);
                activity.setPostAdres(nextLine[12]);
                activity.setEmail(nextLine[13]);
                activity.setProgrmCn(nextLine[14]);
                activity.setResultCode(nextLine[15]);
                activity.setResultMsg(nextLine[16]);
                activity.setNumOfRows(Integer.parseInt(nextLine[17]));
                activity.setPageNo(Integer.parseInt(nextLine[18]));
                activity.setTotalCount(Integer.parseInt(nextLine[19]));
                activity.setProgrmSj(nextLine[20]);
                activity.setProgrmSttusSe(nextLine[21]);
                activity.setProgrmBgnde(nextLine[22]);
                activity.setProgrmEndde(nextLine[23]);
                activity.setActBeginTm(Integer.parseInt(nextLine[24]));
                activity.setActEndTm(Integer.parseInt(nextLine[25]));
                activity.setNoticeBgnde(nextLine[26]);
                activity.setNoticeEndde(nextLine[27]);
                activity.setRcritNmpr(Integer.parseInt(nextLine[28]));
                activity.setSidoCd(nextLine[29]);
                activity.setGugunCd(nextLine[30]);

                activities.add(activity);
            }

            repository.saveAll(activities);
            System.out.println("CSV data saved successfully.");
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
