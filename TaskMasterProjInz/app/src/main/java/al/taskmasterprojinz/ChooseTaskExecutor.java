package al.taskmasterprojinz;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;

import DataModel.MyDate;
import PreparingData.CurrentCreatingTask;
import PreparingData.PrepareListGroupsAndUsers;
import PreparingViewsAdapter.ExpandableExecutorsListAdapter;


public class ChooseTaskExecutor extends Activity {

    PrepareListGroupsAndUsers prepare;
    ExpandableListView expListView;
    ExpandableExecutorsListAdapter listAdapter;
    ImageButton save, cancel;
    CurrentCreatingTask newTask;
    long group_id;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_task_executor);

        prepare = new PrepareListGroupsAndUsers(getApplicationContext());

        initList();
        save = (ImageButton) findViewById(R.id.save_task_button);
        cancel = (ImageButton) findViewById(R.id.clear_task_button);
        newTask = CurrentCreatingTask.getInstance();
        group_id = newTask.getId_group();
        user_id = newTask.getId_executor();

        initOnClickListeners();
    }

    private void initList(){

        //prepTask = PrepareListOfTask.getInstance(this);
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);
        listAdapter = prepare.getPreparedExecutorsAdapter();

        expListView.setAdapter(listAdapter);

        for (int i=0; i< listAdapter.getGroupCount(); i++){
            expListView.expandGroup(i);
        }
        //expListView.collapseGroup(0);
        //expListView.expandGroup(0);

        expListView.setOnGroupClickListener(listAdapter.onGroupClickListener());
        expListView.setOnChildClickListener(listAdapter.onChildClickListener());

        /*if (listAdapter.canExpandFirstGroup()){
            expListView.expandGroup(0); //mozna rozwijac tylko wtedy kiedy lista nie jest pusta
        }*/

    }

    public void initOnClickListeners() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.clear_task_button:
                        //zaimplementowany -
                        cancelTaskListener();
                        break;
                    case R.id.save_task_button:
                        //zaimplementowany +
                        addTaskListener();
                        //defaultListener();
                        break;

                }
            }
        };
        cancel.setOnClickListener(onClickListener);
        save.setOnClickListener(onClickListener);


    }

    public void cancelTaskListener(){
        newTask.setId_group(group_id);
        newTask.setId_executor(user_id);

        //finishActivity(0);
        //onBackPressed();
        onBackPressed();
        //finish();
    }

    public void addTaskListener(){

        onBackPressed();
        //finishActivity(0);

    }



}
