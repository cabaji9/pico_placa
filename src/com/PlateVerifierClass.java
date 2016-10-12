package com;

import com.constants.PlateAllowedDateEnum;
import com.vo.TimeVo;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by HW on 10/11/16.
 */
public class PlateVerifierClass extends PlateVerifierCommon implements PlateVerifierInterface {

    private static Logger logger = Logger.getLogger(PlateVerifierClass.class.getName());

    private Integer plateInt;
    private Date dateObj;
    private TimeVo timeVo;


    public String verifyPlate(String plate, String date, String time) {
        //date should be DD/MM/YYYY example : 20/05/1981
        //time should be HH:MM
        String msgToUser = "YOU CAN CIRCULATE WITH YOUR CAR WITHOUT PROBLEMS";
        if (verifyDataAndAssignValues(plate, date, time)) {
            Integer lastDigit = extractLastDigit(plateInt);
            Integer dayOfWeek = getDayOfWeek(dateObj);
            PlateAllowedDateEnum plateAllowedDateEnum=  obtainDateEnumByDayOfWeek(dayOfWeek);
            logger.info("DAY OF WEEK: " + plateAllowedDateEnum.name());
            if(!verifyPlateDigitAllowedOnDay(lastDigit,plateAllowedDateEnum)){
                logger.info("TODAY ITS PICO Y PLACA DAY IN QUITO FOR YOUR CAR.");




            }
        }
        return msgToUser;
    }

    private boolean verifyPlateDigitAllowedOnDay(Integer lastDigit, PlateAllowedDateEnum plateAllowedDateEnum){
        boolean isAllowed = true;
        if(lastDigit.equals(plateAllowedDateEnum.getDay()) || lastDigit.equals(plateAllowedDateEnum.getDayTwo())){
            isAllowed = false;
        }
        return isAllowed;
    }


//    private boolean verifyTimeAllowed(){

//    }


    private boolean verifyDataAndAssignValues(String plate, String date, String time) {
        boolean verified = false;
        if (isNumber(plate)) {
            plateInt = Integer.parseInt(plate);
            logger.info("YOUR PLATE NUMBER IS: " + plate);
            dateObj = obtainDateObj(date);
            if (dateObj != null) {
                timeVo = obtainTimeDouble(time);
                if (timeVo != null) {
                    verified = true;
                    logger.info("YOUR TIME IS: " + timeVo.getHour() + ":" + timeVo.getMinute());
                } else {
                    logger.log(Level.SEVERE, "YOU DIDNT INPUT A CORRECT TIME REMEMBER THIS IS THE FORMAT HH:MM " + time);
                }
            } else {
                logger.log(Level.SEVERE, "YOU DIDNT INPUT A CORRECT DATE REMEMBER THIS IS THE FORMAT dd/MM/yyyy: " + date);
            }
        } else {
            logger.log(Level.SEVERE, "YOUR VEHICLE PLATE ITS NOT A NUMBER: " + plate);
        }
        return verified;
    }



    private PlateAllowedDateEnum obtainDateEnumByDayOfWeek(Integer dayOfWeek){
        PlateAllowedDateEnum plateAllowedDateEnumResult= null;
        for (PlateAllowedDateEnum plateAllowedDateEnum : PlateAllowedDateEnum.values()) {
            if( plateAllowedDateEnum.getDayOfWeek().equals(dayOfWeek)){
                plateAllowedDateEnumResult = plateAllowedDateEnum;
            }
        }
        return  plateAllowedDateEnumResult;
    }


}
