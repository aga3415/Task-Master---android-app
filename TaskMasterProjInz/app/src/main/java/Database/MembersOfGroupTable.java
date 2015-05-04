package Database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Agnieszka on 2015-05-02.
 */
public class MembersOfGroupTable extends Table {

    Column id_group, id_user;

    public MembersOfGroupTable(SQLiteDatabase db) {
        super(db);
    }

    public void setAllInfoAboutTable(){
        this.nameOfTable = "MEMBERSOFGROUPS";
        id_group = new Column ("ID_GROUP", "INTEGER REFERENCES GROUPS(ID) ON DELETE CASCADE", 0);
        id_user = new Column("ID_USER", "INTEGER REFERENCES USERS(ID) ON DELETE CASCADE", 1);

        listOfColumns.add(id_group);
        listOfColumns.add(id_user);

    }

}
