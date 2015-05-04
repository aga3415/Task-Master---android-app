package DataModel;

/**
 * Created by Agnieszka on 2015-04-28.
 */
public class Task {

    private int id;
    private int id_parent;
    private String description;
    private int priority;
    private Date date_insert;
    private Date date_update;
    private Date date_plan_exec;
    private Date date_exec;
    private Date date_archive;
    private int cycle_time;
    private int id_group;
    private int id_executor;
    private int id_principal;
    private int points;

    public Task(int id, int id_parent, String description, int priority, Date date_insert, Date date_update,
                Date date_plan_exec, Date date_exec, Date date_archive, int cycle_time, int id_group,
                int id_executor, int id_principal, int points){
        this.id = id;
        this.id_parent = id_parent;
        this.description = description;
        this.priority = priority;
        this.date_insert = date_insert;
        this.date_update = date_update;
        this.date_plan_exec = date_plan_exec;
        this.date_exec = date_exec;
        this.date_archive = date_archive;
        this.cycle_time = cycle_time;
        this.id_group = id_group;
        this.id_executor = id_executor;
        this.id_principal = id_principal;
        this.points = points;
    }

    public boolean isComplited(){
        if (date_exec == null) return false;
        return true;
    }

    public boolean isLate(Date current_date){
        return Date.aEarlierThanB(date_plan_exec, current_date);
    }

    public void setId(int id){
        this.id = id;
    }

    public void setId_parent(int id_parent) {
        this.id_parent = id_parent;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setDate_insert(Date date_insert) {
        this.date_insert = date_insert;
    }

    public void setDate_update(Date date_update) {
        this.date_update = date_update;
    }

    public void setDate_plan_exec(Date date_plan_exec) {
        this.date_plan_exec = date_plan_exec;
    }

    public void setDate_exec(Date date_exec) {
        this.date_exec = date_exec;
    }

    public void setDate_archive(Date date_archive) {
        this.date_archive = date_archive;
    }

    public void setCycle_time(int cycle_time) {
        this.cycle_time = cycle_time;
    }

    public void setId_group(int id_group) {
        this.id_group = id_group;
    }

    public void setId_executor(int id_executor) {
        this.id_executor = id_executor;
    }

    public void setId_principal(int id_principal) {
        this.id_principal = id_principal;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public int getId_parent() {
        return id_parent;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate_archive() {
        return date_archive;
    }

    public Date getDate_exec() {
        return date_exec;
    }

    public Date getDate_insert() {
        return date_insert;
    }

    public Date getDate_plan_exec() {
        return date_plan_exec;
    }

    public Date getDate_update() {
        return date_update;
    }

    public int getCycle_time() {
        return cycle_time;
    }

    public int getId_executor() {
        return id_executor;
    }

    public int getId_group() {
        return id_group;
    }

    public int getId_principal() {
        return id_principal;
    }

    public int getPoints() {
        return points;
    }

    public int getPriority() {
        return priority;
    }

}
