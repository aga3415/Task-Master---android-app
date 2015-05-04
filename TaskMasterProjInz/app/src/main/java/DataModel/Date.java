package DataModel;

/**
 * Created by Agnieszka on 2015-04-28.
 */
public class Date {

    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public static boolean aEarlierThanB(Date a, Date b){
        if (a.year < b.year) return true;
        if (a.year > b.year) return false;
        if (a.month < b.month) return true;
        if (a.month > b.month) return false;
        if (a.day < b.day) return true;
        if (a.day > b.day) return false;

        return false; //w przypadku gdy dzien, miesiac i rok takie same

    }

    public static boolean isEqual(Date a, Date b){
        if (a.year == b.year && a.month == b.month && a.day == b.day) return true;
        return false;
    }

    public void setDay(int day){
        this.day = day;
    }
    public void setMonth(int month){
        this.month = month;
    }
    public void setYear(int year){
        this.year = year;
    }
    public int getDay(){
        return day;
    }
    public int getMonth(){
        return month;
    }
    public int getYear(){
        return year;
    }
    public String getDateString(){ return year + "-" + month + "-" + day; }
}
