package MySQLConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DataModel.User;
import Database.UsersTable;
import PreparingData.CurrentCreatingUser;

/**
 * Created by Agnieszka on 2015-06-18.
 */
public class SignIn {

    static User newUser;
    static Connection con;

    public static boolean connectionEstablish = true;
    public static boolean emailCorrect = true;
    public static boolean passwordCorrect = true;



    public static boolean ifCanSignIn() {

        newUser = CurrentCreatingUser.getInstance();
        connectionEstablish = true;
        emailCorrect = true;
        passwordCorrect = true;

        if (newUser != null) {
            con = EstablishDBConnection.getConnection();
            if (con == null) {
                connectionEstablish = false;
                System.out.println("Nie udalo sie polaczyc z baza");
                return false;
            }

            try {
                Statement st = con.createStatement();

                String checkEmail = "select PASSWORD FROM USERS WHERE EMAIL='" +
                        newUser.getEmail() + "'";

                ResultSet resultPassword = st.executeQuery(checkEmail);
                System.out.println("Wykonujemy zapytanie");

                if (resultPassword.next()) {
                    String password = resultPassword.getString("PASSWORD");
                    System.out.println("Znaleziono uzytkownika");
                    if (newUser.getPassword().equals(password)){
                        resultPassword.close();
                        st.close();
                        con.close();
                        return true;
                    }else if (password.length() > 2){
                        resultPassword.close();
                        st.close();
                        con.close();
                        passwordCorrect = false;
                        return false;
                    }else{
                        emailCorrect = false;
                        System.out.print("nie znaleziono uzytkownika");
                        st.close();
                        con.close();
                        return false;
                    }
                }else{
                    emailCorrect = false;
                    System.out.print("nie znaleziono uzytkownika");
                    st.close();
                    con.close();
                    return false;
                }

            } catch (SQLException e) {
                System.out.println("Przechwytujemy wyjatek");
                e.printStackTrace();
                return false;
            }

        }

        System.out.println("Nic sie nie wydarzylo");
        return false;
    }
}
