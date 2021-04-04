package OOPII_21999_219148_219160;

import java.io.IOException;
/*
WHEN CREATING A CITY IT SHOULD BE ALWAYS FOLLOWED BY A COMMA AND THE TWO LETTER CODE OF THE COUNTRY
 */
public class Main {
    public static void main(String[] args) {
        City AthensC= new City("Athens,GR");
        int [] pare =  {10,13,27,8,2,5,10,2,31,10};
        ElderTraveller test= new ElderTraveller( pare , AthensC.getGeodesic_vector());
        City RomeC = new City("Rome,IT");
        for (int i = 0; i < 10; i++){
            System.out.println(RomeC.getTerms_vector()[i]);
        }
        System.out.println(test.calculate_similarity(RomeC));
        City TestC = new City("gsdfhjigopfghjfdsg");
    }
}
