package DataModel;

/**
 * Created by Agnieszka on 2015-04-28.
 */
public class Task {

    protected int id;
    protected int id_parent;
    protected String description;
    protected int priority;
    protected MyDate date_insert;
    protected MyDate date_update;
    protected MyDate date_plan_exec;
    protected MyDate date_exec;
    protected MyDate date_archive;
    protected int cycle_time;
    protected long id_group;
    protected String id_executor;
    protected String id_principal;
    protected int points;

    public Task(int id, int id_parent, String description, int priority, MyDate date_insert, MyDate date_update,
                MyDate date_plan_exec, MyDate date_exec, MyDate date_archive, int cycle_time, long id_group,
                String id_executor, String id_principal, int points){
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

    public Task(){
        date_insert = new MyDate();
        date_exec = new MyDate();
        date_archive = new MyDate();
        date_plan_exec = new MyDate();
        date_update = new MyDate();
    }

    public boolean isComplited(){
        if (date_exec.isEmpty()) return false;
        return true;
    }

    public boolean isLate(MyDate current_date){
        return MyDate.aEarlierThanB(date_plan_exec, current_date);
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

    public void setDate_insert(MyDate date_insert) {
        this.date_insert = date_insert;
    }

    public void setDate_update(MyDate date_update) {
        this.date_update = date_update;
    }

    public void setDate_plan_exec(MyDate date_plan_exec) {
        this.date_plan_exec = date_plan_exec;
    }

    public void setDate_exec(MyDate date_exec) {
        this.date_exec = date_exec;
    }

    public void setDate_archive(MyDate date_archive) {
        this.date_archive = date_archive;
    }

    public void setCycle_time(int cycle_time) {
        this.cycle_time = cycle_time;
    }

    public void setId_group(long id_group) {
        this.id_group = id_group;
    }

    public void setId_executor(String id_executor) {
        this.id_executor = id_executor;
    }

    public void setId_principal(String id_principal) {
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

    public MyDate getDate_archive() {
        return date_archive;
    }

    public MyDate getDate_exec() {
        return date_exec;
    }

    public MyDate getDate_insert() {
        return date_insert;
    }

    public MyDate getDate_plan_exec() {
        return date_plan_exec;
    }

    public MyDate getDate_update() {
        return date_update;
    }

    public int getCycle_time() {
        return cycle_time;
    }

    public String getId_executor() {
        return id_executor;
    }

    public long getId_group() {
        return id_group;
    }

    public String getId_principal() {
        return id_principal;
    }

    public int getPoints() {
        return points;
    }

    public int getPriority() {
        return priority;
    }

}
