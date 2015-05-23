package PreparingViewsAdapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Agnieszka on 2015-05-24.
 */
public class UsersListAdapter extends ArrayAdapter{

    HashMap<String, Integer> users = new HashMap<String, Integer>();

    public UsersListAdapter(Context context, int textViewResourceId,
                              List<String> usersName) {
        super(context, textViewResourceId, usersName);
        for (int i = 0; i < usersName.size(); ++i) {
            users.put(usersName.get(i), i);
        }
    }

    @Override
    public long getItemId(int position) {
        String item = (String) getItem(position);
        return users.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}


