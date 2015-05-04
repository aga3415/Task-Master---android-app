package Database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import DataModel.Aim;

/**
 * Created by Agnieszka on 2015-05-02.
 */
public class AimsTable extends Table {

    Column id, id_executor, id_principal, points_aim, points_actual, description;
    public AimsTable(SQLiteDatabase db) {
        super(db);
    }

    public void setAllInfoAboutTable(){
        this.nameOfTable = "AIMS";
        id = new Column ("ID", "INTEGER PRIMARY KEY AUTOINCREMENT", 0);
        id_executor = new Column("ID_EXECUTOR", "INTEGER REFERENCES USERS(ID) ON DELETE CASCADE", 1);
        id_principal = new Column("ID_PRINCIPAL", "INTEGER REFERENCES USERS(ID) ON DELETE CASCADE", 2);
        points_aim = new Column ("POINTS_AIM", "INTEGER NOT NULL", 3);
        points_actual = new Column ("POINTS_ACTUAL", "INTEGER DEFAULT 0", 4);
        description = new Column("DESCRIPTION", "TEXT", 5);

        listOfColumns.add(id);
        listOfColumns.add(id_executor);
        listOfColumns.add(id_principal);
        listOfColumns.add(points_aim);
        listOfColumns.add(points_actual);
        listOfColumns.add(description);

    }

    public long insertAim(Aim aim){
        ContentValues newAim = new ContentValues();
        newAim.put(listOfColumns.get(1).name, aim.getId_executor());
        newAim.put(listOfColumns.get(2).name, aim.getId_principal());
        newAim.put(listOfColumns.get(3).name, aim.getPoints_aim());
        newAim.put(listOfColumns.get(4).name, aim.getPoints_actual());
        newAim.put(listOfColumns.get(5).name, aim.getDescription());

        return db.insert("AIMS", null, newAim);
        //Log.d(DEBUG_TAG, "Wstawianie nowego taska do bazy");

    }

    public boolean updateAim(Aim aim) {
        long id = aim.getId();

        ContentValues updateAim = new ContentValues();
        updateAim.put(listOfColumns.get(1).name, aim.getId_executor());
        updateAim.put(listOfColumns.get(2).name, aim.getId_principal());
        updateAim.put(listOfColumns.get(3).name, aim.getPoints_aim());
        updateAim.put(listOfColumns.get(4).name, aim.getPoints_actual());
        updateAim.put(listOfColumns.get(5).name, aim.getDescription());

        String where = "id =" + id;
        return db.update("AIMS", updateAim, where, null) > 0;
        //metoda db.update zwraca liczbe zmienionych pól
    }
}
