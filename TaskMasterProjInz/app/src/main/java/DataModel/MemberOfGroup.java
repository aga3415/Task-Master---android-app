package DataModel;

/**
 * Created by Agnieszka on 2015-04-28.
 */
public class MemberOfGroup {

    private String id_user;
    private long id_group;
    private String name;

    public MemberOfGroup(String id_user, long id_group, String name){
        this.id_user = id_user;
        this.name = name;
        this.id_group = id_group;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public void setId_group(long id_group) {
        this.id_group = id_group;
    }

    public long getId_group() {
        return id_group;
    }

    public String getId_user() {
        return id_user;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
