package PreparingData;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DataModel.User;
import PreparingViewsAdapter.ExpandableGroupsListAdapter;

/**
 * Created by Agnieszka on 2015-05-20.
 */
public class PrepareListGroupsAndUsers {

    Context context;
    public PrepareListGroupsAndUsers(Context context){
        this.context = context;
    }
    public  ExpandableGroupsListAdapter getPreparedAdapter(){
        String[] header = {""};
        List<User> usersList = new ArrayList<User>();
        HashMap<String, List<User>> membersOfGroups = new HashMap<>();
        return new ExpandableGroupsListAdapter(context, header, membersOfGroups);
    }
}
