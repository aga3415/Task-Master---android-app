package DataModel;

/**
 * Created by Agnieszka on 2015-04-28.
 */
public class Group {

    private int id;
    private String name;

    public Group(int id, String name){
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
