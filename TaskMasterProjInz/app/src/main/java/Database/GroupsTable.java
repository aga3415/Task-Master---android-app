package Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import DataModel.Group;
import DataModel.MyDate;

/**
 * Created by Agnieszka on 2015-05-02.
 */
public class GroupsTable extends Table {

    Column id, name;
    public GroupsTable(SQLiteDatabase db) {
        super(db);
    }
    public GroupsTable(){
        super();
    }

    public void setAllInfoAboutTable(){
        this.nameOfTable = "GROUPS";
        id = new Column ("ID", "INTEGER PRIMARY KEY AUTOINCREMENT", 0);
        name = new Column("NAME", "VARCHAR(64) NOT NULL", 1);

        listOfColumns.add(id);
        listOfColumns.add(name);

    }

    public long insert(SQLiteDatabase db, String name){
        ContentValues newGroup = new ContentValues();
        newGroup.put(listOfColumns.get(1).name, name);
        return db.insert(nameOfTable, null, newGroup);
    }

    public void insert(SQLiteDatabase db, Group group){
        ContentValues newGroup = new ContentValues();
        newGroup.put(listOfColumns.get(0).name, group.getId());
        newGroup.put(listOfColumns.get(1).name, group.getName());
        db.insert(nameOfTable, null, newGroup);
    }

    public void delete(SQLiteDatabase db, Group group){
        String where = listOfColumns.get(0).name + "=" + group.getId();
        db.delete(nameOfTable, where, null);
    }

    public void updateIdGroup(SQLiteDatabase db, long id, String name){
        String where = listOfColumns.get(1).name + " = '" + name + "'";
        db.delete(nameOfTable, where, null);
        ContentValues newGroup = new ContentValues();
        newGroup.put(listOfColumns.get(0).name, id);
        newGroup.put(listOfColumns.get(1).name, name);
        db.insert(nameOfTable, null, newGroup);
    }

    public void deleteAllGroups(SQLiteDatabase db){
        if (db == null) { System.out.println("Nullem jest : db");}
        if ( nameOfTable == null) { System.out.println("Nullem jest: nameOfTable");}
        System.out.println("Usuwam wszystkie grupy");
        db.delete(nameOfTable, null, null);
    }

    public Cursor getGroupById(SQLiteDatabase db, long id){
        List<String> colNames = new ArrayList<String>();

        String where = this.id.name + " = " + id;

        for (Column c : listOfColumns){
            colNames.add(c.name);
        }

        String[] columns = colNames.toArray(new String [colNames.size()]);
        return db.query(nameOfTable, columns, where, null, null, null, null);
    }

    public Cursor getGroupByName(SQLiteDatabase db, String nameOfGroup) {
        List<String> colNames = new ArrayList<String>();

        String where = name.name + " = '" + nameOfGroup + "'";

        for (Column c : listOfColumns){
            colNames.add(c.name);
        }

        String[] columns = colNames.toArray(new String [colNames.size()]);
        return db.query(nameOfTable, columns, where, null, null, null, null);
        //public Cursor query (String table, String[] columns, String selection,
        // String[] selectionArgs, String groupBy, String having, String orderBy)
    }

    public boolean ifExistGroupWithName(SQLiteDatabase db, String nameOfGroup){
        return getGroupByName(db, nameOfGroup).getCount() <= 0;
    }

    /*public long selectGroupIdByName(SQLiteDatabase db, String name){
        Cursor group = getGroupByName(db, name);
        if (group.getCount() > 0) return group.getLong(0);
        System.out.println("NIE MA TAKIEJ GRUPY");
        return 0;
    }*/
}
