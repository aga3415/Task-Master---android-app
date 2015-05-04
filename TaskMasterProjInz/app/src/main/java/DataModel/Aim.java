package DataModel;

/**
 * Created by Agnieszka on 2015-04-28.
 */
public class Aim {

    private int id;
    private int id_executor;
    private int id_principal;
    private int points_aim;
    private int points_actual;
    private String description;

    public Aim(int id, int id_executor, int id_principal, int points_aim, int points_actual, String description){
        this.id = id;
        this. id_executor = id_executor;
        this.id_principal = id_principal;
        this.points_aim = points_aim;
        this.points_actual = points_actual;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_executor(int id_executor) {
        this.id_executor = id_executor;
    }

    public void setId_principal(int id_principal) {
        this.id_principal = id_principal;
    }

    public void setPoints_actual(int points_actual) {
        this.points_actual = points_actual;
    }

    public void setPoints_aim(int points_aim) {
        this.points_aim = points_aim;
    }

    public int getId_principal() {
        return id_principal;
    }

    public int getId() {
        return id;
    }

    public int getId_executor() {
        return id_executor;
    }

    public int getPoints_actual() {
        return points_actual;
    }

    public int getPoints_aim() {
        return points_aim;
    }

    public String getDescription(){ return description; }

    public void setDescription(String description){ this.description = description; }
}
