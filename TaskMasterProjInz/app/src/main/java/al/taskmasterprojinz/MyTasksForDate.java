package al.taskmasterprojinz;

import android.os.Bundle;
import android.widget.ExpandableListView;

import PreparingData.CurrentCreatingTask;
import PreparingData.PrepareListOfTask;

/**
 * Created by Agnieszka on 2015-05-11.
 */
public class MyTasksForDate extends MainTaskListView {

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initList();
        listAdapter.notifyDataSetChanged();
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
        listAdapter = prepTask.tasksForGivenDate(newTask.getDate_exec());

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
}
