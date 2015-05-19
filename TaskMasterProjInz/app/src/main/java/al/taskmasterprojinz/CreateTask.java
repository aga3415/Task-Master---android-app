package al.taskmasterprojinz;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.util.Calendar;

import DataModel.MyDate;
import Database.DbAdapter;
import PreparingData.CurrentCreatingTask;


public class CreateTask extends Activity {

    ImageButton cancel, save;
    EditText description;
    TextView txt_date_exec, txt_executor, txt_points, txt_priority, txt_cycle, txt_alarm;
    LinearLayout v_date_exec, v_executor, v_points, v_cycle, v_alarm, v_priority;

    CurrentCreatingTask newTask;
    DbAdapter db;

    DatePickerDialog datePickerDialog;
    Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        newTask = CurrentCreatingTask.getInstance();
        res = getApplicationContext().getResources();
        db = DbAdapter.getInstance(getApplicationContext());
        initUIElements();
        setCurrentTxt();
    }

    public void initUIElements(){
        cancel = (ImageButton) findViewById(R.id.clear_task_button);
        save = (ImageButton) findViewById (R.id.save_task_button);
        description = (EditText) findViewById(R.id.description_edit);

        v_date_exec = (LinearLayout) findViewById(R.id.calendar_layout);
        v_executor = (LinearLayout) findViewById(R.id.executor_layout);
        v_points = (LinearLayout) findViewById(R.id.points_layout);
        v_cycle = (LinearLayout) findViewById(R.id.cycle_layout);
        v_alarm = (LinearLayout) findViewById(R.id.alarm_layout);
        v_priority = (LinearLayout) findViewById(R.id.priority_layout);

        txt_alarm = (TextView) findViewById(R.id.alarm_txt);
        txt_cycle = (TextView) findViewById(R.id.cycle_txt);
        txt_date_exec = (TextView) findViewById(R.id.plan_exec_txt);
        txt_executor = (TextView) findViewById(R.id.executor_txt);
        txt_points = (TextView) findViewById(R.id.points_txt);
        txt_priority = (TextView) findViewById(R.id.priority_txt);

        initOnClickListeners();

    }

    public void initOnClickListeners() {
        OnClickListener onClickListener = new OnClickListener() {
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
                    case R.id.calendar_layout:
                        //zaimplementowany kalendarz
                        calendarListener();
                        defaultListener();
                        break;
                    default :
                        //zaimplementowane domyślnie
                        defaultListener();
                        break;
                }
            }
        };
        cancel.setOnClickListener(onClickListener);
        save.setOnClickListener(onClickListener);
        v_date_exec.setOnClickListener(onClickListener);
        v_points.setOnClickListener(onClickListener);
        v_priority.setOnClickListener(onClickListener);
        v_cycle.setOnClickListener(onClickListener);
        v_alarm.setOnClickListener(onClickListener);
        v_executor.setOnClickListener(onClickListener);
    }

    public void cancelTaskListener(){
        newTask.cancelTask();
        setCurrentTxt();
        //Toast.makeText(getApplicationContext(),
        //        "Nacisniety -", Toast.LENGTH_SHORT).show();
        finishActivity(0);
        //finish();
    }

    public void addTaskListener(){
        String desc = description.getText().toString();

        if(desc.equals("")){
            description.setError("Nie trzeba planować lenistwa, wpisz coś ;)");
        } else{
            newTask.setDate_insert(MyDate.getTodayDate());
            newTask.setDescription(desc);
            newTask.saveTask(getApplicationContext());
            //db.insert(newTask);
            //newTask.cancelTask();
            setCurrentTxt();
            Toast.makeText(getApplicationContext(), res.getString(R.string.add_new_task_txt), Toast.LENGTH_SHORT).show();
        }
        finishActivity(0);

    }

    public void calendarListener(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                MyDate plan_exec = new MyDate(dayOfMonth,monthOfYear + 1, year);
                newTask.setDate_plan_exec(plan_exec);
                txt_date_exec.setText(res.getText(R.string.plan_exec_added)+ plan_exec.getDateString());


            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    public void defaultListener(){
        description.setError(null);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(description.getWindowToken(), 0);
    }

    public void setCurrentTxt(){
        if (newTask.getDate_plan_exec().getDateString() == ""){
            txt_date_exec.setText(res.getString(R.string.plan_exec));
        }else{
            txt_date_exec.setText(res.getString(R.string.plan_exec_added)+ newTask.getDate_plan_exec().getDateStringDMY());
        }
        if (newTask.getDescription() == ""){
            description.setText("");
            description.setHint(res.getString(R.string.description));
        }else{
            description.setText(newTask.getDescription());
        }

    }

    public void onBackPressed(){
        //finishActivity(0);
        finish();
    }





}
