package com;

import java.util.logging.Logger;

public class Main {


    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
	// write your code here

        String plate = args[0];
        String date = args[1];
        String time = args[2];


        PlateVerifierInterface plateVerifier = new PlateVerifierClass();
        String result =    plateVerifier.verifyPlate(plate,date,time);
        System.out.println(result);
    }
}
