package com.constants;

/**
 * Created by HW on 10/11/16.
 */
public enum PlateAllowedTimeEnum {

    TIME_NOT_ALLOWED_MORNING(7, 9),
    TIME_NOT_ALLOWED_AFTERNOON(16, 19);

    private Integer startHour;
    private Integer endHour;

    PlateAllowedTimeEnum(Integer start, Integer end) {
        this.startHour = start;
        this.endHour = end;
    }


    public Integer getStartHour(){
        return startHour;
    }

    public Integer getEndHour(){
        return endHour;
    }

}
