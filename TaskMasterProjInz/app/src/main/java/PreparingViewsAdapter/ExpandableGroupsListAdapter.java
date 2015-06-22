package PreparingViewsAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DataModel.Group;
import DataModel.MemberOfGroup;
import Database.DbAdapter;
import PreparingData.CurrentCreatingGroup;
import al.taskmasterprojinz.AddGroup;
import al.taskmasterprojinz.EditGroup;
import al.taskmasterprojinz.R;

/**
 * Created by Agnieszka on 2015-05-19.
 */
public class ExpandableGroupsListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Resources res;
    private List<Group> listDataHeader; // grupy jako cale klasy
    private HashMap<Group, List<MemberOfGroup>> listChildData;
    private ExpandableGroupsListAdapter instance;



    //public HashMap<String, List<User>> listDataChild;
    //format: naglowek listy, wszystkie elementy listy,

    public ExpandableGroupsListAdapter(Context context, HashMap<Group, List<MemberOfGroup>> listChildData) {
        this.context = context;
        res = context.getResources();
        instance = this;
        this.listDataHeader = new ArrayList<Group>();
        for (Group g : listChildData.keySet()){
            listDataHeader.add(g);
        }
        this.listChildData = listChildData;

    }

    @Override
    public MemberOfGroup getChild(int groupPosition, int childPosititon) {
        return this.listChildData.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon);
    }
    /*public MemberOfGroup getMember(int groupPosition, int childPosititon) {
        return this.listChildData.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon);
    }*/



    /*public long getId(int groupPosition, int childPosititon) {
        return this.listChildData.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon).getId_group();
    }*/




    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition).getName();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.member_on_list, null);
        }

        final TextView txtListChild = (TextView) convertView.findViewById(R.id.users_name);
        txtListChild.setText(childText);


        ImageButton delete = (ImageButton) convertView.findViewById((R.id.delete_button));
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbAdapter db = DbAdapter.getInstance(context);
                MemberOfGroup removingUser = getChild(groupPosition, childPosition);
                //String removingGroupName = (String) getGroup(groupPosition);

                db.deleteMemberOfGroup(removingUser);

                listChildData.get(getGroup(groupPosition)).remove(removingUser);
                instance.notifyDataSetChanged();

            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listChildData.get(this.listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Group getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition).getName();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_header, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.task_list_header_title);
        ImageButton edit = (ImageButton) convertView.findViewById(R.id.edit_task_button);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentCreatingGroup.getInstance(context).clearCreatingGroup();
                CurrentCreatingGroup.getInstance(context).nameOfGroup = getGroup(groupPosition).getName();
                CurrentCreatingGroup.getInstance(context).oldGroup = getGroup(groupPosition);
                CurrentCreatingGroup.getInstance(context).getListOfUsers(listChildData.get(getGroup(groupPosition)));

                Intent editGroup = new Intent(context, EditGroup.class);
                editGroup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(editGroup);
            }
        });
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public boolean canExpandFirstGroup(){
        return !(getChildrenCount(0) == 0);
    }
}
