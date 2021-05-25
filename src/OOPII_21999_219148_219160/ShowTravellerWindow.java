package OOPII_21999_219148_219160;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class ShowTravellerWindow {
    private JTable table1;
    private JPanel panel1;
    private JButton OKButton;
    static JFrame frame = new JFrame("Travellers");

    class MyTableModel extends AbstractTableModel {

        private String[] columnNames = {"Full Name", "Current Location",
                "Attributes","Last Recommended City","Timestamp"};

        Object[][] data = populateList();

        public Object[][] populateList(){
            ArrayList<Traveller> travellerList=App.getTravellerList();
            Object[][] tempArr=new Object[travellerList.size()][];
            for (int i = 0; i < travellerList.size(); i++) {
                tempArr[i]=new Object[]{travellerList.get(i).getFullName(), Arrays.toString(travellerList.get(i).getCurrentLocation()),Arrays.toString(travellerList.get(i).getTermsRating()),travellerList.get(i).getLastRecommendedCity(),travellerList.get(i).getTimestamp()};
            }
            return tempArr;
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }
    }


    public ShowTravellerWindow() {
       MyTableModel tableModel = new MyTableModel();
        table1.setModel(tableModel);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    public static void main(String[] args) {
        frame.setContentPane(new ShowTravellerWindow().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
