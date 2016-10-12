package com.vo;

import java.util.Date;

/**
 * Created by hw on 10/11/16.
 */
public class PlateVo {


    private Integer plateInt;
    private Date dateObj;
    private TimeVo timeVo;

    public Integer getPlateInt() {
        return plateInt;
    }

    public void setPlateInt(Integer plateInt) {
        this.plateInt = plateInt;
    }

    public Date getDateObj() {
        return dateObj;
    }

    public void setDateObj(Date dateObj) {
        this.dateObj = dateObj;
    }

    public TimeVo getTimeVo() {
        return timeVo;
    }

    public void setTimeVo(TimeVo timeVo) {
        this.timeVo = timeVo;
    }
}
