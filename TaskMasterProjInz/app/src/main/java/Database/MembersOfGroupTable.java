package Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import DataModel.MemberOfGroup;

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

    public boolean delete(SQLiteDatabase db, String group, long user){
        String where = listOfColumns.get(0).name + "=" + group + "AND"
                + listOfColumns.get(1).name + "=" + Long.toString(user);
        return db.delete(nameOfTable, where, null) > 0;
    }

    public Cursor getAllMembers(SQLiteDatabase db) {
        List<String> colNames = new ArrayList<String>();
        for (Column c : listOfColumns){
            colNames.add(c.name);
        }
        String[] columns = colNames.toArray(new String [colNames.size()]);
        return db.query(nameOfTable, columns, null, null, null, null, null);
        //public Cursor query (String table, String[] columns, String selection,
        // String[] selectionArgs, String groupBy, String having, String orderBy)
    }

    public void insert(SQLiteDatabase db, MemberOfGroup member){
        ContentValues newMember = new ContentValues();
        newMember.put(listOfColumns.get(0).name, member.getId_group());
        newMember.put(listOfColumns.get(1).name, member.getId_user());
    }

}
