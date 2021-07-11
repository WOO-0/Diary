import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;
import javax.swing.border.LineBorder;

import com.mysql.jdbc.Constants;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CalendarView extends CalendarOperation {
	private MainProcess main;
	JFrame mainFrame;
	JPanel calOpPanel;
	JButton IYearBut;
	JButton IMonBut;
	JButton nMonBut;
	JButton nYearBut;
	JButton addBut;
	ListenForCalOpButtons IForCalOpButtons = new ListenForCalOpButtons();
	ListenSelButtons lForSelButtons = new ListenSelButtons();
	JPanel calPanel;
	JButton weekDaysName[];
	JButton dateButs[][] = new JButton[6][7];
	listenForDateButs IForDateButs = new listenForDateButs();

	JPanel infoPanel;
	JLabel infoCal;
	JPanel keepingBookPanel;

	JPanel menuPanel;
	JButton timetableBut;
	JButton diaryBut;
	JButton moneyBut;

	String selectedDate;
	final String WEEK_DAY_NAME[] = { "일", "월", "화", "수", "목", "금", "토" };

	ArrayList<ScheduleData> schedulelist;
	ArrayList<MoneyData> moneylist;
	int posOfData[];
	private int selectednumber;
	
	JLabel expenselb;
	JLabel expense_money;
	JLabel profitlb;
	JLabel profit_money;
	JLabel totallb;
	JLabel total_money;

	public CalendarView(MainProcess main) {
		if ((today.get(Calendar.MONTH) + 1) < 10 && (today.get(Calendar.DAY_OF_MONTH) < 10))
			selectedDate = (today.get(Calendar.YEAR) + "-0" + (today.get(Calendar.MONTH) + 1) + "-0"
					+ today.get(Calendar.DAY_OF_MONTH));
		else if ((today.get(Calendar.MONTH) + 1) > 10 && (today.get(Calendar.DAY_OF_MONTH) < 10))
			selectedDate = (today.get(Calendar.YEAR) + "-" + (today.get(Calendar.MONTH) + 1) + "-0"
					+ today.get(Calendar.DAY_OF_MONTH));
		else if ((Calendar.getInstance().get(Calendar.MONTH) + 1) > 10
				&& (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) > 10))
			selectedDate = (today.get(Calendar.YEAR) + "-" + (today.get(Calendar.MONTH) + 1) + "-"
					+ today.get(Calendar.DAY_OF_MONTH));
		else
			selectedDate = (today.get(Calendar.YEAR) + "-0" + (today.get(Calendar.MONTH) + 1) + "-"
					+ today.get(Calendar.DAY_OF_MONTH));

		this.main = main;
		mainFrame = new JFrame("Calendar");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(1000, 700);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		} catch (Exception e) {
			e.printStackTrace();
		}

		calOpPanel = new JPanel();
		IYearBut = new JButton("<<");
		IYearBut.setToolTipText("Previous Year");
		IYearBut.addActionListener(IForCalOpButtons);
		IMonBut = new JButton("<");
		IMonBut.setToolTipText("Previous Month");
		IMonBut.addActionListener(IForCalOpButtons);
		nMonBut = new JButton(">");
		nMonBut.setToolTipText("Next Month");
		nMonBut.addActionListener(IForCalOpButtons);
		nYearBut = new JButton(">>");
		nYearBut.setToolTipText("Next Year");
		nYearBut.addActionListener(IForCalOpButtons);
		addBut = new JButton("+");
		addBut.setToolTipText("add");

		calOpPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		calOpPanel.add(IYearBut);
		calOpPanel.add(IMonBut);
		calOpPanel.add(nMonBut);
		calOpPanel.add(nYearBut);
		calOpPanel.add(addBut);

		infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		infoCal = new JLabel("<html><font size = 10>" + "<center>"
				+ (today.get(Calendar.YEAR) + "년<br>" + (today.get(Calendar.MONTH) + 1)) + "월&nbsp;</center></html>",
				SwingConstants.CENTER);
		infoCal.setBorder(BorderFactory.createTitledBorder(""));
		infoPanel.add(infoCal, BorderLayout.NORTH);

		calPanel = new JPanel();
		weekDaysName = new JButton[7];
		for (int i = 0; i < CAL_WIDTH; i++) {
			weekDaysName[i] = new JButton(WEEK_DAY_NAME[i]);
			weekDaysName[i].setBorderPainted(false);
			weekDaysName[i].setContentAreaFilled(false);

			weekDaysName[i].setForeground(Color.WHITE);
			weekDaysName[i].setBackground(Color.GRAY);
			weekDaysName[i].setOpaque(true);
			weekDaysName[i].setFocusPainted(false);
			calPanel.add(weekDaysName[i]);
		}
		for (int i = 0; i < CAL_HEIGHT; i++) {
			for (int j = 0; j < CAL_WIDTH; j++) {
				dateButs[i][j] = new JButton();
				dateButs[i][j].setBorderPainted(false);
				dateButs[i][j].setBackground(Color.white);
				dateButs[i][j].setOpaque(true);
				dateButs[i][j].addActionListener(IForDateButs);
				calPanel.add(dateButs[i][j]);
			}
		}

		calPanel.setLayout(new GridLayout(0, 7, 1, 0));
		calPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		// showCal();
		loadScheduleData();
		keepingBookPanel = new JPanel();
		keepingBookPanel.setPreferredSize(new Dimension(200, 115));
		keepingBookPanel.setBorder(BorderFactory.createTitledBorder("Total Income/Expense"));
		keepingBookPanel.setLayout(null);
		profitlb = new JLabel("수익");
		profitlb.setBounds(10, 25, 48, 23);
		profitlb.setOpaque(true);
		profitlb.setBackground(new Color(103, 153, 255));
		profitlb.setForeground(new Color(255, 255, 255));
		profitlb.setHorizontalAlignment(SwingConstants.CENTER);
		keepingBookPanel.add(profitlb);
		profit_money = new JLabel("0  ");
		profit_money.setBounds(63, 25, 129, 23);
		profit_money.setOpaque(true);
		profit_money.setForeground(new Color(103, 153, 255));
		profit_money.setBackground(Color.WHITE);
		profit_money.setHorizontalAlignment(SwingConstants.RIGHT);
		keepingBookPanel.add(profit_money);

		expenselb = new JLabel("지출");
		expenselb.setBounds(10, 50, 48, 23);
		expenselb.setOpaque(true);
		expenselb.setBackground(new Color(241, 95, 95));
		expenselb.setForeground(new Color(255, 255, 255));
		expenselb.setHorizontalAlignment(SwingConstants.CENTER);
		keepingBookPanel.add(expenselb);
		expense_money = new JLabel("0  ");
		expense_money.setBounds(63, 50, 129, 23);
		expense_money.setOpaque(true);
		expense_money.setForeground(new Color(241, 95, 95));
		expense_money.setBackground(Color.WHITE);
		expense_money.setHorizontalAlignment(SwingConstants.RIGHT);
		keepingBookPanel.add(expense_money);

		totallb = new JLabel("총계");
		totallb.setBounds(10, 75, 48, 23);
		totallb.setOpaque(true);
		totallb.setBackground(new Color(53, 53, 53));
		totallb.setForeground(new Color(255, 255, 255));
		totallb.setHorizontalAlignment(SwingConstants.CENTER);
		keepingBookPanel.add(totallb);
		total_money = new JLabel("0  ");
		total_money.setBounds(63, 75, 129, 23);
		total_money.setOpaque(true);
		total_money.setBackground(Color.WHITE);
		total_money.setHorizontalAlignment(SwingConstants.RIGHT);
		keepingBookPanel.add(total_money);

		loadMoneyData();
		menuPanel = new JPanel();
		diaryBut = new JButton("Diary");
		diaryBut.addActionListener(lForSelButtons);
		timetableBut = new JButton("TimeTable");
		timetableBut.addActionListener(lForSelButtons);
		moneyBut = new JButton("Money");
		moneyBut.addActionListener(lForSelButtons);
		menuPanel.add(timetableBut);
		menuPanel.add(diaryBut);
		menuPanel.add(moneyBut);

		JPanel frameSubPanelNorth = new JPanel();
		Dimension menuPanelSize = menuPanel.getPreferredSize();
		menuPanelSize.width = 320;
		menuPanel.setPreferredSize(menuPanelSize);
		frameSubPanelNorth.setLayout(new BorderLayout());
		frameSubPanelNorth.add(menuPanel, BorderLayout.EAST);
		frameSubPanelNorth.add(calOpPanel, BorderLayout.WEST);

		JPanel frameSubPanelCenter = new JPanel();
		Dimension calOpPanelSize = frameSubPanelNorth.getPreferredSize();
		calOpPanelSize.height = 60;
		frameSubPanelNorth.setPreferredSize(calOpPanelSize);
		frameSubPanelCenter.setLayout(new BorderLayout());
		frameSubPanelCenter.add(frameSubPanelNorth, BorderLayout.NORTH);
		frameSubPanelCenter.add(calPanel, BorderLayout.CENTER);

		JPanel frameSubPanelWest = new JPanel();
		Dimension infoPanelSize = infoPanel.getPreferredSize();
		infoPanelSize.height = 60;
		infoPanel.setPreferredSize(infoPanelSize);
		frameSubPanelWest.setLayout(new BorderLayout());
		frameSubPanelWest.add(infoPanel, BorderLayout.CENTER);
		frameSubPanelWest.add(keepingBookPanel, BorderLayout.SOUTH);

		Dimension frameSubPanelCenterSize = frameSubPanelCenter.getPreferredSize();
		frameSubPanelCenterSize.width = 410;
		frameSubPanelCenter.setPreferredSize(frameSubPanelCenterSize);

		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(frameSubPanelCenter, BorderLayout.CENTER);
		mainFrame.add(frameSubPanelWest, BorderLayout.WEST);
		// mainFrame.add(frameSubPanelEast, BorderLayout.EAST);
		mainFrame.setVisible(true);

		addBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.showAddScheduleView(selectedDate);
			}

		});
	}

//	private void showCal() { // 달력에 날짜를 보여주는 메소드
//
//		for (int i = 0; i < CAL_HEIGHT; i++) {
//			for (int j = 0; j < CAL_WIDTH; j++) {
//				String fontColor = "black";
//				dateButs[i][j].setText("<html><font color=" + fontColor + ">" + calDates[i][j] + "</font></html>");
//				dateButs[i][j].setHorizontalAlignment(SwingConstants.LEFT);
//				dateButs[i][j].setVerticalAlignment(SwingConstants.TOP);
////				dateButs[i][j].removeAll();
//				if (calMonth == today.get(Calendar.MONTH) && calYear == today.get(Calendar.YEAR)
//						&& calDates[i][j] == today.get(Calendar.DAY_OF_MONTH)) {
//					dateButs[i][j].setText("<html><font color=" + fontColor + ">" + calDates[i][j]
//							+ "</font><font color = green>&nbsp&nbsp(today)</html>");
//					;
//					dateButs[i][j].setToolTipText("Today");
//				}
//				if (calDates[i][j] == 0)
//					dateButs[i][j].setText(" ");
////				else dateButs[i][j].setVisible(true);
//
//			}
//		}
//	}

	private class ListenForCalOpButtons implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == IYearBut)
				moveMonth(-12);
			else if (e.getSource() == IMonBut)
				moveMonth(-1);
			else if (e.getSource() == nMonBut)
				moveMonth(1);
			else if (e.getSource() == nYearBut)
				moveMonth(12);
			infoCal.setText("<html><font size = 10>" + "<center>" + calYear + "년<br>" + (calMonth + 1)
					+ "월&nbsp;</center></html>");
			loadScheduleData();
		}
	}

	private class ListenSelButtons implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == diaryBut)
				main.showDiaryView();
			else if (e.getSource() == moneyBut)
				main.showMoneyView();
			else if (e.getSource() == timetableBut)
				main.showTimetable();
			mainFrame.dispose();
		}

	}

	private class listenForDateButs implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int k = 0, l = 0;
			for (int i = 0; i < CAL_HEIGHT; i++) {
				for (int j = 0; j < CAL_WIDTH; j++) {
					if (e.getSource() == dateButs[i][j]) {
						k = i;
						l = j;
						break;
					}
				}
			}
			String str = dateButs[k][l].getText().replaceAll("[^0-9]", "");
			if (str.equals("")) {
				if ((today.get(Calendar.MONTH) + 1) < 10 && (today.get(Calendar.DAY_OF_MONTH) < 10))
					selectedDate = (today.get(Calendar.YEAR) + "-0" + (today.get(Calendar.MONTH) + 1) + "-0"
							+ today.get(Calendar.DAY_OF_MONTH));
				else if ((today.get(Calendar.MONTH) + 1) > 10 && (today.get(Calendar.DAY_OF_MONTH) < 10))
					selectedDate = (today.get(Calendar.YEAR) + "-" + (today.get(Calendar.MONTH) + 1) + "-0"
							+ today.get(Calendar.DAY_OF_MONTH));
				else if ((Calendar.getInstance().get(Calendar.MONTH) + 1) > 10
						&& (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) > 10))
					selectedDate = (today.get(Calendar.YEAR) + "-" + (today.get(Calendar.MONTH) + 1) + "-"
							+ today.get(Calendar.DAY_OF_MONTH));
				else
					selectedDate = (today.get(Calendar.YEAR) + "-0" + (today.get(Calendar.MONTH) + 1) + "-"
							+ today.get(Calendar.DAY_OF_MONTH));

			} else {
				if (calMonth < 10 && Integer.parseInt(str) < 10) {
					selectedDate = (calYear + "-0" + (calMonth + 1) + "-0" + str);
				} else if (calMonth > 10 && Integer.parseInt(str) < 10) {
					selectedDate = (calYear + "-" + (calMonth + 1) + "-0" + str);
				} else if (calMonth > 10 && Integer.parseInt(str) < 10) {
					selectedDate = (calYear + "-" + (calMonth + 1) + "-" + str);
				} else {
					selectedDate = (calYear + "-0" + (calMonth + 1) + "-" + str);
				}

			}
			int position=0;
			try {
				position=posOfData[Integer.parseInt(str)];
			}catch(Exception evt) {
				return;
			}
			if (position == 0) return;
			JFrame subFrame = new JFrame();
			subFrame.setLayout(null);
			subFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			subFrame.setResizable(false);
			
			
			JLabel datelabel=new JLabel(calYear+"년 "+(calMonth+1)+"월 "+str+"일");
			datelabel.setFont(new Font("바탕체",Font.BOLD, 20));
			datelabel.setOpaque(true);
			datelabel.setBackground(new Color(126,210,255));
			datelabel.setHorizontalAlignment(SwingConstants.CENTER);
			datelabel.setBounds(0,0,500,50);
			int cnt=0;
			do {
				cnt++;
				JPanel panel = new JPanel();
				panel.setBounds(0, 50+70*(cnt-1), 500, 70);
				panel.setLayout(null);
				panel.setBackground(Color.WHITE);
				JLabel daylabel = new JLabel();
				daylabel.setText("<html>"+schedulelist.get(position-1).startdate+"<br>"+schedulelist.get(position-1).enddate+"</html>");
				daylabel.setForeground(new Color(54,138,255));
				daylabel.setBounds(10, 0,90,70);
				daylabel.setHorizontalAlignment(SwingConstants.RIGHT);
				
				JLabel titlelabel = new JLabel();
				titlelabel.setText(schedulelist.get(position-1).title);
				titlelabel.setFont(new Font("바탕체",Font.BOLD,20));
				titlelabel.setBounds(120,0,350,70);
				
				JButton delbut=new JButton("X");
				delbut.setBounds(437, 25, 35, 23);
				if(cnt>1) {
					delbut.setText("X("+(cnt-1)+")");
					delbut.setBounds(430, 25, 50, 23);
				}
				panel.add(titlelabel);
				panel.add(daylabel);
				panel.add(delbut);
				subFrame.add(panel);
				selectednumber=position-1;
				delbut.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String str=e.getActionCommand().replaceAll("[^0-9]", "");
						if(!str.equals(""))
							selectednumber+=Integer.parseInt(str);
						boolean ok=main.dao.deleteSchedule(main.id, schedulelist.get(selectednumber));
						if(ok==true) {
							JOptionPane.showMessageDialog(null, "정상적으로 삭제되었습니다.");
							main.disposeScheduleView();
							subFrame.dispose();
							main.showCalendarView();
						}
						else
							JOptionPane.showMessageDialog(null, "삭제를 하는 과정에서 에러가 발생했습니다.");
					}
				}); 
				position--;
				if(position-2<0) break;
			}while(schedulelist.get(position).startdate.equals(schedulelist.get(position-1).startdate));

			subFrame.add(datelabel);
			subFrame.setSize(500,80+70*cnt);
			subFrame.setLocationRelativeTo(null);
			subFrame.setVisible(true);

		}
	}

	private void loadMoneyData() {
		moneylist = main.dao.loadMoneyData(main.id);
		int expense = 0;
		int profit = 0;
		int total = 0;
		for (int i = 0; i < moneylist.size(); i++) {
			if (moneylist.get(i).classify.equals("지출"))
				expense += Integer.parseInt(moneylist.get(i).money);
			else
				profit += Integer.parseInt(moneylist.get(i).money);
		}
		total = profit - expense;
		StringBuilder sb = new StringBuilder("  ");
		while (expense > 0) {
			if (expense / 1000 == 0)
				sb.insert(0, Integer.toString(expense % 1000));
			else if (expense % 1000 < 10)
				sb.insert(0, ",00" + expense % 1000);
			else if (expense % 1000 < 100)
				sb.insert(0, ",0" + expense % 1000);
			else
				sb.insert(0, "," + expense % 1000);
			expense = expense / 1000;
		}
		expense_money.setText(sb.toString());

		sb = new StringBuilder("  ");
		while (profit > 0) {
			if (profit / 1000 == 0)
				sb.insert(0, Integer.toString(profit % 1000));
			else if (profit % 1000 < 10)
				sb.insert(0, ",00" + profit % 1000);
			else if (profit % 1000 < 100)
				sb.insert(0, ",0" + profit % 1000);
			else
				sb.insert(0, "," + profit % 1000);
			profit = profit / 1000;
		}
		profit_money.setText(sb.toString());

		sb = new StringBuilder("  ");
		if (total < 0) {
			sb.insert(0, "-");
			total *= -1;
		} else {
			sb.insert(0, "+");
		}
		while (total > 0) {
			if (total / 1000 == 0)
				sb.insert(1, Integer.toString(total % 1000));
			else if (total % 1000 < 10)
				sb.insert(1, ",00" + total % 1000);
			else if (total % 1000 < 100)
				sb.insert(1, ",0" + total % 1000);
			else
				sb.insert(1, "," + total % 1000);
			total = total / 1000;
		}

		total_money.setText(sb.toString());

	}

	private void loadScheduleData() {
		schedulelist = main.dao.loadSchedule(main.id);
		posOfData = new int[32];
		for (int i = 0; i < schedulelist.size(); i++) {
			if (calMonth + 1 > Integer.parseInt(schedulelist.get(i).startdate.substring(5, 7))
					|| calYear > Integer.parseInt(schedulelist.get(i).startdate.substring(0, 4))) {
				continue; // 데이터에서 불러온 일정이 그 달 보다 빠르면 넘어감
			} else if (calMonth + 1 < Integer.parseInt(schedulelist.get(i).startdate.substring(5, 7))
					|| calYear < Integer.parseInt(schedulelist.get(i).startdate.substring(0, 4))) {
				break; // 데이터베이스에서 불러온 일정이 그 달의 연월 보다 커지면 멈춤
			} else if (calMonth + 1 == Integer.parseInt(schedulelist.get(i).startdate.substring(5, 7))
					&& calYear == Integer.parseInt(schedulelist.get(i).startdate.substring(0, 4))) {
				posOfData[Integer.parseInt(schedulelist.get(i).startdate.substring(8))] = i + 1; // scheulelist를 찾아갈 수
																									// 있도록 배열을 이용함
			}
		}
		for (int i = 0; i < CAL_HEIGHT; i++) {
			for (int j = 0; j < CAL_WIDTH; j++) {
				if (posOfData[calDates[i][j]] == 0)
					dateButs[i][j].setText(Integer.toString(calDates[i][j]));

				else
					dateButs[i][j].setText("<html>" + calDates[i][j] + "<font color=blue><br> (일정 확인)");

				dateButs[i][j].setHorizontalAlignment(SwingConstants.LEFT);
				dateButs[i][j].setVerticalAlignment(SwingConstants.TOP);
//				dateButs[i][j].removeAll();
				if (calMonth == today.get(Calendar.MONTH) && calYear == today.get(Calendar.YEAR)
						&& calDates[i][j] == today.get(Calendar.DAY_OF_MONTH)) {
					dateButs[i][j].setText("<html>" + calDates[i][j] + "<font color = green>&nbsp&nbsp(today)</html>");
					dateButs[i][j].setToolTipText("Today");
					if (posOfData[calDates[i][j]] != 0)
						dateButs[i][j].setText("<html>" + calDates[i][j]
								+ "<font color = green>&nbsp&nbsp(today)</font><font color=blue><br>(일정 확인)</html>");
				}
				if (calDates[i][j] == 0)
					dateButs[i][j].setText(" ");
//				else dateButs[i][j].setVisible(true);

			}
		}
	}

	public void dispose() {
		mainFrame.dispose();
	}
}

class ScheduleData {
	String title;
	String startdate;
	String enddate;
	String contents;

	public void setData(String title, String startdate, String enddate, String contents) {
		this.title = title;
		this.startdate = startdate;
		this.enddate = enddate;
		this.contents = contents;
	}
}