package al.taskmasterprojinz;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import DataModel.MyDate;
import DataModel.Task;
import Database.DbAdapter;
import PreparingData.PrepareTask;


public class MainView extends ActionBarActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    ImageButton edit, save, cancel;
    TextView task_description_text;
    HashMap<String, List<Task>> listOfTask;
    PrepareTask prepTask;
    DbAdapter db;
    Task newTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        initUIElements();
        initList();
        newTask = new Task();
        Intent previous = getIntent();
        //if (previous != null){
            String year = previous.getStringExtra("year");
            String month = previous.getStringExtra("month");
            String day = previous.getStringExtra("day");

            if (year != null && month != null && day != null) newTask.setDate_plan_exec(new MyDate(Integer.parseInt(day), Integer.parseInt(month),Integer.parseInt(year)));
            if (year != null) System.out.println("year "+ Integer.parseInt(year)+ "month " + Integer.parseInt(month) + "day " + Integer.parseInt(day));
//            Toast.makeText(getApplicationContext(),"Data wykonania" + new MyDate(year+"-"+month+"-"+day).getDateString(), Toast.LENGTH_LONG).show();
        if (year != null) Toast.makeText(getApplicationContext(),"Data wykonania" + new MyDate(Integer.parseInt(day), Integer.parseInt(month),Integer.parseInt(year)).getDateString(), Toast.LENGTH_LONG).show();
        //}


    }

    private void initDate(){
        db = DbAdapter.getInstance(this);
        //db.refresh();
        listOfTask = new HashMap<String, List<Task>>();

        prepTask = PrepareTask.getInstance(db,this);
        listOfTask = prepTask.todayTommorowInFutureTaskLists();

    }
    private void initUIElements(){

        edit = (ImageButton) findViewById(R.id.edit_task_button);
        save = (ImageButton) findViewById(R.id.add_task_button);
        cancel = (ImageButton) findViewById(R.id.clear_task_button);
        task_description_text = (TextView) findViewById(R.id.edit_task_text);
        hideKeyboard();
        initButtonsOnClickListeners();


    }
    private void initList(){
        initDate();
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);
        if (!listOfTask.isEmpty()){
            listAdapter = new ExpandableListAdapter(this, listOfTask);
            expListView.setAdapter(listAdapter);
            expListView.expandGroup(0);
        }

    }


    private void initButtonsOnClickListeners() {
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
                }
            }
        };
        edit.setOnClickListener(onClickListener);
        save.setOnClickListener(onClickListener);
        cancel.setOnClickListener(onClickListener);
    }

    private void cancelNewTask(){
        task_description_text.setText("");

    }

    final DatePickerDialog.OnDateSetListener odsl = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //newTask.setDate_exec(new MyDate(dayOfMonth,monthOfYear,year));
        };
    };

    private void editNewTask(){
        Calendar cal=Calendar.getInstance();
        DatePickerDialog datePickDiag= new DatePickerDialog(getApplicationContext(), odsl, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
        //datePickDiag.show();

        Context context = getApplicationContext();
        Intent editTaskIntent = new Intent(context, GetDate.class);
        startActivity(editTaskIntent);

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
            if (!listOfTask.isEmpty()) listAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(),"Data wykonania" + newTask.getDate_plan_exec().getDateString(), Toast.LENGTH_LONG).show();
            newTask = new Task();

        }


    }



    /*private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listOfTask = new HashMap<String, List<Task>>();

        // Adding child data
        listDataHeader.add("Pusto tutaj");
        listDataHeader.add("Jutro");
        listDataHeader.add("Kiedyś");
        PrepareTask prepTask = PrepereTask.getInstance(db)
        listOfTask =
        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");


        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);


    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(task_description_text.getWindowToken(), 0);
    }
}
