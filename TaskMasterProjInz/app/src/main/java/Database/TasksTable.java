package Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import DataModel.MyDate;
import DataModel.Task;

/**
 * Created by Agnieszka on 2015-05-02.
 */
public class TasksTable extends Table{

    public Column   id, id_parent, description, priority, date_insert, date_update, date_plan_exec,
                    date_exec, date_archive, cycle_time, id_group, id_executor, id_principal, points;

    Calendar calendar;

    //zawiera wszystkie nazwy kolumn w tabeli
    public TasksTable(SQLiteDatabase db) {
        super(db);
    }

    public void setAllInfoAboutTable(){
        this.nameOfTable = "TASKS";
        id = new Column ("ID", "INTEGER PRIMARY KEY AUTOINCREMENT", 0);
        id_parent = new Column("ID_PARENT", "INTEGER REFERENCES TASKS(ID) ON DELETE CASCADE", 1);
        description = new Column("DESCRIPTION", "TEXT NOT NULL", 2);
        priority = new Column("PRIORITY", "INTEGER", 3);
        date_insert = new Column("DATE_INSERT", "DATE DEFAULT 'NOW'", 4);
        date_update = new Column ("DATE_UPDATE", "DATE", 5);
        date_plan_exec = new Column("DATE_PLAN_EXEC", "VARCHAR(160)",6);
        date_exec = new Column("DATE_EXEC", "DATE",7);
        date_archive = new Column("DATE_ARCHIVE", "DATE",8);
        cycle_time = new Column("CYCLE_TIME", "INTEGER", 9);
        id_group = new Column("ID_GROUP", "INTEGER REFERENCES GROUPS(ID) ON DELETE CASCADE", 10);
        id_executor = new Column("ID_EXECUTOR", "INTEGER REFERENCES USERS(ID) ON DELETE CASCADE", 11);
        id_principal = new Column("ID_PRINCIPAL", "INTEGER REFERENCES USERS(ID) ON DELETE CASCADE", 12);
        points = new Column("POINTS", "INTEGER", 13);

        listOfColumns.add(id);
        listOfColumns.add(id_parent);
        listOfColumns.add(description);
        listOfColumns.add(priority);
        listOfColumns.add(date_insert);
        listOfColumns.add(date_update);
        listOfColumns.add(date_plan_exec);
        listOfColumns.add(date_exec);
        listOfColumns.add(date_archive);
        listOfColumns.add(cycle_time);
        listOfColumns.add(id_group);
        listOfColumns.add(id_executor);
        listOfColumns.add(id_principal);
        listOfColumns.add(points);

    }

    public long insert(Task task, SQLiteDatabase db){
        ContentValues newTask = new ContentValues();
        newTask.put(listOfColumns.get(1).name, task.getId_parent());
        newTask.put(listOfColumns.get(2).name, task.getDescription());
        newTask.put(listOfColumns.get(3).name, task.getPriority());
        newTask.put(listOfColumns.get(4).name, task.getDate_insert().getDateString());
        newTask.put(listOfColumns.get(5).name, task.getDate_update().getDateString());
        newTask.put(listOfColumns.get(6).name, task.getDate_plan_exec().getDateString());
        newTask.put(listOfColumns.get(7).name, task.getDate_exec().getDateString());
        newTask.put(listOfColumns.get(8).name, task.getDate_archive().getDateString());
        newTask.put(listOfColumns.get(9).name, task.getCycle_time());
        newTask.put(listOfColumns.get(10).name, task.getId_group());
        newTask.put(listOfColumns.get(11).name, task.getId_executor());
        newTask.put(listOfColumns.get(12).name, task.getId_principal());
        newTask.put(listOfColumns.get(13).name, task.getPoints());

        return db.insert(nameOfTable, null, newTask);
        //Log.d(DEBUG_TAG, "Wstawianie nowego taska do bazy");

    }

    public boolean delete(long id, SQLiteDatabase db){
        String where = listOfColumns.get(0).name + "=" + id;
        return db.delete(nameOfTable, where, null) > 0;
    }

    public boolean deleteCompletedTasks(SQLiteDatabase db){
        String where = listOfColumns.get(7).name + "!=" + "''";
        return db.delete(nameOfTable, where, null) > 0;
    }

    public boolean deleteAllTasks(SQLiteDatabase db){
        //String where = listOfColumns.get(7).name + "!=" + "''";
        return db.delete(nameOfTable, null, null) > 0;
    }

    public boolean update(Task task, SQLiteDatabase db) {
        long id = task.getId();

        ContentValues newTask = new ContentValues();
        newTask.put(listOfColumns.get(1).name, task.getId_parent());
        newTask.put(listOfColumns.get(2).name, task.getDescription());
        newTask.put(listOfColumns.get(3).name, task.getPriority());
        newTask.put(listOfColumns.get(4).name, task.getDate_insert().getDateString());
        newTask.put(listOfColumns.get(5).name, task.getDate_update().getDateString());
        newTask.put(listOfColumns.get(6).name, task.getDate_plan_exec().getDateString());
        newTask.put(listOfColumns.get(7).name, task.getDate_exec().getDateString());
        newTask.put(listOfColumns.get(8).name, task.getDate_archive().getDateString());
        newTask.put(listOfColumns.get(9).name, task.getCycle_time());
        newTask.put(listOfColumns.get(10).name, task.getId_group());
        newTask.put(listOfColumns.get(11).name, task.getId_executor());
        newTask.put(listOfColumns.get(12).name, task.getId_principal());
        newTask.put(listOfColumns.get(13).name, task.getPoints());

        String where = listOfColumns.get(0).name + "=" + id;
        return db.update(nameOfTable, newTask, where, null) > 0;
        //metoda db.update zwraca liczbe zmienionych pól
    }

    public Cursor getAllTasks(SQLiteDatabase db) {
        List<String> colNames = new ArrayList<String>();
        String where = listOfColumns.get(10).name + " =0 AND " + listOfColumns.get(11).name + " IS NULL";
        for (Column c : listOfColumns){
            colNames.add(c.name);
        }
        String[] columns = colNames.toArray(new String [colNames.size()]);
        return db.query(nameOfTable, columns, where, null, null, null, "date_plan_exec");
        //public Cursor query (String table, String[] columns, String selection,
        // String[] selectionArgs, String groupBy, String having, String orderBy)
    }

    public Cursor getGroupedTasks(SQLiteDatabase db) {
        List<String> colNames = new ArrayList<String>();
        String where = listOfColumns.get(10).name + " <>0";
        for (Column c : listOfColumns){
            colNames.add(c.name);
        }
        String[] columns = colNames.toArray(new String [colNames.size()]);
        return db.query(nameOfTable, columns, where, null, null, null, "date_plan_exec");
        //public Cursor query (String table, String[] columns, String selection,
        // String[] selectionArgs, String groupBy, String having, String orderBy)
    }

    /*public Task getTaskById(long id) {

        String where = "id_task =" + id;
        Cursor cursor = db.query("tasks", columns, where, null, null, null, null);
        Task task = null;
        if(cursor != null && cursor.moveToFirst()) {
            String description = cursor.getString(1); //nr kolumny description
            boolean completed = cursor.getInt(2) > 0 ? true : false;
            //getInt(2) - 2 to nr kolumny completed
            // 0 kolumna - id_task
            // 1 kolumna - description
            // 2 kolumna  - completed
            task = new Task(id, description, completed);
        }
        return task;
    }*/

    public Cursor getTasksForGivenDate(SQLiteDatabase db, MyDate date) {
        List<String> colNames = new ArrayList<String>();

        String where = date_plan_exec.name + " = '" + date.getDateString()+" '";//"' AND " +
                //listOfColumns.get(10).name + " =0 AND " + listOfColumns.get(11).name + " IS NULL";

        for (Column c : listOfColumns){
            colNames.add(c.name);
        }

        String[] columns = colNames.toArray(new String [colNames.size()]);
        return db.query(nameOfTable, columns, where, null, null, null, null);
        //public Cursor query (String table, String[] columns, String selection,
        // String[] selectionArgs, String groupBy, String having, String orderBy)
    }

    public Cursor getSendedTasks(SQLiteDatabase db) {
        List<String> colNames = new ArrayList<String>();
        String where = listOfColumns.get(11).name + " IS NOT NULL";
        for (Column c : listOfColumns){
            colNames.add(c.name);
        }
        String[] columns = colNames.toArray(new String [colNames.size()]);
        return db.query(nameOfTable, columns, where, null, null, null, "date_plan_exec");
        //public Cursor query (String table, String[] columns, String selection,
        // String[] selectionArgs, String groupBy, String having, String orderBy)
    }


}
