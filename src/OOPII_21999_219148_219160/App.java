package OOPII_21999_219148_219160;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class App {
    private JButton addNewTravellerButton;
    private JPanel App;
    private JButton viewTravellersButton;
    private JButton recommendMeACityButton;
    private JButton addNewCityToButton;
    private JButton saveAndExitButton;
    private JTextArea welcomePleaseMakeATextArea;


    public App() {
        addNewTravellerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Biribas");
            }
        });
        addNewTravellerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().App);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);


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
    }
}
