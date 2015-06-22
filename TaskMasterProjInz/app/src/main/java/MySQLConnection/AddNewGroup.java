package MySQLConnection;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DataModel.Group;
import DataModel.MemberOfGroup;
import Database.DbAdapter;
import Database.GroupsTable;
import Database.MembersOfGroupTable;
import PreparingData.CurrentCreatingGroup;
import PreparingData.CurrentCreatingUser;
import al.taskmasterprojinz.R;

/**
 * Created by Agnieszka on 2015-06-20.
 */
public class AddNewGroup extends AsyncTask {

    public boolean connectionSuccess = true;
    private Connection conn;
    Context context;

    public AddNewGroup(Context context){
        this.context = context;
        connectionSuccess = true;
    }
    @Override
    protected Object doInBackground(Object[] params) {

        conn = EstablishDBConnection.getConnection();
        connectionSuccess = true;
        if (conn == null ){
            connectionSuccess = false;
            return null;
        }
        if (CurrentCreatingGroup.getInstance(context) != null){
            try {
                CurrentCreatingGroup newGroup = CurrentCreatingGroup.getInstance(context);
                Statement statement = conn.createStatement();
                GroupsTable groupsTable = new GroupsTable();
                String query = " INSERT INTO " + groupsTable.getNameOfTable() + " (NAME)" +
                        " VALUES ('" + newGroup.nameOfGroup + "')";
                int result = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {

                        long generetaedKey = generatedKeys.getLong(1);
                        /*DbAdapter db = DbAdapter.getInstance(context);
                        db.updateIdGroup(generetaedKey, newGroup.nameOfGroup);
                        */
                        statement.close();
                        statement = conn.createStatement();
                        String newQuery = "INSERT INTO " + new MembersOfGroupTable().getNameOfTable() +
                                "(ID_GROUP, ID_USER, NAME) VALUES ";


                        for (String usersName : CurrentCreatingGroup.users.keySet()){

                            String usersEmail = CurrentCreatingGroup.users.get(usersName);
                            String valuesQuery = "(" + generetaedKey + ", '" + usersEmail + "', '" + usersName + "')";
                            System.out.println(newQuery + valuesQuery);

                            statement.executeUpdate(newQuery + valuesQuery);


                        }
                        statement.close();
                        conn.close();
                        CurrentCreatingGroup.clearCreatingGroup();
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
                connectionSuccess = false;
            }
        }
        return null;
    }

    public void onPostExecute(){
        if (connectionSuccess == false){
            Toast.makeText(context, context.getResources().getString(R.string.error_connection_establish), Toast.LENGTH_SHORT).show();
        }
    }
}
