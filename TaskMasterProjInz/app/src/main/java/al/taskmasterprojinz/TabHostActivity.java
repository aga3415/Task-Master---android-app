package al.taskmasterprojinz;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;


public class TabHostActivity extends ActivityGroup {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_host);

        // create the TabHost that will contain the Tabs
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);



        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Third tab");

        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        Resources res = getResources();
        tab1.setIndicator(res.getString(R.string.my_task_label_txt));
        tab1.setContent(new Intent(getApplicationContext(), MyTasksView.class));

        tab2.setIndicator(res.getString(R.string.sended_tasks));
        tab2.setContent(new Intent(getApplicationContext(),MyTasksView.class));

        tab3.setIndicator(res.getString(R.string.group_tasks));
        tab3.setContent(new Intent(getApplicationContext(),MyTasksView.class));

        /** Add the tabs  to the TabHost to display. */
        tabHost.setup();

        LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
        tabHost.setup(mLocalActivityManager);

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);

        tabHost.setCurrentTab(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab_host, menu);
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
}
