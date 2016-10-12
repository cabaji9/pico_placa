package com;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {


    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
	// write your code here
        logger.info("THE PROGRAM WILL VERIFY IF YOU CAN TAKE YOUR CAR");

        String plate = args[0];
        String date = args[1];
        String time = args[2];


        PlateVerifierInterface plateVerifier = new PlateVerifierClass();
        String result =    plateVerifier.verifyPlate(plate,date,time);
        logger.info(result);
    }
}
