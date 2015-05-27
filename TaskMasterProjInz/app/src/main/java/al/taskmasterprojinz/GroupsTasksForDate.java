package al.taskmasterprojinz;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import DataModel.MyDate;
import PreparingData.CurrentCreatingTask;
import PreparingData.PrepareListOfTask;

/**
 * Created by Agnieszka on 2015-05-27.
 */
public class GroupsTasksForDate extends TasksListViewPattern {

    static GroupsTasksForDate instance;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initList();
        listAdapter.notifyDataSetChanged();
        instance = this;
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

        prepTask = PrepareListOfTask.getInstance(this);
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);

        newTask = CurrentCreatingTask.getInstance();
        if (newTask.getDate_plan_exec().isEmpty()){
            listAdapter = prepTask.groupedTasksList();
            header_task_list.setVisibility(View.GONE);

        }else if (MyDate.isEqual(newTask.getDate_plan_exec(), MyDate.getTodayDate())){
            listAdapter = prepTask.groupedTasksListForDate(newTask.getDate_plan_exec());
            header_task_list.setVisibility(View.VISIBLE);
            header_task_list.setText(newTask.getDate_plan_exec().getDateStringDMY());
        }else{
            listAdapter = prepTask.groupedTasksListForDate(newTask.getDate_plan_exec());
            header_task_list.setVisibility(View.VISIBLE);
            header_task_list.setText(newTask.getDate_plan_exec().getDateStringDMY());
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
