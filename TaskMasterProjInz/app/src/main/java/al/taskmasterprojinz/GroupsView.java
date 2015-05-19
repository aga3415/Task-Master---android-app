package al.taskmasterprojinz;

import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

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

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.groups_view);

        prepare = new PrepareListGroupsAndUsers(getApplicationContext());

        initList();
        listAdapter.notifyDataSetChanged();
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
        /*if (listAdapter.canExpandFirstGroup()){
            expListView.expandGroup(0); //mozna rozwijac tylko wtedy kiedy lista nie jest pusta
        }*/

    }
    public static void refresh() {
        if (instance != null) {
            instance.initList();
            instance.listAdapter.notifyDataSetChanged();
        }

    }
}
