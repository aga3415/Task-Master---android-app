package MySQLConnection;

import android.content.Context;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DataModel.Task;
import Database.DbAdapter;
import Database.Table;
import Database.TasksTable;
import PreparingData.CurrentCreatingTask;

/**
 * Created by Agnieszka on 2015-06-21.
 */
public class SaveCreatedTask extends AsyncTask {

    Connection conn;
    boolean connectionSuccess;
    Context context;

    public SaveCreatedTask(Context context){
        this.context = context;
    }
    @Override
    protected Object doInBackground(Object[] params) {

        Task task = (Task) params[0];
        System.out.println("Sklonowany otrzymany task: " + task.getDescription());

        conn = EstablishDBConnection.getConnection();
        connectionSuccess = true;
        if (conn == null ){
            connectionSuccess = false;
            return null;
        }

        try{
            Statement insertTaskSt = conn.createStatement();
            TasksTable tasksTable = new TasksTable();

            String insertTaskText = "INSERT INTO " + tasksTable.getNameOfTable() + " (";

            String listOfColumns = tasksTable.getListOfColumns().get(2).name + //description
                    ", " + tasksTable.getListOfColumns().get(3).name + //priority
                    ", " + tasksTable.getListOfColumns().get(4).name + //date insert
                    ", " + tasksTable.getListOfColumns().get(5).name + //date update
                    ", " + tasksTable.getListOfColumns().get(6).name + //plan exec
                    ", " + tasksTable.getListOfColumns().get(7).name + //exec
                    ", " + tasksTable.getListOfColumns().get(10).name + //group
                    ", " + tasksTable.getListOfColumns().get(11).name + //executor
                    ", " + tasksTable.getListOfColumns().get(12).name; //principial

            insertTaskText += listOfColumns + ") VALUES (";

            String values = "'" + task.getDescription() + "', " +
                    task.getPriority() + ", " +
                    "'" + task.getDate_insert().getDateString() + "', " +
                    "'" + task.getDate_update().getDateString() + "', " +
                    "'" + task.getDate_plan_exec().getDateString() + "', " +
                    "'" + task.getDate_exec().getDateString() + "', " +
                    task.getId_group() + ", " +
                    "'" + task.getId_executor() + "', " +
                    "'" + task.getId_principal() + "'";

            insertTaskText += values + ")";

            System.out.println(insertTaskText);

            insertTaskSt.executeUpdate(insertTaskText);

            insertTaskSt.close();

            Statement getId = conn.createStatement();
            String getIdText = "SELECT ID_TASK FROM TASKS WHERE " +
                    tasksTable.getListOfColumns().get(2).name + " = " + "'" + task.getDescription() + "' AND " +
                    tasksTable.getListOfColumns().get(3).name +   "= " + task.getPriority() + " AND " +
                    tasksTable.getListOfColumns().get(4).name + " = '" + task.getDate_insert().getDateString() + "' AND " +
                    tasksTable.getListOfColumns().get(11).name + " ='" + task.getId_executor() + "'";

            ResultSet id = getId.executeQuery(getIdText);
            if(id.next()){
                long idNumber = id.getLong(1);
                DbAdapter db = DbAdapter.getInstance(context);
                db.updateTaskId(idNumber, task);

            }

            id.close();
            getId.close();
            conn.close();


        }catch(SQLException e){
            e.printStackTrace();
            connectionSuccess = false;
        }
        return null;
    }
}
