package DataModel;

/**
 * Created by Agnieszka on 2015-04-28.
 */
public class MemberOfGroup {

    private int id_user;
    private int id_group;

    public MemberOfGroup(int id_user, int id_group){
        this.id_user = id_user;
        this.id_group = id_group;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setId_group(int id_group) {
        this.id_group = id_group;
    }

    public int getId_group() {
        return id_group;
    }

    public int getId_user() {
        return id_user;
    }
}
