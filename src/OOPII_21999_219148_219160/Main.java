package OOPII_21999_219148_219160;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        City myCity= new City("Athens");
        System.out.println("For city: "+myCity.getName()+"\nLatitude: "+myCity.getGeodesic_vector()[1]+"\nLongitude: "+myCity.getGeodesic_vector()[0]);
        int [][] pare = new int[0][];
        YoungTraveler test= new YoungTraveler( pare , myCity.getGeodesic_vector());
        System.out.println(test.calculate_similarity(myCity));

    }
}
