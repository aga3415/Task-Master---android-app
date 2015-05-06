package PreparingData;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DataModel.MyDate;
import DataModel.Task;
import Database.DbAdapter;
import Database.TasksTable;

/**
 * Created by Agnieszka on 2015-05-05.
 */
public class PrepareTask {

    Context context;
    DbAdapter db;
    static PrepareTask instance;
    TasksTable tasksTable;
    HashMap<String, List<Task>> todayTommorowFutureList;

    private PrepareTask(DbAdapter db, Context context){
        this.context = context;
        this.db = db;
        instance = this;

        db.open();
    }

    public static PrepareTask getInstance(DbAdapter db, Context context){
        return (instance == null) ? new PrepareTask(db,context) : instance;
    }

    public HashMap<String, List<Task>> todayTommorowInFutureTaskLists(){
            todayTommorowFutureList = new HashMap<>();

        /*String untildate="2011-10-08";//can take any date in current format
        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime( dateFormat.parse(untildate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.add( Calendar.DATE, 1 );
        String convertedDate=dateFormat.format(cal.getTime());
        MyDate currentDateDateClass = new MyDate(convertedDate);*/

        //Cursor todoCursor = db.getTasksTable().getAllTasks();
        Cursor todoCursor = db.getAllTask();
        System.out.println("Pobieram wszystkie taski");
        /*if(todoCursor != null) {
            startManagingCursor(todoCursor);
        }*/ //nie dziala bo nie ma aktywnoosci?

        String [] headers = {"Dzisiaj", "Jutro", "Kiedyś"};
        List<Task> tasks = new ArrayList<Task>();

        if(todoCursor != null && todoCursor.moveToFirst()) {
            if (todoCursor.getCount() == 0) headers[0] = "Pusto tutaj";
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
                //int points = 0;
                tasks.add(new Task(id, id_parent, description, priority, date_insert, date_update, date_plan_exec,
                        date_exec, date_archive, cycle_time, id_group, id_executor, id_principal, points));

            } while(todoCursor.moveToNext());


        }
        todayTommorowFutureList.put(headers[0], tasks);

        return todayTommorowFutureList;
    }
}
