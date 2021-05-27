package OOPII_21999_219148_219160;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AddCityWindow {
    private JTextField CityNameTextField;
    private JButton OKButton;
    private JPanel Panel;
    private static Boolean added=false;
    static JFrame frame = new JFrame("Add New Traveller");

    public AddCityWindow() {
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AddCityWindowBackend();
                } catch (InvalidInputException invalidInputException) {
                    JOptionPane.showMessageDialog(frame, "Invalid Input!\nMake sure you include a comma and the two letter country code in the current city field!", "Invalid Input!", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(frame, "City Not Found!", "Not Found!", JOptionPane.ERROR_MESSAGE);
                }
                if(added){
                    JOptionPane.showMessageDialog(frame,"City added successfully!","Success",JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                }
            }
        });
    }

    private void AddCityWindowBackend() throws InvalidInputException, IOException {
        String newCityName = CityNameTextField.getText();
        if (!App.getCityMap().containsKey(newCityName)) {
            added = App.addNewCity(App.getCityMap(), newCityName);
        }else{
            JOptionPane.showMessageDialog(frame,"City already exists in database!");
        }


    }

    public static void main(String[] args) {
        added=false;
        frame.setContentPane(new AddCityWindow().Panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.setVisible(true);
    }
}
