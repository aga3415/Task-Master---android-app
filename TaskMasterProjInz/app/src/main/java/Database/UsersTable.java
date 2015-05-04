package Database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Agnieszka on 2015-05-02.
 */
public class UsersTable extends Table {

    private Column id, login, password, email, verify_email, session_id;
    /*private int id;
    private String login;
    private String password;
    private String email;
    private String verify_mail;
    private int session_id;
    */

    public UsersTable(SQLiteDatabase db) {
        super(db);
    }

    public void setAllInfoAboutTable(){
        this.nameOfTable = "USERS";
        id = new Column ("ID", "INTEGER PRIMARY KEY AUTOINCREMENT", 0);
        login = new Column("LOGIN", "TEXT NOT NULL", 1);
        password = new Column("PASSWORD", "TEXT", 2);
        email = new Column("EMAIL", "TEXT NOT NULL", 3);
        verify_email = new Column("VERIFY_EMAIL", "TEXT", 4);
        session_id = new Column ("SESSION_ID", "INTEGER", 5);

        listOfColumns.add(id);
        listOfColumns.add(login);
        listOfColumns.add(password);
        listOfColumns.add(email);
        listOfColumns.add(verify_email);
        listOfColumns.add(session_id);

    }



}
