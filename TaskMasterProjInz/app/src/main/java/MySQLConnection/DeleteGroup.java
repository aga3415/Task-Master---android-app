package MySQLConnection;

import android.content.Context;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import DataModel.Group;

/**
 * Created by Agnieszka on 2015-06-22.
 */
public class DeleteGroup extends AsyncTask {

    Connection conn;
    boolean connectionSuccess;
    Context context;

    public DeleteGroup(Context context){
        this.context = context;
    }
    @Override
    protected Object doInBackground(Object[] params) {//parametrem jest cala grupa
        System.out.println("Zaczynam wykonywać usuwanie grupy");
        conn = EstablishDBConnection.getConnection();
        connectionSuccess = true;
        if (conn == null ){
            connectionSuccess = false;
            return null;
        }
        try {
            Statement statement = conn.createStatement();
            String query = "DELETE FROM GROUPS WHERE NAME= '" + ((Group) params[0]).getName() + "'";
            statement.executeUpdate(query);

        } catch (SQLException e) {
            connectionSuccess = false;
            e.printStackTrace();
        }
        return null;
    }
}
