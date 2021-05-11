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

    //adds a city into the HashMap and handles the errors (returns true if city was added successfully)
    public static boolean addNewCity(HashMap<String, City> cityMap, String newCityName) {
        try {
            City newCity = new City(newCityName);
            cityMap.put(newCity.getName(), newCity);
            return true;
        } catch (InvalidInputException e) {
            System.out.println("Input: " + newCityName + " is invalid,please add a comma and the countries two letter code");
            return false;
        } catch (IOException e) {
            System.out.println("City: " + newCityName + " Not found");
            return false;
        }
    }

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, InvalidInputException {

        //establish connection if failed program stops
        new DbConnector();


        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT); //initialize jackson mapper
        mapper.enableDefaultTyping(); //Deprecated,but functional
        boolean run=true;
        int input,age;
        String name,cityName = null;
        boolean flag;
        Scanner scanner  = new Scanner(System.in);

        //create json file
        File f=new File("travellers.json");
        ArrayList<Traveller> travellerList=new ArrayList<>();
        if(f.exists()){ //try to load travellers only if json file exists
            String deserializedJsonString= Files.readString(f.toPath());
            Clients cl_temp=mapper.readValue(deserializedJsonString,Clients.class); //deserialize from template class clients
            travellerList= (ArrayList<Traveller>) cl_temp.getTravellers();
        }
        //load the cities from db
        HashMap<String,City> cityMap =DbConnector.LoadFromDB();


        //start of menu
       while (run){
           try{
               System.out.println("Main Menu:\n1.New traveller \n2.Print loaded travellers\n3.Save data and exit");
               input = scanner.nextInt();
               scanner.nextLine();
               switch (input){
                   case 1: //traveller creation portion
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
                           System.out.println("Please wait,retrieving city information....");
                        flag =  addNewCity(cityMap,cityName);
                       }else{
                           flag = true;
                       }
                   }
                   System.out.println("Please enter from 1 to 10 how much you like the following");
                   for(int i=0; i<term_names.length;i++){
                       do {
                           System.out.println(term_names[i]);
                           traveller_terms[i] = Integer.parseInt(scanner.nextLine());
                       }while(traveller_terms[i]<1 || traveller_terms[i] >10);
                   }
                   try { //create object based on age
                       if (age > 60 && age <= 115) {
                           travellerList.add(new ElderTraveller(traveller_terms, cityMap.get(cityName).getGeodesic_vector(), name));
                       }
                       else if (age > 25 && age <= 60) {
                           travellerList.add(new MiddleTraveller(traveller_terms, cityMap.get(cityName).getGeodesic_vector(), name));
                       }
                       else if (age >= 16 && age <= 25) {
                           travellerList.add(new YoungTraveler(traveller_terms, cityMap.get(cityName).getGeodesic_vector(), name));
                       } else {
                           throw new InvalidAgeException(String.valueOf(age));
                       }
                   }catch (InvalidAgeException e){
                       System.out.println("Age: "+age+" is invalid,please specify an age between 16 and 115,traveller will not be saved!");
                   }
                   break;

                   case 2:
                       for(int i = 0 ; i < travellerList.size();i++){
                           System.out.println(travellerList.get(i).getFullName());
                       }


                       break;

                   case 3: //save and exit portion
                   Clients cl_temp=new Clients(); //make new template class for json serialization
                   cl_temp.setTravellers(travellerList);
                   String jsonDataString = mapper.writeValueAsString(cl_temp); //create json string
                   BufferedWriter writer = new BufferedWriter(new FileWriter("travellers.json"));
                   writer.write(jsonDataString);//write string to file
                   writer.close();
                   DbConnector.SaveToDB(cityMap); //save new cities to db
                   run=false;
                   break;
               }

           }catch (Exception e){
               e.printStackTrace();
           }
       }
    }



}
