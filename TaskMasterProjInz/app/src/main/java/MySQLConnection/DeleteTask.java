package MySQLConnection;

import android.content.Context;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Agnieszka on 2015-06-21.
 */
public class DeleteTask extends AsyncTask {

    Connection conn;
    boolean connectionSuccess;
    Context context;

    public DeleteTask(Context context){
        this.context = context;
    }
    @Override
    protected Object doInBackground(Object[] params) {//parametrem jest id_taska do usuniecia
        System.out.println("Zaczynam wykonywać usuwanie");
        conn = EstablishDBConnection.getConnection();
        connectionSuccess = true;
        if (conn == null ){
            connectionSuccess = false;
            return null;
        }
        try {
            Statement statement = conn.createStatement();
            String query = "DELETE FROM TASKS WHERE ID_TASK = " + (int) params[0];
            statement.executeUpdate(query);

        } catch (SQLException e) {
            connectionSuccess = false;
            e.printStackTrace();
        }
        return null;
    }
}
