package com;

import com.constants.PlateAllowedDateEnum;
import com.constants.PlateAllowedTimeEnum;
import com.vo.PlateVo;
import com.vo.TimeVo;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by HW on 10/11/16.
 */
public class PlateVerifierClass extends PlateVerifierCommon implements PlateVerifierInterface {

    private static Logger logger = Logger.getLogger(PlateVerifierClass.class.getName());

    public String verifyPlate(String plate, String date, String time) {
        //date should be DD/MM/YYYY example : 20/05/1981
        //time should be HH:MM
        System.out.println("THE PROGRAM WILL VERIFY IF YOU CAN TAKE YOUR CAR");
        String msgToUser = "YOU CAN CIRCULATE WITH YOUR CAR WITHOUT PROBLEMS";
        PlateVo plateVo = new PlateVo();
        if (verifyDataAndAssignValues(plate, date, time,plateVo)) {
            Integer lastDigit = extractLastDigit(plateVo.getPlateInt());
            Integer dayOfWeek = getDayOfWeek(plateVo.getDateObj());
            PlateAllowedDateEnum plateAllowedDateEnum=  obtainDateEnumByDayOfWeek(dayOfWeek);
            if(plateAllowedDateEnum != null) {
                System.out.println("DAY OF WEEK: " + plateAllowedDateEnum.name());
                if (!verifyPlateDigitAllowedOnDay(lastDigit, plateAllowedDateEnum)) {
                    System.out.println("TODAY ITS PICO Y PLACA DAY IN QUITO FOR YOUR CAR.");
                    if (!verifyTimeAllowed(plateVo.getTimeVo())) {
                        msgToUser = "YOU MUST NOT TAKE OUT YOUR CAR YOU ARE IN PICO Y PLACA TIME ZONE. " + plateVo.getTimeVo().toString();
                    }
                }
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


    private boolean verifyTimeAllowed(TimeVo timeVo){
        boolean timeAllowed = true;
        Integer hour =timeVo.getHour();
        Integer minute = timeVo.getMinute();

        Date toSearchDate = getDate(hour,minute);

        if(hour < 12 ){
            PlateAllowedTimeEnum morning = PlateAllowedTimeEnum.TIME_NOT_ALLOWED_MORNING;
            Date warningStart = getDate(morning.getStartHour()-1,30);
            Date morninStart = getDate(morning.getStartHour()-1,59);
            Date morningEnd = getDate(morning.getEndHour(),30);
            if(betweenDate(toSearchDate,morninStart,morningEnd)){
                timeAllowed = false;
            }
            if(timeAllowed == true){
                if(betweenDate(toSearchDate,warningStart,morninStart)){
                    System.out.println("THING ABOUT WHAT YOU ARE DOING YOU HAVE ONLY "+ obtainMinutesDifference(morninStart,toSearchDate) +" MINS TO BEAT THE PICO Y PLACA");
                }
            }
        }
        else{
            PlateAllowedTimeEnum afternoon = PlateAllowedTimeEnum.TIME_NOT_ALLOWED_AFTERNOON;
            Date afternoonDateStart = getDate(afternoon.getStartHour()-1,59);
            Date warningStart = getDate(afternoon.getStartHour()-1,30);
            Date afternoonDateEnd = getDate(afternoon.getEndHour(),30);
            if(betweenDate(toSearchDate,afternoonDateStart,afternoonDateEnd)){
                timeAllowed = false;
            }
            if(timeAllowed == true){
                if(betweenDate(toSearchDate,warningStart,afternoonDateStart)){
                    System.out.println("THING ABOUT WHAT YOU ARE DOING YOU HAVE ONLY "+ obtainMinutesDifference(afternoonDateStart,toSearchDate)+" MINS TO BEAT THE PICO Y PLACA");
                }
            }
        }
        return timeAllowed;
    }





    private boolean verifyDataAndAssignValues(String plate, String date, String time,PlateVo plateVo) {
        boolean verified = false;
        if (isNumber(plate)) {
            plateVo.setPlateInt(Integer.parseInt(plate));
            System.out.println("YOUR PLATE NUMBER IS: " + plate);
            plateVo.setDateObj(obtainDateObj(date));
            if (plateVo.getDateObj() != null) {
                plateVo.setTimeVo(obtainTimeDouble(time));
                if (plateVo.getTimeVo() != null) {
                    verified = true;

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
