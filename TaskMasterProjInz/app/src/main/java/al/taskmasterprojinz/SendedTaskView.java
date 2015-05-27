package al.taskmasterprojinz;


import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import DataModel.MyDate;
import PreparingData.CurrentCreatingTask;
import PreparingData.PrepareListOfTask;

/**
 * Created by Agnieszka on 2015-05-22.
 */
public class SendedTaskView extends TasksListViewPattern{
    static SendedTaskView instance;
    CurrentCreatingTask newTask;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        instance = this;
        initList();
        listAdapter.notifyDataSetChanged();
        newTask = CurrentCreatingTask.getInstance();
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

    public static SendedTaskView getInstance(){
        return instance;
    }

    private void initList(){

        newTask = CurrentCreatingTask.getInstance();
        prepTask = PrepareListOfTask.getInstance(this);
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);
        if (newTask.getDate_plan_exec().isEmpty()) {
            listAdapter = prepTask.sendedTasksList();
            header_task_list.setVisibility(View.GONE);
        }else if (MyDate.isEqual(newTask.getDate_plan_exec(), MyDate.getTodayDate())) {
            listAdapter = prepTask.sendedTasksListForToday();
            header_task_list.setVisibility(View.VISIBLE);
            header_task_list.setText(MyDate.getTodayDate().getDateStringDMY());
        }else{
            listAdapter = prepTask.sendedTasksListForDate(newTask.getDate_plan_exec());
            header_task_list.setVisibility(View.VISIBLE);
            header_task_list.setText(newTask.getDate_plan_exec().getDateStringDMY());
        }

        /*if (!newTask.getDate_plan_exec().isEmpty()){
            header_task_list.setVisibility(View.VISIBLE);
            header_task_list.setText(MyDate.getTodayDate().getDateStringDMY());
        }*/


        expListView.setAdapter(listAdapter);
        if (listAdapter.canExpandFirstGroup()){
            expListView.expandGroup(0); //mozna rozwijac tylko wtedy kiedy lista nie jest pusta
        }

    }
    public static void refresh() {
        if (instance != null) {
            instance.initList();
            instance.listAdapter.notifyDataSetChanged();
        }

    }

}
