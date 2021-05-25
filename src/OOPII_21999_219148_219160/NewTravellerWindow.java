package OOPII_21999_219148_219160;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class NewTravellerWindow {
    private JPanel panel1;
    private  JTextField NameTextBox;
    private  JTextField CurrentCityTextBox;
    private  JTextField AgeTextBox;
    private JButton OKButton;
    private JComboBox musBox;
    private JComboBox hisBox;
    private JComboBox carBox;
    private JComboBox bikeBox;
    private JComboBox foodBox;
    private JComboBox mountBox;
    private JComboBox cafeBox;
    private JComboBox shopBox;
    private JComboBox seaBox;
    private JComboBox nightBox;
    static JFrame frame = new JFrame("Add New Traveller");
    private static Boolean added=false;


    public NewTravellerWindow() {
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    NewTravellerBackend();
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(frame, "City Not Found!", "Not Found!", JOptionPane.ERROR_MESSAGE);
                } catch (InvalidInputException invalidInputException) {
                    JOptionPane.showMessageDialog(frame, "Invalid Input!\nMake sure you include a comma and the two letter country code in the current city field!", "Invalid Input!", JOptionPane.ERROR_MESSAGE);
                } catch (InvalidAgeException invalidAgeException) {
                    JOptionPane.showMessageDialog(frame, "Invalid Age!\nPlease Enter An Age Between 16 And 115", "Invalid Age!", JOptionPane.ERROR_MESSAGE);
                }
                if(added){
                    frame.dispose();
                }
            }
        });
    }

    private void NewTravellerBackend() throws IOException, InvalidInputException, InvalidAgeException {
        HashMap<String,City> cityMap =App.getCityMap();
        ArrayList<Traveller> travellerList= App.getTravellerList();
        String name=NameTextBox.getText();
        int age=Integer.parseInt(AgeTextBox.getText());
        String cityName=CurrentCityTextBox.getText();
        int[] traveller_terms = {Integer.parseInt(musBox.getSelectedItem().toString()),Integer.parseInt(hisBox.getSelectedItem().toString()),Integer.parseInt(carBox.getSelectedItem().toString()),Integer.parseInt(bikeBox.getSelectedItem().toString()),Integer.parseInt(foodBox.getSelectedItem().toString()),Integer.parseInt(mountBox.getSelectedItem().toString()), Integer.parseInt(cafeBox.getSelectedItem().toString()),Integer.parseInt(shopBox.getSelectedItem().toString()),Integer.parseInt(seaBox.getSelectedItem().toString()),Integer.parseInt(nightBox.getSelectedItem().toString())};
        Boolean flag = false;
        while (!flag) {
            if (!cityMap.containsKey(cityName)) {
                flag = App.addNewCity(cityMap, cityName);
            } else {
                flag = true;
            }
        }
       //create object based on age
            if (age > 60 && age <= 115) {
                travellerList.add(new ElderTraveller(traveller_terms, cityMap.get(cityName).getGeodesic_vector(), name));
            } else if (age > 25 && age <= 60) {
                travellerList.add(new MiddleTraveller(traveller_terms, cityMap.get(cityName).getGeodesic_vector(), name));
            } else if (age >= 16 && age <= 25) {
                travellerList.add(new YoungTraveler(traveller_terms, cityMap.get(cityName).getGeodesic_vector(), name));
            } else {
                throw new InvalidAgeException(String.valueOf(age));
            }
            App.setTravellerList(travellerList);
            App.setCityMap(cityMap);
       added=true;
    }
    public static void main(String[] args) {
        frame.setContentPane(new NewTravellerWindow().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
