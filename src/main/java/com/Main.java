package com;

import com.constants.MessagesEnum;

import java.util.logging.Logger;

public class Main {



    public static void main(String[] args) {
        if(args != null && args.length == 3) {
            String plate = args[0];
            String date = args[1];
            String time = args[2];
            PlateVerifierInterface plateVerifier = new PlateVerifierClass();
            String result = plateVerifier.verifyPlate(plate, date, time);
            System.out.println(result);
        }
        else{
            System.out.println(MessagesEnum.PARAM_INPUT_MSG.getMsg());
            System.out.println(MessagesEnum.PARAM_INPUT_MSG_EXAMPLE.getMsg());

        }
    }
}
