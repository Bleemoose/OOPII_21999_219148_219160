package OOPII_21999_219148_219160;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class App {
    private JButton addNewTravellerButton;
    private JPanel App;
    private JButton viewTravellersButton;
    private JButton recommendMeACityButton;
    private JButton addNewCityToButton;
    private JButton saveAndExitButton;
    private JTextArea welcomePleaseMakeATextArea;
    private static HashMap<String, City> cityMap;
    private static ArrayList<Traveller> travellerList;
    static JFrame frame = new JFrame("App");
    static ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT); //initialize jackson mapper


    //adds a city into the HashMap and handles the errors (returns true if city was added successfully)
    public static boolean addNewCity(HashMap<String, City> cityMap, String newCityName) throws InvalidInputException, IOException {
        System.out.println("Please wait,retrieving city information...");
        City newCity = new City(newCityName);
        cityMap.put(newCity.getName(), newCity);
        return true;
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
    }

    public static void main(String[] args) throws IOException, InvalidInputException, SQLException {
        //Here we handle the connection the database

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
            String deserializedJsonString = Files.readString(f.toPath());
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
