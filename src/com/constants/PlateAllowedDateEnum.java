package com.constants;

/**
 * Created by HW on 10/11/16.
 */
public enum PlateAllowedDateEnum {


    MONDAY(2,1,2),TUESDAY(3,3,4),WEDNESDAY(4,5,6),THURSDAY(5,7,8),FRIDAY(6,9,0);

    private Integer day;
    private Integer dayTwo;
    private Integer dayOfWeek;

    PlateAllowedDateEnum(Integer dayOfWeek, Integer day,Integer dayTwo){
        this.day =day;
        this.dayTwo = dayTwo;
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getDayTwo() {
        return dayTwo;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }
}
