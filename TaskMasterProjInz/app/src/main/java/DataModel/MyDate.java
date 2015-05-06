package DataModel;

/**
 * Created by Agnieszka on 2015-04-28.
 */
public class MyDate {

    private int day;
    private int month;
    private int year;

    public MyDate(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }
    public MyDate(){
        this.day = 0;
        this.month = 0;
        this.year = 0;
    }

    public MyDate(String date/*"yyyy-mm-dd"*/){
        if (date.length() < 9) {
            this.day = 0;
            this.month = 0;
            this.year = 0;
        }else {
            this.year = Integer.parseInt(date.substring(0, 3));
            this.month = Integer.parseInt(date.substring(5,6));
            this.day = Integer.parseInt(date.substring(8,9));
        }

    }
    public boolean isEmpty(){
        if ((day == 0) || ((month == 0) | (year == 0))) return true;
        return false;
    }

    public static boolean aEarlierThanB(MyDate a, MyDate b){
        if (a.isEmpty()) return false;

        if (a.year < b.year) return true;
        if (a.year > b.year) return false;
        if (a.month < b.month) return true;
        if (a.month > b.month) return false;
        if (a.day < b.day) return true;
        if (a.day > b.day) return false;

        return false; //w przypadku gdy dzien, miesiac i rok takie same

    }

    public static boolean isEqual(MyDate a, MyDate b){
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
    public String getDateString(){
        if (this.isEmpty()) {
            return "";
        }
        return Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day);
    }

}
