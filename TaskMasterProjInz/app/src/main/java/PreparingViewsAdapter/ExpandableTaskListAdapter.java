package PreparingViewsAdapter;

/**
 * Created by Agnieszka on 2015-05-04.
 */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

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
import android.widget.ImageButton;
import android.widget.TextView;

import DataModel.Group;
import DataModel.MemberOfGroup;
import DataModel.MyDate;
import DataModel.Task;
import Database.DbAdapter;
import PreparingData.CurrentCreatingTask;
import al.taskmasterprojinz.EditTask;
import al.taskmasterprojinz.R;
import android.view.View.OnClickListener;

public class ExpandableTaskListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Resources res;
    private Calendar calendar;
    private List<String> listDataHeader; // tytuly naglowkow
    private ExpandableTaskListAdapter instance, instance2;

    private MyDate date;

    public HashMap<String, List<Task>> listDataChild;
    //format: naglowek listy, wszystkie elementy listy,

    public ExpandableTaskListAdapter(Context context, HashMap<MemberOfGroup, List<Task>> listOfTasks, boolean isUser){
        this.context = context;
        instance = this;
        this.listDataHeader = new ArrayList<>();
        this.listDataChild = new HashMap<String, List<Task>>();

        for (MemberOfGroup m : listOfTasks.keySet()){
            listDataHeader.add(m.getName());
            listDataChild.put(m.getName(), listOfTasks.get(m));
        }

        date = MyDate.getTodayDate();


    }

    public ExpandableTaskListAdapter(Context context, HashMap<Group, List<Task>> listOfTasks){
        this.context = context;
        instance = this;
        this.listDataHeader = new ArrayList<>();
        this.listDataChild = new HashMap<String, List<Task>>();

        for (Group g : listOfTasks.keySet()){
            listDataHeader.add(g.getName());
            listDataChild.put(g.getName(), listOfTasks.get(g));
        }

        date = MyDate.getTodayDate();


    }



    public ExpandableTaskListAdapter(Context context, String [] headers, HashMap<String, List<Task>> listChildData) {
        this.context = context;
        res = context.getResources();
        instance2 = this;
        this.listDataHeader = new ArrayList<>();
        //listDataHeader = headers;
        //String [] headers = res.getStringArray(R.array.task_headers);
        for (String h : headers){
            listDataHeader.add(h);
        }

        this.listDataChild = listChildData;

        // usuwanie tych nagłówków dla których nie ma tasków----------------------------------------
        if (listDataChild.isEmpty()) {
            listDataHeader = new ArrayList<>();
        }else{
            for (int i=0; i< listDataHeader.size(); i++){
                if(listChildData.get(listDataHeader.get(i)) == null){
                    listDataHeader.remove(i);
                    i = i-1;
                }
            }
        }
        //------------------------------------------------------------------------------------------

        date = MyDate.getTodayDate(); //bedzie potrzebne do oznaczania taskow jako skonczone

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon).getDescription();
    }
    public Task getTask(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    public boolean isCompleted(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon).isComplited();
    }

    public long getId(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon).getId();
    }

    public boolean isLate(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon).isLate(date);
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
            convertView = infalInflater.inflate(R.layout.task_on_list, null);
        }

        final TextView txtListChild = (TextView) convertView
                .findViewById(R.id.task_text);
        txtListChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = getTask(groupPosition,childPosition);
                if (task.getDate_exec().isEmpty()) {
                    task.setDate_exec(date);
                    task.setDate_update(date);
                }else{
                    task.setDate_exec(new MyDate());
                    task.setDate_update(date);
                }
                DbAdapter db = DbAdapter.getInstance(context);
                db.update(task);
                /*if (instance != null) instance.*/notifyDataSetChanged();
                //if(instance2 != null) instance2.notifyDataSetChanged();

            }
        });
        ImageButton edit = (ImageButton) convertView.findViewById(R.id.edit_task_button);
        ImageButton delete = (ImageButton) convertView.findViewById((R.id.delete_task_button));
        edit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //przejscie do aktywnosci z edycja taska
                Intent editTask = new Intent(context, EditTask.class);
                CurrentCreatingTask editingTask = CurrentCreatingTask.getInstance();
                editingTask.taskCastToCurrentCreatingTask(getTask(groupPosition,childPosition));
                ((Activity) context).startActivity(editTask);
            }
        });

        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DbAdapter db = DbAdapter.getInstance(context);
                Task toDelete = getTask(groupPosition,childPosition);
                db.deleteTask(toDelete.getId());
                //Toast.makeText(context, "Usunięto zadanie", Toast.LENGTH_LONG);
                listDataChild.get(getGroup(groupPosition)).remove(toDelete);
                /*if (instance != null) instance.*/notifyDataSetChanged();
                //if(instance2 != null) instance2.notifyDataSetChanged();

            }
        });

        txtListChild.setText(childText);
        txtListChild.setTextColor(Color.BLACK);

        //-----------------przekreslanie i odkreslanie taskow wykonanych----------------------------
        if(isCompleted(groupPosition, childPosition)) {
            txtListChild.setPaintFlags(txtListChild.getPaintFlags() |
                            Paint.STRIKE_THRU_TEXT_FLAG);
            txtListChild.setTextColor(Color.GRAY);
            //edit.setVisibility(View.INVISIBLE);
        } else {
            txtListChild.setPaintFlags(txtListChild.getPaintFlags() &
                            ~Paint.STRIKE_THRU_TEXT_FLAG);
            //edit.setVisibility(View.VISIBLE);
        }
        //------------------------------------------------------------------------------------------

        //-------czerwone spoznione taski-----------------------------------------------------------
        if (isLate(groupPosition,childPosition) && !isCompleted(groupPosition, childPosition)){
            txtListChild.setTextColor(Color.RED);
        }
        //------------------------------------------------------------------------------------------

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
        if (listDataHeader.isEmpty()) return false;
        return !(getChildrenCount(0) == 0);
    }
}

