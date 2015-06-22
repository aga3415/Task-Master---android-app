package PreparingData;

import DataModel.User;

/**
 * Created by Agnieszka on 2015-06-18.
 */
public class CurrentCreatingUser extends User {

    static CurrentCreatingUser instance;

    public CurrentCreatingUser(int id, String login, String password, String email, String verify_mail, int session_id) {
        super(id, login, password, email, verify_mail, session_id);
        instance = this;
    }

    public CurrentCreatingUser(String login, String password, String email){
        super(login, password, email);
        instance = this;
    }

    private CurrentCreatingUser(){
        super();
    }

    public static CurrentCreatingUser getInstance(){
        return instance;
    }

    public static void clearUser(){
        instance = new CurrentCreatingUser();
    }
}
