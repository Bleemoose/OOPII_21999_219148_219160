package OOPII_21999_219148_219160;

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
                if ( (row = table1.getSelectedRow()) != -1){
                    String value = table1.getModel().getValueAt(row, column).toString();
                    System.out.println(value);
                    int indx = row;
                    City recommendedCity = App.recommendCity(App.getTravellerList().get(indx),App.getCityMap());
                    App.getTravellerList().get(indx).setLastRecommendedCity(recommendedCity.getName());
                    App.getTravellerList().get(indx).setTimestamp(new Date());
                    JOptionPane.showMessageDialog(frame,"You should visit: " + recommendedCity.getName());
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
