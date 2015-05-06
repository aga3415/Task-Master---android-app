package al.taskmasterprojinz;

/**
 * Created by Agnieszka on 2015-05-04.
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import DataModel.MyDate;
import DataModel.Task;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader; // tytuly naglowkow

    private HashMap<String, List<Task>> listDataChild;
    //format: naglowek listy, wszystkie elementy listy,

    public ExpandableListAdapter(Context context,HashMap<String, List<Task>> listChildData) {
        this.context = context;
        this.listDataHeader = new ArrayList<>();
        listDataHeader.add("Dzisiaj");
        listDataHeader.add("Jutro");
        listDataHeader.add("Kiedyś");
        this.listDataChild = listChildData;


        for (int i=0; i< listDataHeader.size(); i++){
            if(listChildData.containsKey(listDataHeader.get(i))== false || listChildData.get(listDataHeader.get(i)).size() == 0){
                listDataHeader.remove(i);
                i = i-1;
            }
        }

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon).getDescription();
    }

    public boolean isComplited(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon).isComplited();
    }

    public boolean isLate(int groupPosition, int childPosititon) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD");
        String currentDate = dateFormat.format(new Date()).toString();
        MyDate currentDateDateClass = new MyDate(currentDate);
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon).isLate(currentDateDateClass);
    }


        @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.task_on_list, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.task_text);

        txtListChild.setText(childText);
        



        //-----------------przekreslanie i odkreslanie taskow wykonanych----------------------------
        if(isComplited(groupPosition,childPosition)) {
            txtListChild.setPaintFlags(txtListChild.getPaintFlags() |
                            Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            txtListChild.setPaintFlags(txtListChild.getPaintFlags() &
                            ~Paint.STRIKE_THRU_TEXT_FLAG);
        }
        //------------------------------------------------------------------------------------------

        //-------czerwone spoznione taski-----------------------------------------------------------
        if (isLate(groupPosition,childPosition)){
            txtListChild.setTextColor(Color.RED);
        }else{
            txtListChild.setTextColor(Color.BLACK);
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
}

