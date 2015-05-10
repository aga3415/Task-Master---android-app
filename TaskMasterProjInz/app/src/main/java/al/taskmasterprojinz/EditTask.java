package al.taskmasterprojinz;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import DataModel.MyDate;

/**
 * Created by Agnieszka on 2015-05-10.
 */
public class EditTask extends CreateTask {

    TextView header;
    Resources res;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        res = getApplicationContext().getResources();
        header = (TextView) findViewById(R.id.create_label);

        header.setText(res.getString(R.string.edit_task_label));

    }

    public void addTaskListener(){
        String desc = description.getText().toString();

        if(desc.equals("")){
            description.setError("Nie trzeba planować lenistwa, wpisz coś ;)");
        } else{
            newTask.setDate_update(MyDate.getTodayDate());
            newTask.setDescription(desc);
            newTask.updateTask(getApplicationContext());
            //db.insert(newTask);
            //newTask.cancelTask();
            setCurrentTxt();
            Toast.makeText(getApplicationContext(), res.getString(R.string.edit_task_txt), Toast.LENGTH_SHORT).show();
        }
        finishActivity(0);

    }

}
