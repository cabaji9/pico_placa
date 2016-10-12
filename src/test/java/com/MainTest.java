package com;


import org.junit.Assert;
import org.junit.Test;

/**
 * Created by hw on 10/11/16.
 */
public class MainTest {


    PlateVerifierInterface plateVerifier = new PlateVerifierClass();

    @Test
    public void testMain() {
        String[] args = {"9830", "20/05/2016", "19:29"};
        assertResultsTrue(args, "PICO Y PLACA");
        args[2] = "19:99";
        assertResultsTrue(args, "MINUTE MUST BE IN RANGE");
        args[2] = "19:31";
        assertResultsTrue(args, "YOU CAN CIRCULATE");
        args[0] = "9831";
        assertResultsTrue(args, "YOU CAN CIRCULATE");
        args[1] = "16/05/2016"; args[2]="7:29";
        assertResultsTrue(args, "PICO Y PLACA");
        args[2]="6:29";
        assertResultsTrue(args, "YOU CAN CIRCULATE");
        args[2]="9:30";
        assertResultsTrue(args, "YOU CAN CIRCULATE");
        args[1] = "15/05/2016";
        assertResultsTrue(args, "YOU CAN CIRCULATE");
        args[1] = "14/05/2016";
        assertResultsTrue(args, "YOU CAN CIRCULATE");
        args[0] = "asdfads";
        assertResultsTrue(args, "YOUR VEHICLE PLATE");
        args[0] = "9830"; args[1]="98798789";
        assertResultsTrue(args, "YOU DIDNT INPUT A CORRECT DATE REMEMBER");
        args[1]="20/05/2016"; args[2]="asdfads";
        assertResultsTrue(args, "CORRECT TIME REMEMBER ");
        args[1]=""; args[2]="";
        assertResultsTrue(args, "YOU DIDNT INPUT A CORRECT DATE");



    }




    private void assertResultsTrue(String args[], String resultMsg){
        String result = plateVerifier.verifyPlate(args[0], args[1], args[2]);
        System.out.println(result);
        Assert.assertTrue(result.contains(resultMsg));
        System.out.println("*****************************************");
    }




}
