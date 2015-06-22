package MySQLConnection;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DataModel.User;
import Database.DbAdapter;
import Database.Table;
import Database.UsersTable;
import PreparingData.CurrentCreatingUser;

/**
 * Created by Agnieszka on 2015-06-18.
 */
public class SignUp {

    static User newUser;
    static Connection con;

    //private static final String url = "jdbc:mysql://mysql.cba.pl:3306/taskmaster_cba_pl"; //na hostingu cba
    private static final String url = "jdbc:mysql://192.168.0.107:3306/taskmaster"; //lokalna baza
    //private static final String user = "adminTaskMaster";
    private static final String user = "admin";
    private static final String pass = "haslo";
    private ResultSet resultSet = null;

    public static boolean connectionEstablish = true;
    public static boolean emailCorrect = true;
    public static boolean passwordCorrect = true;



    public static boolean ifCanCreateNewUser() {

        connectionEstablish = true;
        emailCorrect = true;
        passwordCorrect = true;

        newUser = CurrentCreatingUser.getInstance();

        if (newUser != null) {
            con = EstablishDBConnection.getConnection();
            if (con == null) {
                connectionEstablish = false;
                System.out.println("Nie udalo sie polaczyc z baza");
                return false;
            }

            try {
                Statement st = con.createStatement();

                String checkEmail = "select EMAIL FROM USERS WHERE EMAIL='" +
                        newUser.getEmail() + "'";
                ResultSet resultEmail = st.executeQuery(checkEmail);
                if (resultEmail.next()) {
                    emailCorrect = false;
                    con.close();
                    System.out.println("Znaleziono uzytkownika o takim adresie email");
                    return false;
                }

                st = con.createStatement();

                UsersTable usersTable = new UsersTable();

                String columnNames = "(LOGIN, PASSWORD, EMAIL)";

                String stString = "INSERT INTO " +
                        usersTable.getNameOfTable() +
                        columnNames +
                        " VALUES ('" +
                        newUser.getLogin() + "', '" +
                        newUser.getPassword() + "', '" +
                        newUser.getEmail() + "')";

                //System.out.println("Statement: " + stString);
               boolean createNewUser =  st.execute(stString);
                st.close();
                con.close();

                System.out.println("Efekt zapytania: " + createNewUser);
                return true;



            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

        }

        return false;
    }
}
