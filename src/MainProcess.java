import java.sql.*;

import javax.swing.SwingUtilities;
public class MainProcess{
	public static MainProcess main;
	public static UserDAO dao;
	private Login loginView;
    private CalendarView calendarView;
    private Join joinView;
    private AddSchedule addScheduleView;
    private Diary diaryView;
    private Timetable timetableView;
    private AddDiary addDiaryView;
    private AddTimeTable addTimeTableView;
    private MoneyAdmin moneyView;
    private AddMoney addMoneyView;
    public String id;
    
    public static void main(String[] args) {
    	dao=new UserDAO();
    	main = new MainProcess();
        main.loginView = new Login(); // 로그인창 보이기
        main.loginView.setMain(main); // 로그인창에게메인 클래스보내기
    }
    
    public void showJoinView() {
    	this.joinView=new Join();
    }
    
    
    public void showCalendarView(){
        loginView.dispose(); // 로그인창닫기
        SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				 calendarView=new CalendarView(main);
			}
		});
    }
    public void showAddScheduleView(String date) {
    	this.addScheduleView = new AddSchedule(main,date);
    }
    
    public void disposeScheduleView() {
    	this.calendarView.dispose();
    }



    
    public void showDiaryView() {
    	this.diaryView=new Diary(main);
    }
    public void showAddDiaryView() {
    	this.addDiaryView = new AddDiary(main);
    }
    
    public void disposeDiaryView() {
    	this.diaryView.dispose();
    }
 
    



    public void showMoneyView() {				//MoneyGUI
    	this.moneyView = new MoneyAdmin(main);
    }
    public void disposeMoneyView() {
    	this.moneyView.dispose();
    }
    public void addMoneyView() {
    	this.addMoneyView=new AddMoney(main);
    }
    
    
    
    public void showTimetable() {				//TimeTableGUI
    	this.timetableView=new Timetable(main);
    }
    public void disposeTimeTableView() {
    	this.timetableView.dispose();
    }
    public void showAddTimeTableView() {
    	this.addTimeTableView=new AddTimeTable(main);
    }
    
    public void sendID(String id){
    	this.id =id;
    }
}

