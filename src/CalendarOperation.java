import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarOperation{
	final int CAL_WIDTH=7;
	final int CAL_HEIGHT=6;
	int calDates[][];
	int calYear;
	int calMonth;
	int calDayOfMon;
	final int calLastDateOfMonth[]= {31,28,31,30,31,30,31,31,30,31,30,31};
	int calLastDate;
	Calendar today=Calendar.getInstance();
	Calendar cal;
	
	public CalendarOperation() {
		calYear=today.get(Calendar.YEAR);
		calMonth=today.get(Calendar.MONTH);
		calDayOfMon=today.get(Calendar.DAY_OF_MONTH);
		makeCalData(today);
	}
	private void makeCalData(Calendar cal) {
		 calDates= new int[CAL_HEIGHT][CAL_WIDTH];
		int calStartingPos = (cal.get(Calendar.DAY_OF_WEEK)+7-(cal.get(Calendar.DAY_OF_MONTH))%7)%7;
		if(calMonth==1)
			calLastDate=calLastDateOfMonth[calMonth]+leapCheck(calYear);
		else
			calLastDate=calLastDateOfMonth[calMonth];
		for(int i=0,num=1,k=0;i<CAL_HEIGHT;i++) {
			if(i==0)
				k=calStartingPos;
			else
				k=0;
			for(int j=k;j<CAL_WIDTH;j++)
				if(num<=calLastDate)
					calDates[i][j]=num++;
		}
	}
	private int leapCheck(int year) {//???????? Ȯ??
		if(year%4==0&&year%100!=0||year%400==0) return 1;
		else return 0;
	}
	public void moveMonth(int mon) {
		calMonth+=mon;
		if(calMonth>11)
			while(calMonth>11) {
				calYear++;
				calMonth-=12;
			}else if(calMonth<0)
				while(calMonth<0) {
					calYear--;
					calMonth+=12;
				}
		cal=new GregorianCalendar(calYear,calMonth,calDayOfMon);
		makeCalData(cal);
	}
}