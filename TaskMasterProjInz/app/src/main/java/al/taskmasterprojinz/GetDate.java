package al.taskmasterprojinz;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import DataModel.MyDate;


public class GetDate extends Dialog {
    int year, month, day;
    Intent backToEditTask;
    Context context;
    DatePicker datePicker;
    Button saveDate;

    public GetDate(Context context) {
        super(context);
        setContentView(R.layout.activity_get_date);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                year = datePicker.getYear();
                month = datePicker.getMonth()+1;
                day = datePicker.getDayOfMonth();



                /*context = getApplicationContext();
                backToEditTask = new Intent(context, MainView.class);
                backToEditTask.putExtra("year", Integer.toString(year));
                backToEditTask.putExtra("month", Integer.toString(month));
                backToEditTask.putExtra("day", Integer.toString(day));
                startActivity(backToEditTask);*/
            }
        });

    }


    //@Override
    /*protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_date);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        //saveDate = (Button) findViewById(R.id.save_date_button);

        Calendar calendar = Calendar.getInstance();

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                year = datePicker.getYear();
                month = datePicker.getMonth()+1;
                day = datePicker.getDayOfMonth();

                Toast.makeText(getApplicationContext(),
                        "onDateChanged", Toast.LENGTH_SHORT).show();

                /*context = getApplicationContext();
                backToEditTask = new Intent(context, MainView.class);
                backToEditTask.putExtra("year", Integer.toString(year));
                backToEditTask.putExtra("month", Integer.toString(month));
                backToEditTask.putExtra("day", Integer.toString(day));
                startActivity(backToEditTask);
            }
        });

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                backToEditTask = new Intent(context, MainView.class);
                backToEditTask.putExtra("year", Integer.toString(year));
                backToEditTask.putExtra("month", Integer.toString(month));
                backToEditTask.putExtra("day", Integer.toString(day));
                startActivity(backToEditTask);
            }
        });

        saveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = datePicker.getYear();
                month = datePicker.getMonth()+1;
                day = datePicker.getDayOfMonth();

                context = getApplicationContext();
                backToEditTask = new Intent(context, MainView.class);
                backToEditTask.putExtra("year", Integer.toString(year));
                backToEditTask.putExtra("month", Integer.toString(month));
                backToEditTask.putExtra("day", Integer.toString(day));
                startActivity(backToEditTask);
            }
        });

    }





    public class MyOnDateChangedListener implements DatePicker.OnDateChangedListener {
        int year, month, day;
        Context context;

        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            this.year = year;
            this.month = month;
            this.day = day;
            Toast.makeText(getApplicationContext(),
                    " thiv view"+year,
                    Toast.LENGTH_LONG).show();

            context = getApplicationContext();
            backToEditTask = new Intent(context, MainView.class);
            backToEditTask.putExtra("year", Integer.toString(year));
            backToEditTask.putExtra("month", Integer.toString(month));
            backToEditTask.putExtra("day", Integer.toString(day));
            startActivity(backToEditTask);
        }


    }*/

}
