package al.taskmasterprojinz;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import PreparingData.CurrentCreatingTask;
import PreparingData.PrepareListOfTask;

/**
 * Created by Agnieszka on 2015-05-11.
 */
public class MyTasksForDate extends TasksListViewPattern {

    static MyTasksForDate instance;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initList();
        listAdapter.notifyDataSetChanged();
        instance = this;
        System.out.println("MY TASK FOR DATE STARTED");

    }

    protected void onResume(){
        super.onResume();
        initList();
        listAdapter.notifyDataSetChanged();
    }

    protected void onRestart(){
        super.onRestart();
        initList();
        listAdapter.notifyDataSetChanged();
    }

    private void initList(){

        //System.out.println("MY TASKS FOR DATE INIT LIST");
        prepTask = PrepareListOfTask.getInstance(this);
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);

        newTask = CurrentCreatingTask.getInstance();
        if(newTask.getDate_plan_exec().isEmpty()) {
            listAdapter = prepTask.todayTomorrowInFutureTaskLists();
            header_task_list.setVisibility(View.GONE);
        }
        else{
            listAdapter = prepTask.tasksForGivenDate(newTask.getDate_plan_exec());
            System.out.println("ZAINICJOWANO LIST ADAPTERA");

        }

        expListView.setAdapter(listAdapter);
        if (listAdapter.canExpandFirstGroup()){
            expListView.expandGroup(0); //mozna rozwijac tylko wtedy kiedy lista nie jest pusta
        }

    }

    private void backToTodayTomorrowFutureView(){
        finish();
        onBackPressed();
        //filtrDate = new MyDate();
        //standardList = true;
        //initList();
        //listAdapter.notifyDataSetChanged();
        //newTask = CurrentCreatingTask.getInstance();
        //newTask.setDate_plan_exec(filtrDate);
    }
    public static void refresh(){
        if (instance != null){
            instance.initList();
            instance.listAdapter.notifyDataSetChanged();
        }

    }


}
