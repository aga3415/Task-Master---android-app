package MySQLConnection;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import PreparingData.CurrentCreatingUser;

/**
 * Created by Agnieszka on 2015-06-21.
 */
public class DeleteAllMyCompletedSendedTasks extends AsyncTask{

    @Override
    protected Object doInBackground(Object[] params) {
        Connection conn;
        boolean connectionSuccess;

        conn = EstablishDBConnection.getConnection();
        connectionSuccess = true;
        if (conn == null ){
            connectionSuccess = false;
            return null;
        }
        try {
            Statement statement = conn.createStatement();
            String query = "DELETE FROM TASKS WHERE ID_PRINCIPAL ='" +
                    CurrentCreatingUser.getInstance().getEmail() +"' AND DATE_EXEC <> ''";
            statement.executeUpdate(query);

        } catch (SQLException e) {
            connectionSuccess = false;
            e.printStackTrace();
        }
        return null;
    }
}
