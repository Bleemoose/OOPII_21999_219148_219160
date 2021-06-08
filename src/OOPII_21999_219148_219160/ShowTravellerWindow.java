package OOPII_21999_219148_219160;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ShowTravellerWindow {
    private JTable table1;
    private JPanel panel1;
    private JButton OKButton;
    static JFrame frame = new JFrame("Travellers");



    //returns the position if traveller is found else returns -1
    public static int containsTravellerByName(String travellerName,ArrayList<Traveller> travellers){
        for (int i = 0; i < travellers.size() ; i++){
            if (travellers.get(i).getFullName().equals(travellerName)){
                return i;
            }
        }
        return -1;
    }


    public static ArrayList<Traveller> filterTravellerList(ArrayList<Traveller> travellerList){
        ArrayList<Traveller> filteredTravellerList = new ArrayList<>();


        int pos;
        for (int i =0 ; i < travellerList.size() ; i++){
            if (( pos = containsTravellerByName(travellerList.get(i).getFullName(),filteredTravellerList)) != -1){

                if(travellerList.get(i).getTimestamp() != null) {
                    if (travellerList.get(i).getTimestamp().compareTo(filteredTravellerList.get(pos).getTimestamp()) > 0) {
                        filteredTravellerList.remove(pos);
                        filteredTravellerList.add(travellerList.get(i));
                    }
                }
            }else{
                filteredTravellerList.add(travellerList.get(i));
            }

        }
    return filteredTravellerList;
    }

    class MyTableModel extends AbstractTableModel {

        private String[] columnNames = {"Full Name", "Current Location",
                "Attributes","Last Recommended City","Timestamp"};

        Object[][] data = populateList();

        public Object[][] populateList(){
            ArrayList<Traveller> travellerList= filterTravellerList(App.getTravellerList());
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
        table1.addComponentListener(new ComponentAdapter() {
        });


    }

    public static void main(String[] args) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.setContentPane(new ShowTravellerWindow().panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
