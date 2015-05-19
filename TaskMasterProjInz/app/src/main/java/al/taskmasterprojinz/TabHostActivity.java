package al.taskmasterprojinz;

import android.app.ActionBar;
import android.app.ActivityGroup;
import android.app.DatePickerDialog;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.util.Calendar;

import DataModel.MyDate;
import Database.DbAdapter;
import PreparingData.CurrentCreatingTask;


public class TabHostActivity extends al.taskmasterprojinz.Menu {

    LocalActivityManager mLocalActivityManager;
    ImageButton addTask, calendar, removeTask;
    DatePickerDialog datePickerDialog;
    CurrentCreatingTask newTask;
    TabHost.TabSpec tab1, tab2, tab3;
    TabHost tabHost;
    Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_host);
        setTitle("TabHost activity");
        res = getResources();
        initUIElements();

        // create the TabHost that will contain the Tabs
        tabHost = (TabHost)findViewById(android.R.id.tabhost);

        tab1 = tabHost.newTabSpec("First Tab");
        tab2 = tabHost.newTabSpec("Second Tab");
        tab3 = tabHost.newTabSpec("Third tab");

        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        mLocalActivityManager = new LocalActivityManager(this, false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);

        initStandardTab();

    }

    protected void onResume(){
        super.onResume();
        MyTasksView.refresh();
        MyTasksForDate.refresh();

    }

    protected void onRestart(){
        super.onResume();
        MyTasksView.refresh();
        MyTasksForDate.refresh();

    }

    private void initStandardTab(){

        tab1.setIndicator(res.getString(R.string.my_task_label_txt));
        tab1.setContent(new Intent(getApplicationContext(),MyTasksView.class));

        tab2.setIndicator(res.getString(R.string.sended_tasks));
        tab2.setContent(new Intent(getApplicationContext(),MyTasksView.class));

        tab3.setIndicator(res.getString(R.string.group_tasks));
        tab3.setContent(new Intent(getApplicationContext(),MyTasksView.class));

        /** Add the tabs  to the TabHost to display. */

        tabHost.setup(mLocalActivityManager);

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);

        tabHost.setCurrentTab(0);
    }

    private void initNewTab(){
        Resources res = getResources();
        tab1.setIndicator(res.getString(R.string.my_task_label_txt));
        tab1.setContent(new Intent(getApplicationContext(),MyTasksForDate.class));

        tab2.setIndicator(res.getString(R.string.sended_tasks));
        tab2.setContent(new Intent(getApplicationContext(),MyTasksForDate.class));

        tab3.setIndicator(res.getString(R.string.group_tasks));
        tab3.setContent(new Intent(getApplicationContext(),MyTasksForDate.class));

        /** Add the tabs  to the TabHost to display. */

        tabHost.setup(mLocalActivityManager);

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);

        tabHost.setCurrentTab(0);
    }

    private void initUIElements(){

        calendar = (ImageButton) findViewById(R.id.calendar_button);
        addTask = (ImageButton) findViewById(R.id.add_task_button);
        removeTask = (ImageButton) findViewById(R.id.delete_button);

        initOnClickListeners();


    }

    private void initOnClickListeners() {
        OnClickListener onClickListener = new OnClickListener() {
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
                }
            }
        };

        calendar.setOnClickListener(onClickListener);
        addTask.setOnClickListener(onClickListener);

        registerForContextMenu(removeTask);
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

                //zmiana na zakladki z zadaniami tylko z konkretna data

                tabHost.getCurrentTab();
                tabHost.clearAllTabs();
                initNewTab();


            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }


    private void editNewTask(){
        Intent edit_task_activity = new Intent(getApplicationContext(), CreateTask.class);
        startActivity(edit_task_activity);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

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
        //tabHost.clearAllTabs();
        //initStandardTab();
        int current = tabHost.getCurrentTab();
        for (int i =0; i <3; i++){
            tabHost.setCurrentTab(i);
            onRestart();
        }
        tabHost.setCurrentTab(current);


        //initList();
        //listAdapter.notifyDataSetChanged();
    }
    public void function2(int id){
        Toast.makeText(this, res.getString(R.string.removed_all_task), Toast.LENGTH_SHORT).show();
        DbAdapter db = DbAdapter.getInstance(getApplicationContext());
        db.deleteAllTasks();
        int current = tabHost.getCurrentTab();
        for (int i =0; i <3; i++){
            tabHost.setCurrentTab(i);
            onRestart();
        }
        tabHost.setCurrentTab(current);

        //initList();
        //listAdapter.notifyDataSetChanged();
    }

}
