package DataModel;

/**
 * Created by Agnieszka on 2015-04-28.
 */
public class User {

    private int id;
    private String login;
    private String password;
    private String email;
    private String verify_mail;
    private int session_id;

    private boolean use_application;

    public User(int id, String login, String password, String email,
                String verify_mail, int session_id){
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.verify_mail = verify_mail;
        this.session_id = session_id;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public void setVerify_mail(String verify_mail) {
        this.verify_mail = verify_mail;
    }

    public int getId() {
        return id;
    }

    public int getSession_id() {
        return session_id;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getVerify_mail() {
        return verify_mail;
    }
}
