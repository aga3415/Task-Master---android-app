package Database;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Agnieszka on 2015-05-02.
 */
public abstract class Table {

    SQLiteDatabase db;
    List<Column> listOfColumns;
    String nameOfTable;


    String create, drop; //zmienne przechowujące polecenia stworzenia i usuniecia tabeli
    public Table(SQLiteDatabase db){
        this.db = db;
        listOfColumns = new ArrayList<Column>();
        setAllInfoAboutTable();
        composeCreate();
        composeDrop();

    }

    public class Column{
        public String name;
        public String options;
        public int number;

        public Column(String name, String options, int number){
            this.name = name;
            this.options = options;
            this.number = number;
        }
    }

    public void setAllInfoAboutTable(){}
    //tutaj powinny byc wpisane wszystkie informacje o kolumnach,
    // wszystkie Column powinny byc dodane do listy


    private void composeCreate(){

        create = "CREATE TABLE " + nameOfTable + " ( ";
        for (Column c : listOfColumns){
            create = create + c.name + " " + c.options + ", ";
        }
        create = create.substring(0, create.length()-2);
        create += " )";

    }

    private void composeDrop(){
        drop = "DROP TABLE IF EXISTS " + nameOfTable;
    }
}
