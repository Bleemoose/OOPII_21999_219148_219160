package OOPII_21999_219148_219160;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.*;

/*
WHEN CREATING A CITY IT SHOULD BE ALWAYS FOLLOWED BY A COMMA AND THE TWO LETTER CODE OF THE COUNTRY
 */

public class Main {


    public static City recommendCity(Traveller traveller,HashMap<String, City> cityMap){
        //col 0 for name col 1 similarity
        int i = 0;
        String[][] tmp = new String[cityMap.size()][2];
        //iterator for the city hashmap
        //move to next element
        for (Map.Entry<String, City> entry : cityMap.entrySet()) {
            tmp[i][0] = entry.getValue().getName();
            tmp[i][1] = String.valueOf(traveller.calculate_similarity(entry.getValue()));
            i++;
        }
        Arrays.sort(tmp, Comparator.comparingDouble(o -> Double.parseDouble(o[1])));
        return cityMap.get(tmp[tmp.length-1][0]);
    }

    //adds a city into the HashMap and handles the errors (returns true if city was added successfully)
    public static boolean addNewCity(HashMap<String, City> cityMap, String newCityName) throws InvalidInputException, IOException {
        System.out.println("Please wait,retrieving city information...");
            City newCity = new City(newCityName);
            cityMap.put(newCity.getName(), newCity);
            return true;
    }

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, InvalidInputException {

        //establish connection if failed program stops
        new DbConnector();


        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT); //initialize jackson mapper
        mapper.enableDefaultTyping(); //Deprecated,but functional
        boolean run = true;
        int input, age;
        String name, cityName = null;
        boolean flag;
        Scanner scanner = new Scanner(System.in);

        //create json file
        File f = new File("travellers.json");
        ArrayList<Traveller> travellerList = new ArrayList<>();
        if (f.exists()) { //try to load travellers only if json file exists
            String deserializedJsonString = Files.readString(f.toPath());
            Clients cl_temp = mapper.readValue(deserializedJsonString, Clients.class); //deserialize from template class clients
            travellerList = (ArrayList<Traveller>) cl_temp.getTravellers();
        }
        //load the cities from db
        HashMap<String, City> cityMap = DbConnector.LoadFromDB();


        //start of menu
        while (run) {
            try {
                System.out.println("Main Menu:\n1.New traveller \n2.Print loaded travellers\n3.Recommend me a city\n4.Add new City\n5.Save data and exit");
                input = scanner.nextInt();
                scanner.nextLine();
                switch (input) {
                    case 1: //traveller creation portion
                        String[] term_names = {"museum", "history", "car", "bike", "food", "mountain", "cafe", "shopping", "sea", "nightlife"};
                        int[] traveller_terms = new int[term_names.length];
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
                                flag = addNewCity(cityMap, cityName);
                            } else {
                                flag = true;
                            }
                        }
                        System.out.println("Please enter from 1 to 10 how much you like the following");
                        for (int i = 0; i < term_names.length; i++) {
                            do {
                                System.out.println(term_names[i]);
                                traveller_terms[i] = Integer.parseInt(scanner.nextLine());
                            } while (traveller_terms[i] < 1 || traveller_terms[i] > 10);
                        }
                        try { //create object based on age
                            if (age > 60 && age <= 115) {
                                travellerList.add(new ElderTraveller(traveller_terms, cityMap.get(cityName).getGeodesic_vector(), name));
                            } else if (age > 25 && age <= 60) {
                                travellerList.add(new MiddleTraveller(traveller_terms, cityMap.get(cityName).getGeodesic_vector(), name));
                            } else if (age >= 16 && age <= 25) {
                                travellerList.add(new YoungTraveler(traveller_terms, cityMap.get(cityName).getGeodesic_vector(), name));
                            } else {
                                throw new InvalidAgeException(String.valueOf(age));
                            }
                        } catch (InvalidAgeException e) {
                            System.out.println("Age: " + age + " is invalid,please specify an age between 16 and 115,traveller will not be saved!");
                        }
                        break;

                    case 2:
                        for (int i = 0; i < travellerList.size(); i++) {
                            if (travellerList.get(i).getTimestamp() == null){
                                System.out.println(i + ". " + travellerList.get(i).getFullName() + " " + Arrays.toString(travellerList.get(i).getCurrentLocation()));
                            }else{
                                System.out.println(i + ". " + travellerList.get(i).getFullName() + " " + Arrays.toString(travellerList.get(i).getCurrentLocation())+ " last recommended: " + travellerList.get(i).getTimestamp().toString());
                            }

                        }

                        break;

                    case 3:
                        int indx;
                        do {
                            System.out.println("Give index of the traveller");
                            indx = Integer.parseInt(scanner.nextLine());
                        }while(indx > travellerList.size() && indx > 0);
                        City recommendedCity = recommendCity(travellerList.get(indx),cityMap);
                        travellerList.get(indx).setLastRecommendedCity(recommendedCity.getName());
                        travellerList.get(indx).setTimestamp(new Date());
                        System.out.println("You should visit :" + recommendedCity.getName());



                        break;
                    case 4: //Add new city portion
                        System.out.println("Type in the city name you want to add");
                        cityName = scanner.nextLine();
                        if (!cityMap.containsKey(cityName)) {
                            addNewCity(cityMap, cityName);
                        } else {
                            System.out.println("City already exists!");
                        }
                        break;


                    case 5: //save and exit portion
                        Clients cl_temp = new Clients(); //make new template class for json serialization
                        cl_temp.setTravellers(travellerList);
                        String jsonDataString = mapper.writeValueAsString(cl_temp); //create json string
                        BufferedWriter writer = new BufferedWriter(new FileWriter("travellers.json"));
                        writer.write(jsonDataString);//write string to file
                        writer.close();
                        DbConnector.SaveToDB(cityMap); //save new cities to db
                        run = false;
                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
