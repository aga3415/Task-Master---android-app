package PreparingData;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DataModel.Group;
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
        List<MemberOfGroup> usersList = new ArrayList<MemberOfGroup>();
        HashMap<Group, List<MemberOfGroup>> membersOfGroups = new HashMap<>();

        Cursor members;
        DbAdapter db = DbAdapter.getInstance(context);

        //------------------
        members = db.getAllMembers();

        if(members != null && members.moveToFirst()) {
            do {
                long id_group = members.getLong(0);
                String id_user = members.getString(1);
                String user_name = members.getString(2);

                MemberOfGroup member = new MemberOfGroup(id_user, id_group, user_name);

                //usersList.add(member);
                Cursor groupCursor = db.getGroupById(id_group);
                groupCursor.moveToFirst();
                Group groupModel = new Group(groupCursor.getLong(0), groupCursor.getString(1));

                //ten kawalek kodu się nie wykonuje
                if (membersOfGroups.containsKey(groupModel)){
                    ArrayList<MemberOfGroup> pom = (ArrayList<MemberOfGroup>) membersOfGroups.get(groupModel);
                    pom.add(member);
                    membersOfGroups.remove(groupModel);
                    membersOfGroups.put(groupModel, pom);
                }else{
                    ArrayList<MemberOfGroup> mog = new ArrayList<MemberOfGroup>();
                    mog.add(member);
                    membersOfGroups.put(groupModel, mog);

                    //membersOfGroups.get(groupModel) = membersOfGroups.get(groupModel).add(member);

                }

            } while(members.moveToNext());
        }


        /* Sprawdzenie poprawnosci przekazywanych danych, jest ok!
        for (Group g : membersOfGroups.keySet()){
            System.out.println("GRUPA" + g.getName());
            for (MemberOfGroup m : membersOfGroups.get(g)){
                System.out.print(": "+ m.getName() + ", ");
            }
            System.out.println("");
        }*/

        //------------------------------
        return new ExpandableGroupsListAdapter(context, membersOfGroups);
    }


}
