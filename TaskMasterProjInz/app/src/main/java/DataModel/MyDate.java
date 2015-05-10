package DataModel;

import java.util.Calendar;

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
            this.year = Integer.parseInt(date.substring(0, 4));
            //System.out.println("year "+year);
            this.month = Integer.parseInt(date.substring(5,7));
            //System.out.println("month "+month);
            this.day = Integer.parseInt(date.substring(8,10));
            //System.out.println("day "+day);
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
        String date;
        date = Integer.toString(year) + "-";
        if (month < 10) date +="0";
        date += Integer.toString(month) + "-";
        if (day < 10) date +="0";
        date += Integer.toString(day);
        return date;
    }

    public String getDateStringDMY(){
        if (this.isEmpty()) {
            return "";
        }
        String date = "";
        if (day < 10) date ="0";
        date += Integer.toString(day) + "-";
        if (month < 10) date +="0";
        date += Integer.toString(month) + "-";
        date += Integer.toString(year);

        return date;
    }

    public static MyDate getTodayDate(){

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new MyDate(day,month,year);
    }

    public static MyDate getTomorrowDate(){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new MyDate(day,month,year);
    }

}
