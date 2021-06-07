package OOPII_21999_219148_219160;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class RecommendCityWindow {
    private JTable table1;
    private JPanel panel1;
    private JButton OKButton;
    private JComboBox comboBox1;
    private JRadioButton recommendByCriteriaRadioButton;
    private JRadioButton recommendBySimilarTravellersRadioButton;
    private ButtonGroup group = new ButtonGroup();
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


    public RecommendCityWindow() {
        group.add(recommendByCriteriaRadioButton);
        group.add(recommendBySimilarTravellersRadioButton);
        RecommendCityWindow.MyTableModel tableModel = new MyTableModel();
        table1.setModel(tableModel);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        table1.addComponentListener(new ComponentAdapter() {
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int column = 0;
                int row;
                int howMany = Integer.parseInt(comboBox1.getSelectedItem().toString());
                ArrayList<City> recommendedCities = new ArrayList<>(howMany);
                String printString=null;
                if ( (row = table1.getSelectedRow()) != -1){
                    String value = table1.getModel().getValueAt(row, column).toString();

                    if(recommendBySimilarTravellersRadioButton.isSelected()) {
                        recommendedCities = App.recommendCityCollaborative(App.getTravellerList().get(row), howMany);
                    }
                    if(recommendByCriteriaRadioButton.isSelected()) {
                        recommendedCities = App.recommendCity(App.getTravellerList().get(row), App.getCityMap(), howMany);

                    }
                    App.getTravellerList().get(row).setLastRecommendedCity(recommendedCities.get(0).getName());
                    App.getTravellerList().get(row).setTimestamp(new Date());

                    printString = ""; //used to print the dialog later
                    for (int i = 0; i < howMany; i++) {
                        System.out.println(recommendedCities.get(i).getName());
                        printString += recommendedCities.get(i).getName() + "\n";
                    }
                    JOptionPane.showMessageDialog(frame,"You should visit:\n" + printString);
                }
            }
        });
    }

    public static void main(String[] args) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(new RecommendCityWindow().panel1);
        frame.pack();
        frame.setVisible(true);

    }
}
