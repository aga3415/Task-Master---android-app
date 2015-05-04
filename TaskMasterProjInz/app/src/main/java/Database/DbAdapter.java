package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import DataModel.MemberOfGroup;

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


        //DbAdapter jest singletonem, chemy mieć tylko jedna jego instancje,
        //stad prywatny konstruktor i statyczna metoda geInstance
    }

    public static DbAdapter getInstance(Context context){
        return /*(instance == null) ? (instance =*/ new DbAdapter(context)/*): instance*/;
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
        dbHelper.onUpgrade(db, DB_VERSION, DB_VERSION + 1);
    }
}
