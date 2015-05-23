package al.taskmasterprojinz;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import PreparingViewsAdapter.UsersListAdapter;


public class AddGroup extends Activity {

    ImageButton cancel, save;
    EditText description;
    ListView listOfUsers;
    UsersListAdapter listAdapter;
    HashMap<String, String> users; //name, email
    List<String> usersName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        initUIElements();
    }

    public void initUIElements(){
        cancel = (ImageButton) findViewById(R.id.clear_task_button);
        save = (ImageButton) findViewById (R.id.save_task_button);
        description = (EditText) findViewById(R.id.description_edit);
        listOfUsers = (ListView) findViewById(R.id.added_users_list);

        users = new HashMap<String, String>();
        usersName = new ArrayList<String>();
        for (String s : users.keySet()){
            usersName.add(s);
        }
        listAdapter = new UsersListAdapter(getApplicationContext(),R.layout.users_on_list, usersName);
        listOfUsers.setAdapter(listAdapter);
    }

    

}