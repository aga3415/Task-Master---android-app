package al.taskmasterprojinz;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.*;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;

import Database.DbAdapter;
import MySQLConnection.DeleteAllMyGroups;
import PreparingData.PrepareListGroupsAndUsers;
import PreparingData.PrepareListOfTask;
import PreparingViewsAdapter.ExpandableGroupsListAdapter;

/**
 * Created by Agnieszka on 2015-05-19.
 */
public class GroupsView extends Menu {
    static GroupsView instance;

    ImageButton addGroup, addMember, remove;
    ExpandableGroupsListAdapter listAdapter;
    PrepareListGroupsAndUsers prepare;
    ExpandableListView expListView;
    Resources res;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.groups_view);

        prepare = new PrepareListGroupsAndUsers(getApplicationContext());

        initList();
        listAdapter.notifyDataSetChanged();
        initUIElements();
        initOnClickListeners();
    }

    protected void onResume(){
        super.onResume();
        initList();
        listAdapter.notifyDataSetChanged();
    }

    protected void onRestart(){
        super.onRestart();
        initList();
        listAdapter.notifyDataSetChanged();
    }

    public static GroupsView getInstance(){
        return instance;
    }

    private void initUIElements(){
        res = getResources();
        addGroup = (ImageButton) findViewById(R.id.add_groups_button);
        addMember = (ImageButton) findViewById(R.id.add_memeber_button);
        remove = (ImageButton) findViewById(R.id.delete_button);
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);

    }

    private void initList(){

        //prepTask = PrepareListOfTask.getInstance(this);
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);
        listAdapter = prepare.getPreparedAdapter();

        expListView.setAdapter(listAdapter);
        for (int i = 0;  i< listAdapter.getGroupCount(); i++){
            expListView.expandGroup(i);
        }
        /*if (listAdapter.canExpandFirstGroup()){
            expListView.expandGroup(0); //mozna rozwijac tylko wtedy kiedy lista nie jest pusta
        }*/

    }

    private void initOnClickListeners() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.add_groups_button:
                        addGroup();
                        //pierwszy przycisk po lewej
                        break;
                    case R.id.add_memeber_button:
                        addUserToGroup();
                        break;
                }
            }
        };

        addGroup.setOnClickListener(onClickListener);
        addMember.setOnClickListener(onClickListener);

        registerForContextMenu(remove);
    }
    public static void refresh() {
        if (instance != null) {
            instance.initList();
            instance.listAdapter.notifyDataSetChanged();
        }

    }

    public void addGroup(){
        Intent addGroup = new Intent(getApplicationContext(), AddGroup.class);
        startActivity(addGroup);
    }

    public void addUserToGroup(){

    }

    @Override
    public void onCreateContextMenu(android.view.ContextMenu menu, View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, v.getId(), 0, res.getString(R.string.remove_groups));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(res.getString(R.string.remove_groups))){
            function2(item.getItemId());
        }

        else {return false;}
        return true;
    }

    public void function2(int id){
        Toast.makeText(this, res.getString(R.string.removed_all_groups), Toast.LENGTH_SHORT).show();
        DbAdapter db = DbAdapter.getInstance(getApplicationContext());
        db.deleteAllGroups();
        db.deleteAllMembers();

        DeleteAllMyGroups deleteMySQL = new DeleteAllMyGroups(getApplicationContext());
        deleteMySQL.execute();
        onRestart();

    }
}
