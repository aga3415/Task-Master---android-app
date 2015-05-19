package al.taskmasterprojinz;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import DataModel.MyDate;
import Database.DbAdapter;
import PreparingData.CurrentCreatingTask;
import PreparingData.PrepareListOfTask;
import PreparingViewsAdapter.ExpandableTaskListAdapter;


public class TasksListViewPattern extends Activity {

    ExpandableTaskListAdapter listAdapter;
    ExpandableListView expListView;

    TextView header;
    ImageButton calendar, edit, remove, menu;

    PrepareListOfTask prepTask;
    boolean standardList = true;

    Intent edit_task_activity;

    DatePickerDialog datePickerDialog;
    CurrentCreatingTask newTask;

    Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_llist_vie_pattern);

        res = getApplicationContext().getResources();
        initUIElements();


    }




    private void initUIElements(){

        //header = (TextView) findViewById(R.id.my_task_label);
        //calendar = (ImageButton) findViewById(R.id.calendar_button);
        //edit = (ImageButton) findViewById(R.id.add_task_button);
        //remove = (ImageButton) findViewById(R.id.clear_task_button);
        //menu = (ImageButton) findViewById(R.id.menu_button);

        //initOnClickListeners();


    }
    private void initList(){

        prepTask = PrepareListOfTask.getInstance(this);
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);

        newTask = CurrentCreatingTask.getInstance();
        listAdapter = prepTask.tasksForGivenDate(newTask.getDate_plan_exec());

        expListView.setAdapter(listAdapter);
        if (listAdapter.canExpandFirstGroup()){
            expListView.expandGroup(0); //mozna rozwijac tylko wtedy kiedy lista nie jest pusta
        }

    }


    /*private void initOnClickListeners() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.calendar_button:
                        chooseDate();
                        //pierwszy przycisk po lewej
                        break;
                    case R.id.add_task_button:
                        editNewTask();
                        //znak +
                        break;
                    //case R.id.clear_task_button:
                    //    removeTasks();
                    //znak -
                    //    break;
                    case R.id.my_task_label :
                        backToTodayTomorrowFutureView();
                        break;
                    case R.id.menu_button :
                        TasksListViewPattern.this.openOptionsMenu();
                        break;

                }
            }
        };

        calendar.setOnClickListener(onClickListener);
        edit.setOnClickListener(onClickListener);
        //remove.setOnClickListener(onClickListener);
        header.setOnClickListener(onClickListener);
        menu.setOnClickListener(onClickListener);

        registerForContextMenu(remove);
    }

    private void chooseDate(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                MyDate plan_exec = new MyDate(dayOfMonth,monthOfYear + 1, year);
                newTask = CurrentCreatingTask.getInstance();
                newTask.setDate_plan_exec(plan_exec);
                //powinno byc jeszcze wywolanie metody filtrowania zadan
                Intent taskListForChoosenDate = new Intent(getApplicationContext(), MyTasksForDate.class);
                startActivity(taskListForChoosenDate);
                //standardList = false;
                //filtrDate = plan_exec;
                //initList();
                listAdapter.notifyDataSetChanged();


            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }


    private void editNewTask(){
        edit_task_activity = new Intent(getApplicationContext(), CreateTask.class);
        startActivity(edit_task_activity);
    }



    private void backToTodayTomorrowFutureView(){
        MyDate filtrDate = new MyDate();
        standardList = true;
        initList();
        listAdapter.notifyDataSetChanged();
        newTask = CurrentCreatingTask.getInstance();
        newTask.setDate_plan_exec(filtrDate);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //menu.setHeaderTitle(res.getString(R.string.remove_task_menu_header));
        menu.add(0, v.getId(), 0, res.getString(R.string.remove_task_menu_item1));
        menu.add(0, v.getId(), 0, res.getString(R.string.remove_task_menu_item2));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(res.getString(R.string.remove_task_menu_item1))){
            function2(item.getItemId());
        }
        else if(item.getTitle().equals(res.getString(R.string.remove_task_menu_item2))){
            function1(item.getItemId());
        }
        else {return false;}
        return true;
    }
    public void function1(int id){
        Toast.makeText(this, res.getString(R.string.removed_completed_task), Toast.LENGTH_SHORT).show();
        DbAdapter db = DbAdapter.getInstance(getApplicationContext());
        db.deleteCompletedTasks();
        initList();
        listAdapter.notifyDataSetChanged();
    }
    public void function2(int id){
        Toast.makeText(this, res.getString(R.string.removed_all_task), Toast.LENGTH_SHORT).show();
        DbAdapter db = DbAdapter.getInstance(getApplicationContext());
        db.deleteAllTasks();
        initList();
        listAdapter.notifyDataSetChanged();
    }


*/
}
