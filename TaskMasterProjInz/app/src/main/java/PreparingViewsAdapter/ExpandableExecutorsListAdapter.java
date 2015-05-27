package PreparingViewsAdapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.provider.MediaStore;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DataModel.Group;
import DataModel.MemberOfGroup;
import Database.DbAdapter;
import PreparingData.CurrentCreatingTask;
import al.taskmasterprojinz.R;

/**
 * Created by Agnieszka on 2015-05-27.
 */

public class ExpandableExecutorsListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Resources res;
    private List<Group> listDataHeader; // grupy jako cale klasy
    private HashMap<Group, List<MemberOfGroup>> listChildData;
    private ExpandableExecutorsListAdapter instance;
    private HashMap<Pair<Integer, Integer>, RadioButton> listOfChildRadioButton;
    private HashMap<Integer, RadioButton> listOfGroupRadioButton;
    private CurrentCreatingTask newTask;



    //public HashMap<String, List<User>> listDataChild;
    //format: naglowek listy, wszystkie elementy listy,

    public ExpandableExecutorsListAdapter(Context context, HashMap<Group, List<MemberOfGroup>> listChildData) {
        this.context = context;
        res = context.getResources();
        instance = this;
        this.listDataHeader = new ArrayList<Group>();
        for (Group g : listChildData.keySet()){
            listDataHeader.add(g);
        }
        this.listChildData = listChildData;
        listOfChildRadioButton = new HashMap<Pair<Integer, Integer>, RadioButton>();
        listOfGroupRadioButton = new HashMap<Integer, RadioButton>();
        newTask = CurrentCreatingTask.getInstance();

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
            convertView = infalInflater.inflate(R.layout.user_on_checkbox_list, null);
        }

        convertView.setFocusable(false);
        convertView.setClickable(false);

        final RadioButton txtListChild = (RadioButton) convertView.findViewById(R.id.checkBox);
        txtListChild.setText(childText);
        txtListChild.setFocusable(false);
        txtListChild.setClickable(false);

        listOfChildRadioButton.put(new Pair(groupPosition,childPosition), txtListChild);

        /*txtListChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (RadioButton r : listOfChildRadioButton.values()) {
                    r.setChecked(false);
                }
                for (RadioButton r : listOfGroupRadioButton.values()) {
                    r.setChecked(false);
                }
                listOfChildRadioButton.get(new Pair<Integer, Integer>(groupPosition, childPosition)).setChecked(true);
            }

        });

        txtListChild.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (RadioButton r : listOfChildRadioButton.values()) {
                    r.setChecked(false);
                }
                for (RadioButton r : listOfGroupRadioButton.values()) {
                    r.setChecked(false);
                }
                listOfChildRadioButton.get(new Pair<Integer, Integer>(groupPosition, childPosition)).setChecked(isChecked);
            }

        });



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

        /*ImageButton delete = (ImageButton) convertView.findViewById((R.id.delete_button));
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
        });*/

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
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition).getName();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_on_checkbox_list, null);
        }

        convertView.setFocusable(false);
        convertView.setClickable(false);


        RadioButton lblListHeader = (RadioButton) convertView.findViewById(R.id.checkBox);
        lblListHeader.setFocusable(false);
        lblListHeader.setClickable(false);
        listOfGroupRadioButton.put(groupPosition, lblListHeader);

        final TextView groupName = (TextView) convertView.findViewById(R.id.group_txt);
        groupName.setText(headerTitle);
        //lblListHeader.setTypeface(null, Typeface.BOLD);
        //lblListHeader.setText(headerTitle);


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

    public ExpandableListView.OnChildClickListener onChildClickListener(){
        return new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                RadioButton rb = listOfChildRadioButton.get(new Pair<Integer, Integer>(groupPosition, childPosition));
                boolean isChecked = rb.isChecked();
                for (RadioButton r : listOfChildRadioButton.values()) {
                    r.setChecked(false);
                }
                for (RadioButton r : listOfGroupRadioButton.values()) {
                    r.setChecked(false);
                }
                rb.setChecked(!isChecked);
                if (rb.isChecked()){
                    newTask.setId_executor(getChild(groupPosition,childPosition).getId_user());
                }

                return false;
            }
        };
    }

    public ExpandableListView.OnGroupClickListener onGroupClickListener(){
        return new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                RadioButton rb = listOfGroupRadioButton.get(groupPosition);
                boolean isChecked = rb.isChecked();
                for (RadioButton r : listOfChildRadioButton.values()) {
                    r.setChecked(false);
                }
                for (RadioButton r : listOfGroupRadioButton.values()) {
                    r.setChecked(false);
                }
                rb.setChecked(!isChecked);
                if (rb.isChecked()){
                    newTask.setId_group(getGroup(groupPosition).getId());
                }
                return false;
            }
        };
    }
}
