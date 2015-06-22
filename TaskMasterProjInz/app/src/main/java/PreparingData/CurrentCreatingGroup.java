package PreparingData;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DataModel.Group;
import DataModel.MemberOfGroup;
import Database.DbAdapter;
import MySQLConnection.AddNewGroup;
import MySQLConnection.DeleteGroup;

/**
 * Created by Agnieszka on 2015-05-26.
 */
public class CurrentCreatingGroup {
    public static HashMap<String, String> users; //name, email
    public static List<String> usersName;
    public static String nameOfGroup;
    static CurrentCreatingGroup instance;
    public static Group oldGroup;
    public static List<MemberOfGroup> oldGroupMemberlist;
    Context context;
    DbAdapter db;

    private CurrentCreatingGroup(Context context){
        users = new HashMap<String,String>();
        usersName = new ArrayList<String>();
        instance = this;
        this.context = context;
        db = DbAdapter.getInstance(context);

    }
    public static CurrentCreatingGroup getInstance(Context context){
        return (instance == null) ? (new CurrentCreatingGroup(context)) : instance;
    }

    public static void clearCreatingGroup(){
        users.clear();
        usersName.clear();
        nameOfGroup = "";
        oldGroup = null;
        oldGroupMemberlist = new ArrayList<>();
    }

    public static boolean isEmailExistOnList(String email){
        return users.containsValue(email);
    }

    public static boolean isNameEistsOnList(String name){
        return users.containsKey(name);
    }

    public void saveCreatedGroup(){

        AddNewGroup addNewGroup = new AddNewGroup(context);
        addNewGroup.execute();

        long id_group = db.insertGroup(nameOfGroup);
        //long id_group = db.getGroupIdByName(nameOfGroup);
        for (String names : usersName){
            MemberOfGroup newMember = new MemberOfGroup(users.get((String) names), id_group, names);
            db.insertMemberOfGroup(newMember);
        }

    }

    public boolean isUniqueNameOfGroup(String name){
        return db.ifExistGroupWithName(name);
    }

    public void getListOfUsers(List<MemberOfGroup> list) {
        oldGroupMemberlist = list;
        for (MemberOfGroup member : list) {
            users.put(member.getName(), member.getId_user());
            usersName.add(member.getName());
        }
    }

    public void deleteOldGroup(){

        db.deleteGroup(oldGroup);
        for (MemberOfGroup member : oldGroupMemberlist){
            db.deleteMemberOfGroup(member);
        }
        DeleteGroup delete = new DeleteGroup(context);
        delete.execute(oldGroup);


    }
}
