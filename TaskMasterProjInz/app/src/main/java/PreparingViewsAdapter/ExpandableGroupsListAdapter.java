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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import DataModel.MemberOfGroup;
import DataModel.MyDate;
import DataModel.Task;
import DataModel.User;
import Database.DbAdapter;
import PreparingData.CurrentCreatingTask;
import al.taskmasterprojinz.EditTask;
import al.taskmasterprojinz.R;

/**
 * Created by Agnieszka on 2015-05-19.
 */
public class ExpandableGroupsListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Resources res;
    private Calendar calendar;
    private List<String> listDataHeader; // tytuly naglowkow
    private ExpandableGroupsListAdapter instance;



    public HashMap<String, List<User>> listDataChild;
    //format: naglowek listy, wszystkie elementy listy,

    public ExpandableGroupsListAdapter(Context context, String [] headers, HashMap<String, List<User>> listChildData) {
        this.context = context;
        res = context.getResources();
        instance = this;
        this.listDataHeader = new ArrayList<>();
        //listDataHeader = headers;
        //String [] headers = res.getStringArray(R.array.task_headers);
        for (String h : headers){
            listDataHeader.add(h);
        }

        this.listDataChild = listChildData;



    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon).getLogin();
    }
    public User getMember(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon);
    }



    public long getId(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon).getId();
    }




    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.member_on_list, null);
        }

        //final TextView txtListChild = (TextView) convertView
        //        .findViewById(R.id.users_name);
        //txtListChild.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
                /*Task task = getTask(groupPosition,childPosition);
                if (task.getDate_exec().isEmpty()) {
                    task.setDate_exec(date);
                    task.setDate_update(date);
                }else{
                    task.setDate_exec(new MyDate());
                    task.setDate_update(date);
                }
                DbAdapter db = DbAdapter.getInstance(context);
                db.update(task);
                instance.notifyDataSetChanged();
*/
        //    }
        //});

        ImageButton delete = (ImageButton) convertView.findViewById((R.id.delete_button));

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbAdapter db = DbAdapter.getInstance(context);
                User removingUser = getMember(groupPosition, childPosition);
                String removingGroupName = (String) getGroup(groupPosition);

                db.deleteMemberOfGroup(removingGroupName, removingUser.getId());

                listDataChild.get(getGroup(groupPosition)).remove(removingUser);
                instance.notifyDataSetChanged();

            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
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
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.header_task_list, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.task_list_header_title);
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
