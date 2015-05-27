package al.taskmasterprojinz;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import PreparingData.CurrentCreatingGroup;
import PreparingViewsAdapter.UsersListAdapter;


public class AddGroup extends Activity {

    ImageButton cancel, save, contacts, add_user;
    EditText group_name, user_name, user_email;
    TextView title_users_list;
    ListView listOfUsers;
    public UsersListAdapter listAdapter;


    CurrentCreatingGroup group;
    Resources res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        res = getResources();
        initUIElements();
        initOnClickListeners();
        refreshListOfUsers();


    }

    /*@Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putStringArrayList("usersName", (ArrayList<String>) usersName);
        outState.putSerializable("users", users);
        super.onSaveInstanceState(outState);
    }

   @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        ArrayList<String> items = savedInstanceState.getStringArrayList("usersName");
        HashMap<String, String> itemsMap = (HashMap<String, String>) savedInstanceState.getSerializable("users");
        users = itemsMap;
        usersName = items;
        for (String item : items) {
            usersName.add(item);
            listAdapter.notifyDataSetChanged();
            //super.onRestoreInstanceState(savedInstanceState);
        }
        for (String key : itemsMap.keySet()){
            users.put(key, itemsMap.get(key));
        }
       group_name.setText("zadzialalo odzyskiwanie");
       System.out.print("Zadziallao odzywskiwanie");
        super.onRestoreInstanceState(savedInstanceState);
    }*/

    public void initUIElements(){
        cancel = (ImageButton) findViewById(R.id.clear_task_button);
        save = (ImageButton) findViewById (R.id.save_task_button);
        group_name = (EditText) findViewById(R.id.description_edit);
        listOfUsers = (ListView) findViewById(R.id.added_users_list);
        contacts = (ImageButton) findViewById(R.id.contacts_button);
        add_user = (ImageButton) findViewById(R.id.confirm_adding);
        user_name = (EditText) findViewById(R.id.add_user_name_txt);
        user_email = (EditText) findViewById(R.id.add_user_email_txt);
        title_users_list = (TextView) findViewById(R.id.title_user_list_txt);

        group = CurrentCreatingGroup.getInstance(getApplicationContext());
        listAdapter = new UsersListAdapter(getApplicationContext(),this);

        listOfUsers.setAdapter(listAdapter);
    }

    public void initOnClickListeners(){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.save_task_button:
                        //prawy górny róg, znak +
                        confirmAddingGroup();
                        break;
                    case R.id.clear_task_button:
                        //lewy górny róg, znak -
                        cancelAddingGroup();
                        break;
                    case R.id.contacts_button:
                        //znak ksiaski adresowej
                        openContactsList();
                        break;
                    case R.id.confirm_adding:
                        //potwierdzenie dodania uzytkownika
                        addUserToGroup();
                        break;
                }
            }
        };
        cancel.setOnClickListener(onClickListener);
        save.setOnClickListener(onClickListener);
        add_user.setOnClickListener(onClickListener);
        contacts.setOnClickListener(onClickListener);
    }

    public void confirmAddingGroup(){

        /*TRZEBA JESZCZE DODAC UZYTKOWNIKA APLIKACJI JAKO DOMYSLNEGO CZLONKA GRUPY*/

        String name_of_group = group_name.getText().toString();
        if (name_of_group.equals("")){
            group_name.setError(res.getString(R.string.empty_group_name_error));
        }else if (!group.isUniqueNameOfGroup(name_of_group)){
            group_name.setError(res.getString(R.string.unique_group_name));
        }else {
            group.nameOfGroup = name_of_group;
            group.saveCreatedGroup();
            //najpierw dodanie grupy
            //potem wyczyszczenie danych
            group.clearCreatingGroup();
            user_email.setText("");
            user_name.setText("");
            group_name.setText("");
            onBackPressed();
        }



    }

    public void cancelAddingGroup(){
        group.clearCreatingGroup();
        onBackPressed();
    }

    public void addUserToGroup(){
        boolean canAddUser = true;
        if (user_name.getText().toString().equals("")){
            user_name.setError(res.getString(R.string.empty_user_name_error));
            canAddUser = false;
        }else{
            if (group.isNameEistsOnList(user_name.getText().toString())){
                user_name.setError(res.getString(R.string.unique_name_error));
                canAddUser = false;
            }
        }
        if (user_email.getText().toString().equals("")){
            user_email.setError(res.getString(R.string.empty_user_email_error));
            canAddUser = false;
        }else if (!user_email.getText().toString().contains("@")){
            user_email.setError(res.getString(R.string.invalid_user_email_error));
            canAddUser = false;
        }else if (group.isEmailExistOnList(user_email.getText().toString())){
            user_email.setError(res.getString(R.string.unique_email_error));
            canAddUser = false;
        }

        if (canAddUser){

            group.users.put(user_name.getText().toString(), user_email.getText().toString());
            group.usersName.add(user_name.getText().toString());
            refreshListOfUsers();

            user_email.setError(null);
            user_name.setError(null);
            user_email.setText("");
            user_name.setText("");

            //zastanowic sie nad sposobem przechowywania userow na listach, dwie listy, a co jesli usuniemy usera na jednej z nich?
        }


    }

    public void openContactsList(){

    }

    public void refreshListOfUsers(){
        listAdapter.notifyDataSetChanged();
        if (group.usersName.isEmpty()){
            title_users_list.setVisibility(View.GONE);
        }else{
            title_users_list.setVisibility(View.VISIBLE);
        }
    }





}