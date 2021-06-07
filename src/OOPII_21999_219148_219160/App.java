package OOPII_21999_219148_219160;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    private JButton addNewTravellerButton;
    private JPanel App;
    private JButton viewTravellersButton;
    private JButton recommendMeACityButton;
    private JButton addNewCityToButton;
    private JButton saveAndExitButton;
    private static HashMap<String, City> cityMap;
    private static ArrayList<Traveller> travellerList;
    static JFrame frame = new JFrame("App");
    static ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT); //initialize jackson mapper


    //adds a city into the HashMap and handles the errors (returns true if city was added successfully)
    public static boolean addNewCity(HashMap<String, City> cityMap, String newCityName) throws InvalidInputException, IOException, InterruptedException {
        System.out.println("Please wait,retrieving city information...");
        City newCity = new City(newCityName);
        cityMap.put(newCity.getName(), newCity);
        return true;
    }



    //cityMap is already part of App class so you can just change it and not use it as parameter
    public static ArrayList<City> recommendCity(Traveller traveller,HashMap<String, City> cityMap,int returnAmount){
        ArrayList<City> returnArr = new ArrayList<>();
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
        for (int j = 1; j <= returnAmount ; j++){
            returnArr.add(cityMap.get(tmp[tmp.length-j][0]));
        }
        return returnArr;
    }

    public static String[] recommendCityCollaborative(Traveller currentTraveller, int returnAmount){
        System.out.println(currentTraveller.getFullName() + " " + currentTraveller.getClass());
        List<Traveller> tempList=travellerList.stream().filter(t->t.getFullName()!= currentTraveller.getFullName()).filter(t->t.getLastRecommendedCity() != null).filter(t->t.getClass() == currentTraveller.getClass())
                .sorted((Traveller t1,Traveller t2)->t2.compareTo(currentTraveller) - t1.compareTo(currentTraveller))
                .collect(Collectors.toList());
        String[] results=new String[returnAmount];
        for (int i = 0; i < returnAmount; i++) {
            results[i]=tempList.get(i).getLastRecommendedCity();
        }
        return results;
    }

    public App() throws InvalidInputException, SQLException, IOException {
        addNewTravellerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             NewTravellerWindow.main(null);
            }
        });
        saveAndExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clients cl_temp = new Clients(); //make new template class for json serialization
                cl_temp.setTravellers(travellerList);
                String jsonDataString = null; //create json string
                try {
                    jsonDataString = mapper.writeValueAsString(cl_temp);
                } catch (JsonProcessingException jsonProcessingException) {
                    jsonProcessingException.printStackTrace();
                }
                BufferedWriter writer = null;
                try {
                    writer = new BufferedWriter(new FileWriter("travellers.json"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                try {
                    writer.write(jsonDataString);//write string to file
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                try {
                    writer.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                try {
                    DbConnector.SaveToDB(cityMap); //save new cities to db
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                System.exit(0);
            }
        });
        viewTravellersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowTravellerWindow.main(null);
            }
        });
        addNewCityToButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddCityWindow.main(null);
            }
        });
        recommendMeACityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RecommendCityWindow.main(null);
            }
        });
    }

    public static void main(String[] args) throws IOException, InvalidInputException, SQLException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //Here we handle the connection the database
        FlatDarkLaf.setup();
        UIManager.setLookAndFeel( new FlatDarkLaf() );
        while (true) {
            try {
                DbConnector dbc = new DbConnector();

                //if connection was successful break the loop and resume the programme
                break;

            } catch (SQLException throwables) {
                DataBaseConnectionError.main(null);


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        //create json file
        mapper.enableDefaultTyping(); //Deprecated,but functional
        File f = new File("travellers.json");
        travellerList = new ArrayList<>();
        if (f.exists()) { //try to load travellers only if json file exists
            String deserializedJsonString = new String( Files.readAllBytes( Paths.get(f.toPath().toString()) ) );
            Clients cl_temp = mapper.readValue(deserializedJsonString, Clients.class); //deserialize from template class clients
            travellerList = (ArrayList<Traveller>) cl_temp.getTravellers();
        }
        //load the cities from db
        cityMap = DbConnector.LoadFromDB();
        frame.setContentPane(new App().App);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.pack();
        frame.setVisible(true);
        System.out.println(Arrays.toString(recommendCityCollaborative(travellerList.get(0),4)));
    }

    public static HashMap<String, City> getCityMap() {
        return cityMap;
    }

    public static void setCityMap(HashMap<String, City> newcityMap) {
        cityMap = newcityMap;
    }

    public static ArrayList<Traveller> getTravellerList() {
        return travellerList;
    }

    public static void setTravellerList(ArrayList<Traveller> newtravellerList) {
        travellerList = newtravellerList;
    }
}
