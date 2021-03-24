package OOPII_21999_219148_219160;

import java.io.IOException;
/*
WHEN CREATING A CITY IT SHOULD BE ALWAYS FOLLOWED BY A COMMA AND THE TWO LETTER CODE OF THE COUNTRY
 */
public class Main {
    public static void main(String[] args) throws IOException {
        City AthensC= new City("Athens,GR");
        int [] pare = new int[0];
        ElderTraveller test= new ElderTraveller( pare , AthensC.getGeodesic_vector());
        City RomeC = new City("Rome,IT");
        test.calculate_similarity(RomeC);
    }
}
