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
import PreparingViewsAdapter.ExpandableExecutorsListAdapter;
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
        //members = db.getAllMembers();
        Cursor groupsWhereUserIs = db.getAllMembersByUserId(CurrentCreatingUser.getInstance().getLogin());

        if (groupsWhereUserIs != null && groupsWhereUserIs.moveToFirst()){
            do{
                long group_id = groupsWhereUserIs.getLong(0);

                Cursor groups = db.getGroupById(group_id);
                if (groups != null && groups.moveToFirst()){
                    String group_name = groups.getString(1);
                    Group newGroup = new Group(group_id, group_name);

                    Cursor groupMembers = db.getAllMembersByGroupId(group_id);

                    if (groupMembers != null && groupMembers.moveToFirst()){
                        do{
                            long id_group = groupMembers.getLong(0);
                            String id_user = groupMembers.getString(1);
                            String user_name = groupMembers.getString(2);

                            MemberOfGroup member = new MemberOfGroup(id_user, id_group, user_name);
                            usersList.add(member);

                        }while (groupMembers.moveToNext());
                        membersOfGroups.put(newGroup, usersList);
                        usersList = new ArrayList<>();
                    }

                }

            }while (groupsWhereUserIs.moveToNext());
        }


        /*if(members != null && members.moveToFirst()) {
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
        }*/


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


    public ExpandableExecutorsListAdapter getPreparedExecutorsAdapter(){
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
        return new ExpandableExecutorsListAdapter(context, membersOfGroups);
    }



}
