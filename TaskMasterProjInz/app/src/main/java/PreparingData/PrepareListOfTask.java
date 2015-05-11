package PreparingData;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DataModel.MyDate;
import DataModel.Task;
import Database.DbAdapter;
import PreparingViewsAdapter.ExpandableTaskListAdapter;
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
    HashMap<String, List<Task>> listForGivenDate;
    String [] standardHeaders;
    String [] oneDateHeader;
    List<Task> tasksForGivenDate;
    List<Task> tasksForToday;
    List<Task> taskForTomorrow;
    List<Task> restOfTask;



    private PrepareListOfTask(Context context){
        this.context = context;
        this.db = DbAdapter.getInstance(context);
        instance = this;

        res = context.getResources();
        standardHeaders = res.getStringArray(R.array.task_headers);

        tasksForToday = new ArrayList<Task>();
        taskForTomorrow = new ArrayList<Task>();
        restOfTask = new ArrayList<Task>();

        db.open();
    }

    public static PrepareListOfTask getInstance(Context context){
        return (instance == null) ? new PrepareListOfTask(context) : instance;
    }

    public ExpandableTaskListAdapter todayTomorrowInFutureTaskLists(){

        todayTomorrowFutureList = new HashMap<>();
        todoCursor = db.getAllTask();

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

        todayTomorrowFutureList.put(standardHeaders[0], tasksForToday);
        todayTomorrowFutureList.put(standardHeaders[1], taskForTomorrow);
        todayTomorrowFutureList.put(standardHeaders[2], restOfTask);

        return new ExpandableTaskListAdapter(context, standardHeaders, todayTomorrowFutureList);
    }


    public ExpandableTaskListAdapter tasksForGivenDate(MyDate date){

        listForGivenDate = new HashMap<>();
        oneDateHeader = new String[1];

        //-----wpisanie daty do nagłówka------------------------------------------------------------
        if (MyDate.isEqual(date, MyDate.getTodayDate())){
            oneDateHeader[0] = res.getStringArray(R.array.task_headers)[0]; //wpisz "Dzisiaj"

            listForGivenDate.put(oneDateHeader[0],tasksForToday);
            return new ExpandableTaskListAdapter(context, oneDateHeader, listForGivenDate);
        }else if (MyDate.isEqual(date, MyDate.getTomorrowDate())){
            oneDateHeader[0] = res.getStringArray(R.array.task_headers)[1]; //wpisz "Jutro"
        }else{
            oneDateHeader[0] = date.getDateStringDMY(); //Wpisz pełną datę
        }
        //------------------------------------------------------------------------------------------

        todoCursor = db.getTasksForGivenDate(date);
        tasksForGivenDate = new ArrayList<Task>();

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
                tasksForGivenDate.add(task);

            } while(todoCursor.moveToNext());
        }

        listForGivenDate.put(oneDateHeader[0], tasksForGivenDate);


        return new ExpandableTaskListAdapter(context, oneDateHeader,listForGivenDate);
    }

}
