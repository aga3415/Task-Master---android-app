package al.taskmasterprojinz;

import android.app.DatePickerDialog;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.view.View.OnClickListener;
import java.util.Calendar;
import DataModel.MyDate;
import Database.DbAdapter;
import PreparingData.CurrentCreatingTask;
import PreparingData.PrepareListOfTask;
import PreparingViewsAdapter.ExpandableTaskListAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Toast;


public class MainView extends Menu {

    ExpandableTaskListAdapter listAdapter;
    ExpandableListView expListView;

    TextView header;
    ImageButton calendar, edit, remove, menu;

    PrepareListOfTask prepTask;
    boolean standardList = true;
    MyDate filtrDate;

    Intent edit_task_activity;

    DatePickerDialog datePickerDialog;
    CurrentCreatingTask newTask;

    Resources res;

    LocalActivityManager mLocalActivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* otwieranie przegladarki z adresem strony */
        //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        //startActivity(browserIntent);
        //------------------------------------------------------------------------------------------

        //otwieranie aktywnosci logowania
        Intent login = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(login);

        /* otwieranie aktywnosci z lista zadan */
        Intent tabHostActivity = new Intent(getApplicationContext(), TabHostActivity.class);
        startActivity(tabHostActivity);
        //------------------------------------------------------------------------------------------



    }




}

