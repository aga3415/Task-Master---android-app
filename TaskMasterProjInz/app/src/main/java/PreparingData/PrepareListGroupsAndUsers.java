package PreparingData;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DataModel.MemberOfGroup;
import DataModel.MyDate;
import DataModel.Task;
import DataModel.User;
import Database.DbAdapter;
import PreparingViewsAdapter.ExpandableGroupsListAdapter;
import PreparingViewsAdapter.ExpandableTaskListAdapter;

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

        Cursor members;
        DbAdapter db = DbAdapter.getInstance(context);



        //------------------
        members = db.getAllMembers();



        if(members != null && members.moveToFirst()) {
            do {
                int id_group = members.getInt(0);
                int id_user = members.getInt(1);

                MemberOfGroup member = new MemberOfGroup(id_user, id_group);


            } while(members.moveToNext());
        }

        //todayTomorrowFutureList.put(standardHeaders[0], tasksForToday);
        //todayTomorrowFutureList.put(standardHeaders[1], taskForTomorrow);
        //todayTomorrowFutureList.put(standardHeaders[2], restOfTask);

        //return new ExpandableTaskListAdapter(context, standardHeaders, todayTomorrowFutureList);

        //------------------------------
        return new ExpandableGroupsListAdapter(context, header, membersOfGroups);
    }


}
