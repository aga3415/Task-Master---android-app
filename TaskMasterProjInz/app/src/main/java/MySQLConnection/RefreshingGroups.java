package MySQLConnection;

import android.content.Context;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DataModel.Group;
import DataModel.MemberOfGroup;
import DataModel.MyDate;
import DataModel.Task;
import Database.DbAdapter;
import Database.MembersOfGroupTable;
import PreparingData.CurrentCreatingUser;

/**
 * Created by Agnieszka on 2015-06-21.
 */
public class RefreshingGroups {

    public boolean connectionSuccess;
    Connection conn;
    DbAdapter db;
    Context context;

    public RefreshingGroups(Context context){
        this.context = context;
    }


    public Object doInBackground() {

        conn = EstablishDBConnection.getConnection();
        connectionSuccess = true;
        if (conn == null) {
            connectionSuccess = false;
            return null;
        }
        try {
            Statement statement = conn.createStatement();
            String findGroupsId = "SELECT DISTINCT * FROM MEMBERSOFGROUPS WHERE ID_USER = '"
                    + CurrentCreatingUser.getInstance().getEmail() + "'";
            System.out.println(findGroupsId);
            ResultSet resultSetGroupsId = statement.executeQuery(findGroupsId);

            db = DbAdapter.getInstance(context);
            if (db == null) {System.out.println("db is null");}
            else{
                db.deleteAllGroups();
                db.deleteAllMembers();
            }

            db.deleteAllTasks(); //usuwanie wszystkich moich taskow!!!
            db.deleteAllGroupedTasks();
            db.deleteAllSendedTasks();
            db.deleteAllGroups();
            db.deleteAllMembers();

            while(resultSetGroupsId.next()){

                Statement findGroup = conn.createStatement();
                String findGroupText = "SELECT * FROM GROUPS WHERE ID_GROUP = " + resultSetGroupsId.getLong(1);
                ResultSet groupResult = findGroup.executeQuery(findGroupText);

                while (groupResult.next()){
                    Group newGroup = new Group(groupResult.getLong(1), groupResult.getString(2));
                    db.insertGroup(newGroup);
                    System.out.println("Dodano nowa grupe");

                    Statement findGroupedTasks = conn.createStatement();
                    String findGroupedTaskText = "SELECT * FROM TASKS WHERE ID_GROUP = " + groupResult.getLong(1);
                    ResultSet foundGroupedTask = findGroupedTasks.executeQuery(findGroupedTaskText);
                    while(foundGroupedTask.next()){

                        Task task = new Task(
                                (int) foundGroupedTask.getLong(1), //id
                                foundGroupedTask.getInt(2), //id parent
                                foundGroupedTask.getString(3),//description
                                foundGroupedTask.getInt(4), //priority
                                new MyDate(foundGroupedTask.getString(5)), //date_insert
                                new MyDate(foundGroupedTask.getString(6)), //date_update
                                new MyDate(foundGroupedTask.getString(7)), //date plan exec
                                new MyDate(foundGroupedTask.getString(8)), //date exec
                                new MyDate(foundGroupedTask.getString(9)), //date archive
                                foundGroupedTask.getInt(10), //cycle
                                foundGroupedTask.getLong(11), //group
                                foundGroupedTask.getString(12), //executor
                                foundGroupedTask.getString(13), //principal
                                foundGroupedTask.getInt(14) //points
                        );
                        db.insert(task);

                    }
                    foundGroupedTask.close();
                    findGroupedTasks.close();


                }


                String findMembers = "SELECT * FROM MEMBERSOFGROUPS WHERE ID_GROUP = " +
                        resultSetGroupsId.getLong(1);

                System.out.println(findMembers);
                Statement findMembersStatement = conn.createStatement();

                ResultSet members = findMembersStatement.executeQuery(findMembers);

                while (members.next()){

                    MemberOfGroup newMember = new MemberOfGroup(members.getString(2), members.getLong(1), members.getString(3));
                    db.insertMemberOfGroup(newMember);
                }


                members.close();
                findMembersStatement.close();
            }
            resultSetGroupsId.close();
            statement.close();

            Statement myOwnTask = conn.createStatement();
            String myOwnTaskText = "SELECT * FROM TASKS WHERE ID_EXECUTOR = '" + CurrentCreatingUser.getInstance().getEmail() +"'";
            ResultSet myOwnTaskResult = myOwnTask.executeQuery(myOwnTaskText);

            while(myOwnTaskResult.next()) {

                Task task = new Task(
                        (int) myOwnTaskResult.getLong(1), //id
                        myOwnTaskResult.getInt(2), //id parent
                        myOwnTaskResult.getString(3),//description
                        myOwnTaskResult.getInt(4), //priority
                        new MyDate(myOwnTaskResult.getString(5)), //date_insert
                        new MyDate(myOwnTaskResult.getString(6)), //date_update
                        new MyDate(myOwnTaskResult.getString(7)), //date plan exec
                        new MyDate(myOwnTaskResult.getString(8)), //date exec
                        new MyDate(myOwnTaskResult.getString(9)), //date archive
                        myOwnTaskResult.getInt(10), //cycle
                        myOwnTaskResult.getLong(11), //group
                        myOwnTaskResult.getString(12), //executor
                        myOwnTaskResult.getString(13), //principal
                        myOwnTaskResult.getInt(14) //points
                );
                db.insert(task);

            }
            myOwnTask.close();
            myOwnTaskResult.close();

            Statement mySendedTask = conn.createStatement();
            String mySendedTaskText = "SELECT * FROM TASKS WHERE ID_PRINCIPAL = '" + CurrentCreatingUser.getInstance().getEmail() +"'";
            ResultSet mySendedTaskResult = mySendedTask.executeQuery(mySendedTaskText);

            while(mySendedTaskResult.next()) {

                Task task = new Task(
                        (int) mySendedTaskResult.getLong(1), //id
                        mySendedTaskResult.getInt(2), //id parent
                        mySendedTaskResult.getString(3),//description
                        mySendedTaskResult.getInt(4), //priority
                        new MyDate(mySendedTaskResult.getString(5)), //date_insert
                        new MyDate(mySendedTaskResult.getString(6)), //date_update
                        new MyDate(mySendedTaskResult.getString(7)), //date plan exec
                        new MyDate(mySendedTaskResult.getString(8)), //date exec
                        new MyDate(mySendedTaskResult.getString(9)), //date archive
                        mySendedTaskResult.getInt(10), //cycle
                        mySendedTaskResult.getLong(11), //group
                        mySendedTaskResult.getString(12), //executor
                        mySendedTaskResult.getString(13), //principal
                        mySendedTaskResult.getInt(14) //points
                );
                db.insert(task);

            }
            mySendedTask.close();
            mySendedTaskResult.close();

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            connectionSuccess = false;
        }
        return null;
    }
}
