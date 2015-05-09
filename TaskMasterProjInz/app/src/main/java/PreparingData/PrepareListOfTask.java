package PreparingData;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import DataModel.MyDate;
import DataModel.Task;
import Database.DbAdapter;
import Database.TasksTable;
import al.taskmasterprojinz.R;

/**
 * Created by Agnieszka on 2015-05-05.
 */
public class PrepareListOfTask {

    Context context;
    Resources res;
    DbAdapter db;
    Cursor todoCursor;
    static PrepareListOfTask instance;
    HashMap<String, List<Task>> todayTomorrowFutureList;
    String [] headers;
    List<Task> tasksForToday;
    List<Task> taskForTomorrow;
    List<Task> restOfTask;



    private PrepareListOfTask(DbAdapter db, Context context){
        this.context = context;
        this.db = db;
        instance = this;

        res = context.getResources();
        headers = res.getStringArray(R.array.task_headers);

        db.open();
    }

    public static PrepareListOfTask getInstance(DbAdapter db, Context context){
        return (instance == null) ? new PrepareListOfTask(db,context) : instance;
    }

    public HashMap<String, List<Task>> todayTommorowInFutureTaskLists(){

        todayTomorrowFutureList = new HashMap<>();
        todoCursor = /*db.getTasksForToday();//*/db.getAllTask();

        tasksForToday = new ArrayList<Task>();
        taskForTomorrow = new ArrayList<Task>();
        restOfTask = new ArrayList<Task>();

        MyDate today = MyDate.getTodayDate();
        MyDate tomorrow = MyDate.getTomorrowDate();

        if(todoCursor != null && todoCursor.moveToFirst()) {
            do {
                int id = todoCursor.getInt(0);
                int id_parent = todoCursor.getInt(1);
                String description = todoCursor.getString(2);
                int priority = todoCursor.getInt(3);
                MyDate date_insert = new MyDate(todoCursor.getString(4));
                MyDate date_update = new MyDate(todoCursor.getString(5));
                MyDate date_plan_exec = new MyDate(todoCursor.getString(6));
                MyDate date_exec = new MyDate(todoCursor.getString(7));
                MyDate date_archive = new MyDate(todoCursor.getString(8));
                int cycle_time = todoCursor.getInt(9);
                int id_group = todoCursor.getInt(10);
                int id_executor = todoCursor.getInt(11);
                int id_principal  = todoCursor.getInt(12);
                int points = todoCursor.getInt(13);

                Task task = new Task(id, id_parent, description, priority, date_insert, date_update, date_plan_exec,
                        date_exec, date_archive, cycle_time, id_group, id_executor, id_principal, points);
                if (MyDate.isEqual(date_plan_exec, today) || MyDate.aEarlierThanB(date_plan_exec,today)){
                    tasksForToday.add(task);
                } else if (MyDate.isEqual(date_plan_exec, tomorrow)){
                    taskForTomorrow.add(task);
                }else{
                   restOfTask.add(task);
                }

            } while(todoCursor.moveToNext());
        }

        todayTomorrowFutureList.put(headers[0], tasksForToday);
        todayTomorrowFutureList.put(headers[1], taskForTomorrow);
        todayTomorrowFutureList.put(headers[2], restOfTask);

        return todayTomorrowFutureList;
    }

    public boolean isEmptyList(){
        if (tasksForToday.isEmpty() && taskForTomorrow.isEmpty() && restOfTask.isEmpty()) return true;
        return false;
    }
}
