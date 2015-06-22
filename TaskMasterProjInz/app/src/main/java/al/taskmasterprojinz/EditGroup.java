package al.taskmasterprojinz;

import android.os.Bundle;
import android.widget.TextView;

import PreparingData.CurrentCreatingGroup;

/**
 * Created by Agnieszka on 2015-06-20.
 */
public class EditGroup extends AddGroup {

    String oldGroupName;


    public void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setElem();

    }

    public void setElem(){
        TextView label = (TextView) findViewById(R.id.create_label);
        label.setText(res.getString(R.string.edit_group_label));
        group_name.setText(group.nameOfGroup);
        oldGroupName = group.nameOfGroup;

    }

    @Override
    public void confirmAddingGroup() {
        CurrentCreatingGroup.getInstance(getApplicationContext()).deleteOldGroup();
        super.confirmAddingGroup();

    }
}
