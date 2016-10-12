package com;

import com.vo.TimeVo;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by HW on 10/11/16.
 */
public class PlateVerifierCommon {
    private static Logger logger = Logger.getLogger(PlateVerifierCommon.class.getName());

    private static String DATE_FORMAT = "dd/MM/yyyy";


    protected boolean isNumber(String numberStr) {
        boolean isNumber = false;
        if (numberStr != null && !numberStr.isEmpty() && numberStr.length() == 4) {
            isNumber = StringUtils.isNumeric(numberStr);
        }
        return isNumber;
    }


    protected Date obtainDateObj(String dateStr) {
        Date date = null;
        if (dateStr != null && !dateStr.isEmpty()) {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
            String dateInString = dateStr;
            try {
                date = formatter.parse(dateInString);
                logger.info(" THE DATE INPUT IS: " + formatter.format(date));
            } catch (ParseException e) {
                //do nothing
            }
        }
        return date;

    }

    protected TimeVo obtainTimeDouble(String timeStr) {
        TimeVo timeVo = null;
        if (timeStr != null && !timeStr.isEmpty()) {
            try {
                String[] timeStrArray = timeStr.split(":");
                if (timeStrArray.length == 2) {
                    String hour = timeStrArray[0];
                    String minutes = timeStrArray[1];
                    Integer hourInt = Integer.parseInt(hour);
                    Integer minutesInt = Integer.parseInt(minutes);
                    if (isHourInRange(hourInt)) {
                        if (isMinutesInRange(minutesInt)) {
                            timeVo = new TimeVo();
                            timeVo.setHour(hourInt);
                            timeVo.setMinute(minutesInt);
                        } else {
                            logger.log(Level.SEVERE, "MINUTE MUST BE IN RANGE OF 0 TO 59");
                        }
                    } else {
                        logger.log(Level.SEVERE, "HOUR MUST BE IN RANGE OF 0 TO 24");
                    }
                }
            } catch (Exception e) {
                //do nothing.
            }
        }
        return timeVo;
    }


    private boolean isHourInRange(Integer hourInt) {
        boolean isHourInRange = false;
        if (hourInt >= 0 && hourInt <= 24) {
            isHourInRange = true;
        }
        return isHourInRange;
    }

    private boolean isMinutesInRange(Integer minuteInt) {
        boolean isMinutesInRange = false;
        if (minuteInt >= 0 && minuteInt <= 59) {
            isMinutesInRange = true;
        }
        return isMinutesInRange;
    }


    protected Integer extractLastDigit(Integer plate){
        String plateStr = plate.toString();
        char lastDigit= plateStr.charAt(plateStr.length() - 1);
        Integer lastDigitInt =  Character.getNumericValue(lastDigit);
        logger.info("YOUR PLATE LAST DIGIT IS: "+ lastDigitInt);
        return lastDigitInt;

    }


    protected Integer getDayOfWeek(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }


}
