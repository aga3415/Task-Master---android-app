package Database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Agnieszka on 2015-05-02.
 */
public class GroupsTable extends Table {

    Column id, name;
    public GroupsTable(SQLiteDatabase db) {
        super(db);
    }

    public void setAllInfoAboutTable(){
        this.nameOfTable = "GROUPS";
        id = new Column ("ID", "INTEGER PRIMARY KEY AUTOINCREMENT", 0);
        name = new Column("NAME", "TEXT NOT NULL", 1);

        listOfColumns.add(id);
        listOfColumns.add(name);

    }
}
