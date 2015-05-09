package al.taskmasterprojinz;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import DataModel.Task;
import Database.DbAdapter;
import PreparingData.CurrentCreatingTask;
import PreparingData.PrepareListOfTask;
import PreparingViewsAdapter.ExpandableTaskListAdapter;


public class MainView extends ContextMenu {

    ExpandableTaskListAdapter listAdapter;
    ExpandableListView expListView;

    ImageButton edit, save, cancel;
    TextView task_description_text;
    HashMap<String, List<Task>> listOfTask;
    PrepareListOfTask prepTask;
    DbAdapter db;
    CurrentCreatingTask newTask;
    Intent edit_task_activity;

    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        initUIElements();
        initList();
        newTask = CurrentCreatingTask.getInstance();

        //Intent previous = getIntent();
        //if (previous != null){
        //    String year = previous.getStringExtra("year");
        //    String month = previous.getStringExtra("month");
        //    String day = previous.getStringExtra("day");
        //
        //    if (year != null && month != null && day != null) newTask.setDate_plan_exec(new MyDate(Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year)));
        //    if (year != null) System.out.println("year "+ Integer.parseInt(year)+ "month " + Integer.parseInt(month) + "day " + Integer.parseInt(day));
//            Toast.makeText(getApplicationContext(),"Data wykonania" + new MyDate(year+"-"+month+"-"+day).getDateString(), Toast.LENGTH_LONG).show();
        //if (year != null) Toast.makeText(getApplicationContext(),"Data wykonania" + new MyDate(Integer.parseInt(day), Integer.parseInt(month),Integer.parseInt(year)).getDateString(), Toast.LENGTH_LONG).show();
        //}


    }

    private void initDate(){
        db = DbAdapter.getInstance(this);
        //db.refresh();
        listOfTask = new HashMap<String, List<Task>>();

        prepTask = PrepareListOfTask.getInstance(db, this);
        listOfTask = prepTask.todayTommorowInFutureTaskLists();

    }
    private void initUIElements(){

        edit = (ImageButton) findViewById(R.id.edit_task_button);
        save = (ImageButton) findViewById(R.id.add_task_button);
        cancel = (ImageButton) findViewById(R.id.clear_task_button);
        task_description_text = (TextView) findViewById(R.id.edit_task_text);

        initOnClickListeners();


    }
    private void initList(){
        initDate();
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);
        listAdapter = new ExpandableTaskListAdapter(this, listOfTask);
        expListView.setAdapter(listAdapter);
        if (!prepTask.isEmptyList()){
            expListView.expandGroup(0); //mozna rozwijac tylko wtedy kiedy lista nie jest pusta
        }

    }


    private void initOnClickListeners() {
        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.edit_task_button:
                        editNewTask();
                        //pierwszy przycisk po lewej
                        break;
                    case R.id.add_task_button:
                        saveNewTask();
                        //znak +
                        break;
                    case R.id.clear_task_button:
                        cancelNewTask();
                        //znak -
                        break;
                    case R.id.edit_task_text:
                        task_description_text.setError(null);
                        break;
                }
            }
        };

        edit.setOnClickListener(onClickListener);
        save.setOnClickListener(onClickListener);
        cancel.setOnClickListener(onClickListener);
        task_description_text.setOnClickListener(onClickListener);
    }

    private void cancelNewTask(){
        task_description_text.setText("");
        task_description_text.setError(null);

    }


    private void editNewTask(){
        //Calendar cal=Calendar.getInstance();
        task_description_text.setError(null);

        edit_task_activity = new Intent(getApplicationContext(), CreateTask.class);
        startActivity(edit_task_activity);

        /*dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Calendar newCalendar = Calendar.getInstance();
        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Toast.makeText(getApplicationContext(),
                        "onDateChanged", Toast.LENGTH_SHORT).show();
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        toDatePickerDialog.show();
*/
    }

    private void saveNewTask(){
        String description = task_description_text.getText().toString();
        if(description.equals("")){
            task_description_text.setError("Nie trzeba planować lenistwa, wpisz coś ;)");
        } else{
            task_description_text.setText("");
            hideKeyboard();
            newTask.setDescription(description);
            db.insert(newTask);
            initList();
            listAdapter.notifyDataSetChanged();
            newTask.cancelTask();

        }


    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(task_description_text.getWindowToken(), 0);
    }
}

