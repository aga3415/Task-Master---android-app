package al.taskmasterprojinz;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import PreparingData.PrepareListOfTask;

/**
 * Created by Agnieszka on 2015-05-27.
 */
public class GroupsTasks extends TasksListViewPattern {

    static GroupsTasks instance;

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

    public static GroupsTasks getInstance(){
        return instance;
    }

    private void initList(){

        prepTask = PrepareListOfTask.getInstance(this);
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);
        listAdapter = prepTask.groupedTasksList();
        header_task_list.setVisibility(View.GONE);

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
