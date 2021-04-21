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
        String name,cityName = null;
        boolean flag;
        Scanner scanner  = new Scanner(System.in);
        ArrayList<Traveller> travellerList = new ArrayList<>();
        HashMap<String,City> cityMap = new HashMap<>();

       while (true){
           try{
               System.out.println("Main Menu:\n1.New Traveller");
               input = scanner.nextInt();
               scanner.nextLine();
               if (input == 1){
                   String [] term_names={"museum","history","car","bike","food","mountain","cafe","shopping","sea","nightlife"};
                   int [] traveller_terms= new int[term_names.length];
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
                   System.out.println("Please enter from 1 to 10 how much you like the following");
                   for(int i=0; i<term_names.length;i++){
                       System.out.println(term_names[i]);
                       traveller_terms[i]=scanner.nextInt();
                       scanner.nextLine();
                   }
                   if(age>60 && age<=115){
                       travellerList.add(new ElderTraveller(traveller_terms,cityMap.get(cityName).getGeodesic_vector(),name));
                   }
                   if(age>25 && age<=60){
                       travellerList.add(new MiddleTraveller(traveller_terms,cityMap.get(cityName).getGeodesic_vector(),name));
                   }
                   if(age>=16 && age<=25){
                       travellerList.add(new YoungTraveler(traveller_terms,cityMap.get(cityName).getGeodesic_vector(),name));
                   }
               }

           }catch (Exception e){

           }
       }
    }



}
