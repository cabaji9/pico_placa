package com;

import com.constants.MessagesEnum;
import com.constants.PlateAllowedDateEnum;
import com.constants.PlateAllowedTimeEnum;
import com.vo.PlateVo;
import com.vo.TimeVo;

import java.util.Date;

/**
 * Created by HW on 10/11/16.
 */
public class PlateVerifierClass extends PlateVerifierCommon implements PlateVerifierInterface {

    public String verifyPlate(String plate, String date, String time) {
        //date should be DD/MM/YYYY example : 20/05/1981
        //time should be HH:MM
        System.out.println(MessagesEnum.PRIMARY_MSG.getMsg());
        String msgToUser = "";
        try {

            PlateVo plateVo = new PlateVo();

            if (verifyDataAndAssignValues(plate, date, time, plateVo)) {
                Integer lastDigit = extractLastDigit(plateVo.getPlateInt());
                Integer dayOfWeek = getDayOfWeek(plateVo.getDateObj());
                PlateAllowedDateEnum plateAllowedDateEnum = obtainDateEnumByDayOfWeek(dayOfWeek);
                if (plateAllowedDateEnum != null) {
                    System.out.println(MessagesEnum.DAY_OF_WEEK_MSG.getMsg() + plateAllowedDateEnum.name());
                    if (!verifyPlateDigitAllowedOnDay(lastDigit, plateAllowedDateEnum)) {
                        System.out.println(MessagesEnum.PICO_PLACA_HAS_MSG.getMsg());
                        if (!verifyTimeAllowed(plateVo.getTimeVo())) {
                            msgToUser = MessagesEnum.PICO_PLACA_WARNING_MSG.getMsg() + plateVo.getTimeVo().toString();
                        }
                    }
                }
            }
            if (msgToUser.isEmpty()) {
                msgToUser = MessagesEnum.PICO_PLACA_SAFE_MSG.getMsg();
            }
        }catch(Exception e){
            msgToUser = e.getMessage();
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
                    System.out.println(MessagesEnum.PICO_PLACA_BEAT_MSG_ONE.getMsg() + obtainMinutesDifference(morninStart, toSearchDate) + MessagesEnum.PICO_PLACA_BEAT_MSG_TWO.getMsg());
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
                    System.out.println(MessagesEnum.PICO_PLACA_THING_MSG_ONE.getMsg() + obtainMinutesDifference(afternoonDateStart, toSearchDate) + MessagesEnum.PICO_PLACA_THING_MSG_TWO.getMsg());
                }
            }
        }
        return timeAllowed;
    }





    private boolean verifyDataAndAssignValues(String plate, String date, String time,PlateVo plateVo) throws Exception{
        boolean verified = false;
        if (isNumber(plate)) {
            plateVo.setPlateInt(Integer.parseInt(plate));
            System.out.println(MessagesEnum.PLATE_NUMBER_MSG.getMsg() + plate);
            plateVo.setDateObj(obtainDateObj(date));
            if (plateVo.getDateObj() != null) {
                plateVo.setTimeVo(obtainTimeDouble(time));
                if (plateVo.getTimeVo() != null) {
                    verified = true;

                } else {
                    throw new Exception(MessagesEnum.ERROR_TIME_WRONG_MSG.getMsg() + time);
                }
            } else {
                throw new Exception(MessagesEnum.ERROR_DATE_WRONG_MSG.getMsg()+ date);
            }
        } else {
            throw new Exception(MessagesEnum.PLATE_ERROR_MSG.getMsg() + plate);
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
