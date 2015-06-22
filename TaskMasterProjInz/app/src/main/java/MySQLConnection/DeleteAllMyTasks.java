package MySQLConnection;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import PreparingData.CurrentCreatingUser;

/**
 * Created by Agnieszka on 2015-06-21.
 */
public class DeleteAllMyTasks extends AsyncTask {

    Connection conn;
    boolean connectionSuccess;
    @Override
    protected Object doInBackground(Object[] params) {
        conn = EstablishDBConnection.getConnection();
        connectionSuccess = true;
        if (conn == null ){
            connectionSuccess = false;
            return null;
        }
        try {
            Statement statement = conn.createStatement();
            String query = "DELETE FROM TASKS WHERE ID_EXECUTOR ='" +
                    CurrentCreatingUser.getInstance().getEmail() + "'";
            statement.executeUpdate(query);

        } catch (SQLException e) {
            connectionSuccess = false;
            e.printStackTrace();
        }
        return null;
    }
}
