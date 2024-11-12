package com.volunteer.Service;

import org.springframework.stereotype.Service;

@Service
public class ActivityUtilityService {
    public String normalizeActWkdy(String actWkdy) {
        int length = actWkdy.length();
        if (length < 7) {
            return "0".repeat(7 - length) + actWkdy;
        }
        return actWkdy;
    }
}
