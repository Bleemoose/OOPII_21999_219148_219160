package OOPII_21999_219148_219160;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DbConnector {

    static Connection connection;

    //Name of the table that data will be stored
    static String tablename = "CITIES_OOPII";



    public DbConnector() throws SQLException, ClassNotFoundException {
         //log in to database
        connection = DbLogin();

        if(!tableExists(connection)){ //check if table exists
            CreateTable(connection); //if not,create it
        }
    }

    private static Connection  DbLogin() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String username="your username here";
        String password="your password here"; //predefined credentials
        Connection con= DriverManager.getConnection("jdbc:oracle:thin:@your url here",username,password); //initiate connection
        System.out.println("Connected to DB");
        return con; //return the connection object
    }

    private static boolean tableExists(Connection connection) throws SQLException {
        boolean exists=false;
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet res = meta.getTables(null, null, tablename,
                new String[] {"TABLE"}); //acquire database metadata
        while (res.next()) {
            String name=res.getString("TABLE_NAME"); //check for table name existence in all tables
            exists=name.contains(tablename);
        }
        return exists; //return true if table exists
    }

    private static void CreateTable(Connection con) throws SQLException {
        String sql = "CREATE TABLE "+tablename+" (" + //sql commands for table creation
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
        stmt.executeUpdate(sql); //execute query
    }

   public static void SaveToDB(HashMap<String, City> cityMap) throws SQLException {
       Iterator<Map.Entry<String, City>> it = cityMap.entrySet().iterator(); //iterator for the city hashmap
       while(it.hasNext())
       {
           Map.Entry<String, City> entry = it.next(); //move to next element
            Statement stmt = connection.createStatement();
            ResultSet result;
            result = stmt.executeQuery("select count(*) AS FOUNDAT from CITIES_OOPII where NAME="+"'"+entry.getValue().getName()+"'"); //sql query to see if city already in database
            result.next();
            if(result.getInt("FOUNDAT")==0) { //only executes if city not in db
                String insStr = "INSERT INTO CITIES_OOPII VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement insertionStmt = connection.prepareStatement(insStr);
                insertionStmt.setString(1,entry.getValue().getName());
                insertionStmt.setFloat(2,entry.getValue().getGeodesic_vector()[1]);
                insertionStmt.setFloat(3,entry.getValue().getGeodesic_vector()[0]);
                for (int j = 0; j <10; j++) {
                    insertionStmt.setInt(j+4,entry.getValue().getTerms_vector()[j]); //term insertion
                }
                insertionStmt.executeUpdate(); //finish insert
            }
        }
   }

   public static HashMap<String,City> LoadFromDB() throws SQLException, InvalidInputException, IOException {
       HashMap<String,City> temp_map= new HashMap<>(); //temp hashmap
       Statement stmt=connection.createStatement();
       String sql="SELECT * FROM CITIES_OOPII"; //get row
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
           temp_map.put(Name,new City(Name,temp_coord,temp_attr)); //put loaded city in map
           System.out.println("Loaded:" + Name + " from DB");
       }
       return temp_map;
   }

}
