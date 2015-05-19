package al.taskmasterprojinz;

import android.os.Bundle;
import android.widget.ExpandableListView;

import PreparingData.PrepareListOfTask;

/**
 * Created by Agnieszka on 2015-05-15.
 */
public class MyTasksView extends TasksListViewPattern {

    static MyTasksView instance;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        instance = this;
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

    public static MyTasksView getInstance(){
        return instance;
    }

    private void initList(){

        prepTask = PrepareListOfTask.getInstance(this);
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);
        listAdapter = prepTask.todayTomorrowInFutureTaskLists();

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
