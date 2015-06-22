package MySQLConnection;

import android.content.Context;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import PreparingData.CurrentCreatingUser;

/**
 * Created by Agnieszka on 2015-06-21.
 */
public class DeleteAllMyGroups extends AsyncTask {

    public boolean connectionSuccess = true;
    private Connection conn;
    Context context;

    public DeleteAllMyGroups(Context context){
        this.context = context;
    }
    @Override
    protected Object doInBackground(Object[] params) {

        conn = EstablishDBConnection.getConnection();
        connectionSuccess = true;
        if (conn == null ){
            connectionSuccess = false;
            return null;
        }
        try{
            Statement statement = conn.createStatement();
            String findGroupsId = "SELECT DISTINCT ID_GROUP FROM MEMBERSOFGROUPS WHERE ID_USER = '"
                    + CurrentCreatingUser.getInstance().getEmail() + "'";
            System.out.println(findGroupsId);
            ResultSet resultSetGroupsId = statement.executeQuery(findGroupsId);


            while(resultSetGroupsId.next()){
                String deleteGroup = "DELETE FROM GROUPS WHERE ID_GROUP = " +
                        resultSetGroupsId.getString(1);
                System.out.println(deleteGroup);
                Statement deleteStatement = conn.createStatement();

                deleteStatement.executeUpdate(deleteGroup);
                deleteStatement.close();
            }
            resultSetGroupsId.close();
            statement.close();
            conn.close();

        }catch(SQLException  e){
            e.printStackTrace();
            connectionSuccess = false;
        }

        return null;
    }
}
