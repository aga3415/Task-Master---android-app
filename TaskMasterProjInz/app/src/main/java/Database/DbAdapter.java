package Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import DataModel.Group;
import DataModel.MemberOfGroup;
import DataModel.MyDate;
import DataModel.Task;
import DataModel.User;

/**
 * Created by Agnieszka on 2015-05-02.
 */
public class DbAdapter {

    private static DbAdapter instance = null;
    private static final String DEBUG_TAG = "SQLLite";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "database.db";

    private SQLiteDatabase db;
    private Context context;
    private DatabaseHelper dbHelper;

    private List<Table> tables;
    private UsersTable usersTable;
    private GroupsTable groupsTable;
    private MembersOfGroupTable membersOfGroupTable;
    private AimsTable aimsTable;
    private TasksTable tasksTable;

    private class DatabaseHelper extends SQLiteOpenHelper{

        DatabaseHelper(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            System.out.println("Baza danych jest tworzona...");

            //tutaj tworzone sa wszystkie tabele
            for (Table t: tables){
                db.execSQL(t.create);
                System.out.println("Stworzono tabele: " + t.nameOfTable);
            }


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            for (Table t: tables){
                db.execSQL(t.drop);
            }
            dbHelper.onCreate(db);


        }


    }


    private DbAdapter(Context context) {
        this.context = context;


        tables = new ArrayList<Table>();

        usersTable = new UsersTable(db);
        groupsTable = new GroupsTable(db);
        membersOfGroupTable = new MembersOfGroupTable(db);
        aimsTable = new AimsTable(db);
        tasksTable = new TasksTable(db);

        tables.add(usersTable);
        tables.add(groupsTable);
        tables.add(membersOfGroupTable);
        tables.add(aimsTable);
        tables.add(tasksTable);
        open();


        //DbAdapter jest singletonem, chemy mieć tylko jedna jego instancje,
        //stad prywatny konstruktor i statyczna metoda geInstance
    }

    public static DbAdapter getInstance(Context context){
        return (instance == null) ? (instance = new DbAdapter(context)): instance;
    }

    public DbAdapter open(){
        dbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            db = dbHelper.getReadableDatabase();
        }
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void refresh(){
        open();
        dbHelper.onUpgrade(db, DB_VERSION, DB_VERSION + 1);
    }

    public TasksTable getTasksTable(){
        return tasksTable;
    }

    public Cursor getAllTask(){
        return tasksTable.getAllTasks(db);
        /*List<String> colNames = new ArrayList<String>();
        for (Table.Column c : tasksTable.listOfColumns){
            colNames.add(c.name);
        }
        String[] columns = colNames.toArray(new String [colNames.size()]);
        return db.query(tasksTable.nameOfTable, columns, null, null, null, null, null);*/
    }
    public long insert(Task task){
        return tasksTable.insert(task, db);
    }
    /*public Cursor getTasksForToday(){
        return tasksTable.getTasksForToday(db);
    }*/ //nie dziala dobrze
    public boolean update(Task task){
        return tasksTable.update(task,db);
    }

    public void updateTaskId(long id, Task task){
        tasksTable.updateIdTask(db, id, task);
    }

    public Cursor getTasksForGivenDate(MyDate date){
        return tasksTable.getTasksForGivenDate(db, date);
    }

    public boolean deleteCompletedTasks(){
        return tasksTable.deleteCompletedTasks(db);
    }
    public boolean deleteAllGroupedTasks(){ return tasksTable.deleteAllGroupedTasks(db);}
    public boolean deleteAllSendedTasks(){return tasksTable.deleteAllSendedTasks(db);}
    public boolean deleteAllCompletedSendedTasks(){return tasksTable.deleteAllCompletedSendedTasks(db);}
    public boolean deleteAllCompletedGroupedTasks(){return tasksTable.deleteAllCompletedGroupedTasks(db);}

    public boolean deleteAllTasks(){
        return tasksTable.deleteAllTasks(db);
    }
    public boolean deleteTask(long id){
        return tasksTable.delete(id,db);
    }

    public boolean deleteMemberOfGroup(MemberOfGroup member){
        return membersOfGroupTable.delete(db, member);
    }
    public boolean deleteAllMembers(){
        return membersOfGroupTable.deleteAll(db);
    }

    public Cursor getAllMembers(){
        return membersOfGroupTable.getAllMembers(db);
    }
    public Cursor getAllMembersByUserId(String id) {return membersOfGroupTable.getMemberByUserId(db, id);}
    public Cursor getAllMembersByGroupId(Long id) {return membersOfGroupTable.getMembersByGroupId(db, id);}


    public long insertGroup(String name){
        return groupsTable.insert(db, name);
    }
    public void insertGroup(Group group) { groupsTable.insert(db, group);}

    public Cursor getGroupById(long id) {
        return groupsTable.getGroupById(db, id);
    }

    public void deleteGroup(Group group){
        groupsTable.delete(db, group);
    }
    public void deleteAllGroups(){groupsTable.deleteAllGroups(db);}
    public void updateIdGroup(long id, String name){groupsTable.updateIdGroup(db, id, name);}

    public void insertUser(User user){
        usersTable.insert(db,user);
    }
    public void deleteUser(User user){
        usersTable.delete(db, user);
    }

    public void insertMemberOfGroup(MemberOfGroup member){
        membersOfGroupTable.insert(db, member);
    }

    public Cursor getGroupByName(String name){
        return groupsTable.getGroupByName(db, name);
    }

    public boolean ifExistGroupWithName(String name){
        return groupsTable.ifExistGroupWithName(db, name);
    }

    public Cursor getMemberById(String id){
        return membersOfGroupTable.getMemberByUserId(db, id);
    }

    public Cursor getGroupedTasks(){
        return tasksTable.getGroupedTasks(db);
    }

    public Cursor getSendedTasks(){
        return tasksTable.getSendedTasks(db);
    }

    /*public long getGroupIdByName(String name){
        return groupsTable.selectGroupIdByName(db, name);
    }*/




}
