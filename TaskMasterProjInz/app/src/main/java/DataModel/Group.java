package DataModel;

/**
 * Created by Agnieszka on 2015-04-28.
 */
public class Group {

    private long id;
    private String name;

    public Group(long id, String name){
        this.id = id;
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean equals(Object group){
        if (group == null) return false;
        if (! (group instanceof Group)) return false;
        if (this.id == ((Group) group).id && this.name.equals(((Group) group).getName())) return true;
        return false;
    }
    public int hashCode(){
        return (int) id;
    }
}
