package OOPII_21999_219148_219160;

import java.io.IOException;
import java.util.*;

/*
WHEN CREATING A CITY IT SHOULD BE ALWAYS FOLLOWED BY A COMMA AND THE TWO LETTER CODE OF THE COUNTRY
 */
public class Main {

    public static boolean addNewCity(HashMap cityMap, String newCityName) {
        try {
            City newCity = new City(newCityName);
            cityMap.put(newCity.getName(), newCity);
            return true;
        } catch (InvalidCityException e) {
            System.out.println("City: " + newCityName + " Not found");
            return false;

        } catch (InvalidInputException e) {
            System.out.println("Input: " + newCityName + " is invalid,please add a comma and the countries two letter code");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) throws ArrayListException {
        int input,age;
        String name,cityName;
        boolean flag;
        Scanner scanner  = new Scanner(System.in);
        ArrayList<Traveller> travellerList = new ArrayList<>();
        HashMap<String,City> cityMap = new HashMap<>();

       while (true){
           try{
               System.out.println("fadasou menu edw");
               input = scanner.nextInt();
               scanner.nextLine();
               if (input == 1){
                   flag = false;
                   System.out.println("Give name");
                   name = scanner.nextLine();
                   System.out.println("Give age");
                   age = scanner.nextInt();
                   scanner.nextLine();
                   while (!flag) {
                       System.out.println("Give current city");
                       cityName = scanner.nextLine();
                       if (!cityMap.containsKey(cityName)) {
                        flag =  addNewCity(cityMap,cityName);
                       }else{
                           flag = true;
                       }
                   }


               }




           }catch (Exception e){

           }
       }
    }



}
