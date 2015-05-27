package PreparingData;

import android.content.Context;
import android.database.Cursor;

import DataModel.MyDate;
import DataModel.Task;
import Database.DbAdapter;

/**
 * Created by Agnieszka on 2015-05-09.
 */
public class CurrentCreatingTask extends Task{

    static CurrentCreatingTask instance;
    DbAdapter db;
    private CurrentCreatingTask(){
        instance = this;
    }
    public String group_name;
    public String user_executor_name;

    public static CurrentCreatingTask getInstance(){
        return (instance == null) ? new CurrentCreatingTask() : instance;
    }

    public void cancelTask(){
        instance = new CurrentCreatingTask();
        description = "";
        date_insert = new MyDate();
        date_exec = new MyDate();
        date_archive = new MyDate();
        date_plan_exec = new MyDate();
        date_update = new MyDate();
        this.group_name = null;
        this.user_executor_name = null;
    }

    public void saveTask(Context context){
        db = DbAdapter.getInstance(context);
        db.insert(this);
        this.cancelTask();

    }

    public void updateTask(Context context){
        DbAdapter db = DbAdapter.getInstance(context);
        db.update(this);
        this.cancelTask();
    }

    public void taskCastToCurrentCreatingTask(Task task){
        this.id = task.getId();
        this.id_parent = task.getId_parent();
        this.description = task.getDescription();
        this.priority = task.getPriority();
        this.date_insert = task.getDate_insert();
        this.date_update = task.getDate_update();
        this.date_plan_exec = task.getDate_plan_exec();
        this.date_exec = task.getDate_exec();
        this.date_archive = task.getDate_archive();
        this.cycle_time = task.getCycle_time();
        this.id_group = task.getId_group();
        this.id_executor = task.getId_executor();
        this.id_principal = task.getId_principal();
        this.points = task.getPoints();
    }

    public void setGroup_name(Context context){
        DbAdapter db = DbAdapter.getInstance(context);
        Cursor cursor = db.getGroupById(id_group);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) group_name = cursor.getString(1);

    }
    public String getGroup_name(Context context){
        setGroup_name(context);
        return group_name;
    }

    public void setUser_executor_name(Context context){
        DbAdapter db = DbAdapter.getInstance(context);
        Cursor cursor = db.getMemberById(id_executor);
        cursor.moveToFirst();
        //bedzie podawalo tylko pierwsza nazwe uzytkownika jaka sie pojawi
        /*if (cursor.getCount() > 0)*/ user_executor_name = cursor.getString(2);//getString(2);
    }
    public String getUser_executor_name(Context context){
        setUser_executor_name(context);
        return user_executor_name;
    }

}
