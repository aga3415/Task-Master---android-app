package Database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import DataModel.Group;

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

    public void insert(SQLiteDatabase db, String name){
        ContentValues newGroup = new ContentValues();
        newGroup.put(listOfColumns.get(1).name, name);
        db.insert(nameOfTable, null, newGroup);

    }

    public void delete(SQLiteDatabase db, Group group){
        String where = listOfColumns.get(0).name + "=" + group.getId();
        db.delete(nameOfTable, where, null);
    }
}
