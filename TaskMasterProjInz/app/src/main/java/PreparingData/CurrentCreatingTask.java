package PreparingData;

import DataModel.MyDate;
import DataModel.Task;

/**
 * Created by Agnieszka on 2015-05-09.
 */
public class CurrentCreatingTask extends Task{

    static CurrentCreatingTask instance;
    private CurrentCreatingTask(){
        instance = this;
        setDate_insert(MyDate.getTodayDate());

    }

    public static CurrentCreatingTask getInstance(){
        return (instance == null) ? new CurrentCreatingTask() : instance;
    }

    public void cancelTask(){
        instance = new CurrentCreatingTask();
    }

}
