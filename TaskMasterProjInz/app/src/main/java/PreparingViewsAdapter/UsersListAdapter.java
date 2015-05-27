package PreparingViewsAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import DataModel.User;
import PreparingData.CurrentCreatingGroup;
import al.taskmasterprojinz.AddGroup;
import al.taskmasterprojinz.R;

/**
 * Created by Agnieszka on 2015-05-24.
 */
public class UsersListAdapter extends ArrayAdapter{

    HashMap<String, Integer> users = new HashMap<String, Integer>();
    AddGroup group;
    Context context;
    private static class ViewHolder {
        TextView name;
        ImageButton delete;
        //TextView home;
    }

    public UsersListAdapter(Context context, AddGroup group) {
        super(context, R.layout.users_on_list, CurrentCreatingGroup.getInstance(context).usersName);
        /*for (int i = 0; i < usersName.size(); ++i) {
            users.put(usersName.get(i), i);
        }*/
        this.group = group;
        this.context = context;
    }

    /*@Override
    public long getItemId(int position) {
        String item = (String) getItem(position);
        return users.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }*/

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        //User user = getItem(position);
        String name = (String) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.users_on_list, parent, false);
        }
        // Lookup view for data population
        //TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvHome = (TextView) convertView.findViewById(R.id.user_text);
        // Populate the data into the template view using the data object
        tvHome.setText(name);
        //tvHome.setText(user.hometown);
        // Return the completed view to render on screen
        return convertView;
    }*/

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String user = (String) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.users_on_list, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.user_text);
            viewHolder.delete = (ImageButton) convertView.findViewById(R.id.delete_user_button);
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CurrentCreatingGroup.getInstance(context).users.remove((String) getItem(position));
                    CurrentCreatingGroup.getInstance(context).usersName.remove(position);
                    group.refreshListOfUsers();
                }
            });
            //viewHolder.home = (TextView) convertView.findViewById(R.id.tvHome);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.name.setText(user);
        //viewHolder.home.setText(user.hometown);
        // Return the completed view to render on screen
        return convertView;
    }

}


