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

    TextView header, header_task_list;
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
        header_task_list = (TextView) findViewById(R.id.task_list_header_title);
    }

}
