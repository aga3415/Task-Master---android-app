package MySQLConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Agnieszka on 2015-06-18.
 */
public class EstablishDBConnection {

    //private static final String url = "jdbc:mysql://mysql.cba.pl:3306/taskmaster_cba_pl"; //na hostingu cba
    private static final String url = "jdbc:mysql://192.168.0.107:3306/taskmaster"; //lokalna baza
    //private static final String user = "adminTaskMaster";
    private static final String user = "admin";
    private static final String pass = "haslo";
    private static Connection con;

    public static boolean connectToDB(){
        try {
            if (con != null) con.close();
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Database conection success");


        } catch (Exception e) {
            System.out.println("Connection fail");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Connection getConnection(){
        if (con != null ) {
            try {
                if (con.isClosed() ) {
                    connectToDB();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return con;
        }
        else{
            connectToDB();
        }
        return con;
    }

    public static void closeConnection(){
        if (con != null) try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
