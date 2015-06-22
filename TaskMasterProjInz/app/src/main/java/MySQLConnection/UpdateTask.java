package MySQLConnection;

import android.content.Context;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import DataModel.MyDate;
import DataModel.Task;
import Database.TasksTable;

/**
 * Created by Agnieszka on 2015-06-21.
 */
public class UpdateTask extends AsyncTask {

    Connection conn;
    boolean connectionSuccess;
    Context context;

    public UpdateTask(Context context){
        this.context = context;
    }
    @Override
    protected Object doInBackground(Object[] params) {//parametrem jest task do updateowania
        conn = EstablishDBConnection.getConnection();
        connectionSuccess = true;
        if (conn == null ){
            connectionSuccess = false;
            return null;
        }
        try {
            Statement statement = conn.createStatement();
            String query = "UPDATE TASKS SET ";
            Task task = (Task) params[0];

            TasksTable tasksTable = new TasksTable();
            String listOfColumns = tasksTable.getListOfColumns().get(2).name +
                    "= '" + task.getDescription() + "'" + //description
                    ", " + tasksTable.getListOfColumns().get(3).name +
                    "= " + task.getPriority() + //priority
                    ", " + tasksTable.getListOfColumns().get(5).name +
                    "= '" + MyDate.getTodayDate().getDateString() + "'" +//date update
                    ", " + tasksTable.getListOfColumns().get(6).name +
                    "= '" + task.getDate_plan_exec().getDateString() + "'" +//plan exec
                    ", " + tasksTable.getListOfColumns().get(7).name +
                    "= '" + task.getDate_exec().getDateString() + "'" +//exec
                    ", " + tasksTable.getListOfColumns().get(10).name +
                    "= " + task.getId_group() +//group
                    ", " + tasksTable.getListOfColumns().get(11).name +
                    "= '" + task.getId_executor() + "'" +//executor
                    ", " + tasksTable.getListOfColumns().get(12).name +
                    "= '" + task.getId_principal() + "' "; //principial

            query += listOfColumns;
            query += "WHERE ID_TASK =" + task.getId();
            System.out.println(query);

            statement.executeUpdate(query);

            statement.close();
            conn.close();

        } catch (SQLException e) {
            connectionSuccess = false;
            e.printStackTrace();
        }
        return null;
    }
}
