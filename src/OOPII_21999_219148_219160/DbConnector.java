package OOPII_21999_219148_219160;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class DbConnector {

    /*
    todo: find a way to access another user's table and store all the data there
     */


    static Connection connection;

    //Name of the table that data will be stored
    static String tablename = "CITIES_OOPII";



    public DbConnector() throws SQLException, ClassNotFoundException {

        connection = DbLogin();

        if(!tableExists(connection)){
            CreateTable(connection);
        }
    }

    private static Connection  DbLogin() throws ClassNotFoundException, SQLException {
        Scanner myScn=new Scanner(System.in);
        Class.forName("oracle.jdbc.driver.OracleDriver");
        System.out.println("Enter Oracle DB Username");
        String username=myScn.nextLine();
        System.out.println("Enter Oracle DB Password");
        String password=myScn.nextLine();
        Connection con= DriverManager.getConnection("jdbc:oracle:thin:@oracle12c.hua.gr:1521:orcl",username,password);
        System.out.println("Connected to DB");
        return con;
    }

    private static boolean tableExists(Connection connection) throws SQLException {
        boolean exists=false;
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet res = meta.getTables(null, null, tablename,
                new String[] {"TABLE"});
        while (res.next()) {
            String name=res.getString("TABLE_NAME");
            exists=name.contains(tablename);
        }
        return exists;
    }

    private static void CreateTable(Connection con) throws SQLException {
        String sql = "CREATE TABLE "+tablename+" (" +
                "NAME varchar(128)," +
                "LAT   float," +
                "LON   float," +
                "ATTR1 number(5)," +
                "ATTR2  number(5)," +
                "ATTR3  number(5)," +
                "ATTR4  number(5)," +
                "ATTR5  number(5)," +
                "ATTR6  number(5)," +
                "ATTR7  number(5)," +
                "ATTR8  number(5)," +
                "ATTR9  number(5)," +
                "ATTR10  number(5)" +
                ")";
        Statement stmt=con.createStatement();
        stmt.executeUpdate(sql);
    }

   public static void SaveToDB(HashMap<String, City> cityMap) throws SQLException {
       Iterator<Map.Entry<String, City>> it = cityMap.entrySet().iterator();
       while(it.hasNext())
       {
           Map.Entry<String, City> entry = it.next();
            Statement stmt = connection.createStatement();
            ResultSet result;
            result = stmt.executeQuery("select count(*) AS FOUNDAT from CITIES_OOPII where NAME="+"'"+entry.getValue().getName()+"'");
            result.next();
            if(result.getInt("FOUNDAT")==0) {
                String insStr = "INSERT INTO CITIES_OOPII VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement insertionStmt = connection.prepareStatement(insStr);
                insertionStmt.setString(1,entry.getValue().getName());
                insertionStmt.setFloat(2,entry.getValue().getGeodesic_vector()[1]);
                insertionStmt.setFloat(3,entry.getValue().getGeodesic_vector()[0]);
                for (int j = 0; j <10; j++) {
                    insertionStmt.setInt(j+4,entry.getValue().getTerms_vector()[j]);
                }
                insertionStmt.executeUpdate();
            }
        }
   }

   public static HashMap<String,City> LoadFromDB() throws SQLException, InvalidInputException, IOException {
       HashMap<String,City> temp_map= new HashMap<>();
       Statement stmt=connection.createStatement();
       String sql="SELECT * FROM CITIES_OOPII";
       ResultSet rs=stmt.executeQuery(sql);
       while (rs.next()) {
           String Name=rs.getString("NAME");
           float  Latitude=rs.getFloat("LAT");
           float  Longitude=rs.getFloat("LON");
           int    attr1=rs.getInt("ATTR1");
           int    attr2=rs.getInt("ATTR2");
           int    attr3=rs.getInt("ATTR3");
           int    attr4=rs.getInt("ATTR4");
           int    attr5=rs.getInt("ATTR5");
           int    attr6=rs.getInt("ATTR6");
           int    attr7=rs.getInt("ATTR7");
           int    attr8=rs.getInt("ATTR8");
           int    attr9=rs.getInt("ATTR9");
           int    attr10=rs.getInt("ATTR10");
           float[] temp_coord={Longitude,Latitude};
           int[] temp_attr={attr1,attr2,attr3,attr4,attr5,attr6,attr7,attr8,attr9,attr10};
           temp_map.put(Name,new City(Name,temp_coord,temp_attr));
           System.out.println("Loaded:" + Name + " from DB");
       }
       return temp_map;
   }

}
