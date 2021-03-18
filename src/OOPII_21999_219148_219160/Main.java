package OOPII_21999_219148_219160;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        City myCity= new City("Athens");
        System.out.println("For city: "+myCity.getName()+"\nLatitude: "+myCity.getGeodesic_vector()[1]+"\nLongitude: "+myCity.getGeodesic_vector()[0]);
        int [][] pare = new int[0][];
        ElderTraveller test= new ElderTraveller( pare , myCity.getGeodesic_vector());

    }
}
