
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.border.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

public class Diary extends JFrame {
	private final String arr[] = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

	private MainProcess main;

	private JPanel westPanel;
	private JLabel labelTitle;
	private JLabel labelContents;
	private JLabel date;
	private JTextArea textTitle;
	private JTextArea textContents;
	private JScrollPane scrollContents;
	private JButton addButton;
	private JButton delButton;

	private JPanel eastPanel;
	private JTable diaryList;
	private JScrollPane scrolltable;

	private JTable westtable;
	private JTable easttable;
	private JTable headerTable;
	private DefaultTableModel westtableModel;
	private DefaultTableModel easttableModel;
	private DefaultTableModel headerTableModel;

	private JPanel butPanel;
	private JButton schedulerBut;
	private JButton timeTableBut;
	private JButton moneyBut;
	private ListenSelButtons lSelButtons = new ListenSelButtons();

	ArrayList<String> title = new ArrayList<String>();
	ArrayList<String> tabledate = new ArrayList<String>();
	ArrayList<String> contents = new ArrayList<String>();

	String selectedDate = null;

	public Diary(MainProcess main) {
		this.main = main;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 700);
		setResizable(false);
		setTitle("Diary");
		setLocationRelativeTo(null);
		setLayout(null);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		} catch (Exception e) {
			e.printStackTrace();
		}
		westPanel = new JPanel();
		westPanel.setBounds(0, 0, 500, 700);
		westPanel.setLayout(null);
		labelTitle = new JLabel("제목:");
		labelTitle.setFont(new Font("돋움체", Font.BOLD, 19));
		labelTitle.setBounds(10, 70, 50, 20);
		westPanel.add(labelTitle);

		textTitle = new JTextArea();
		textTitle.setEditable(false);
		textTitle.setFont(new Font("고딕체", Font.PLAIN, 19));
		textTitle.setBounds(70, 70, 400, 30);
		westPanel.add(textTitle);

		textContents = new JTextArea();
		textContents.setFont(new Font("굴림체", Font.PLAIN, 19));
		textContents.setLineWrap(true);
		textContents.setWrapStyleWord(true);
		textContents.setEditable(false);
		scrollContents = new JScrollPane(textContents, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollContents.setBounds(10, 100, 460, 540);
		westPanel.add(scrollContents);

		date = new JLabel("<html><font size=6>"
				+ (Calendar.getInstance().get(Calendar.YEAR) + "-" + (Calendar.getInstance().get(Calendar.MONTH) + 1))
				+ "-" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
				+ "</font><br><div style = text-align:right><font size=4>Today : "
				+ arr[Calendar.getInstance().get(Calendar.WEEK_OF_MONTH)-1] + "</html>");
		date.setBounds(10, 10, 200, 60);
		westPanel.add(date);
		addButton = new JButton("+");
		addButton.setBounds(405, 10, 35, 35);
		addButton.setFocusPainted(false);
		westPanel.add(addButton);
		delButton = new JButton("-");
		delButton.setBounds(440, 10, 35, 35);
		delButton.setFocusPainted(false);
		westPanel.add(delButton);

		eastPanel = new JPanel();
		eastPanel.setBounds(500, 0, 500, 700);
		eastPanel.setLayout(null);
		String[] westheaderNames = { "", "월", "수" };
		String[] eastheaderNames = { "", "날짜", "일기" };
		String[] mainHeaderNames = { "날짜", "전체 목록" };
		headerTableModel = new DefaultTableModel();
		for (int i = 0; i < mainHeaderNames.length; i++) {
			headerTableModel.addColumn(mainHeaderNames[i]);
		}

		// table model
		westtableModel = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		easttableModel = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		westtable = new JTable(westtableModel);
		easttable = new JTable(easttableModel);
		headerTable = new JTable(headerTableModel);

		// set table header
		for (int col = 0; col < eastheaderNames.length; col++) {
			easttableModel.addColumn(eastheaderNames[col]);
		}
		for (int col = 0; col < westheaderNames.length; col++) {
			westtableModel.addColumn(westheaderNames[col]);
		}

		loadData();

		westtable.getTableHeader().setPreferredSize(new Dimension(30, 28));
		easttable.getTableHeader().setPreferredSize(new Dimension(70, 28));
		headerTable.getTableHeader().setPreferredSize(new Dimension(100, 28));
		DefaultTableColumnModel colModel = (DefaultTableColumnModel) westtable.getColumnModel();
		colModel.setColumnSelectionAllowed(false);
		colModel.getColumn(0).setPreferredWidth(50);
		colModel.getColumn(1).setPreferredWidth(200);
		colModel.getColumn(2).setPreferredWidth(50);
		colModel = (DefaultTableColumnModel) easttable.getColumnModel();
		colModel.getColumn(0).setPreferredWidth(50);
		colModel.getColumn(1).setPreferredWidth(250);
		colModel.getColumn(2).setPreferredWidth(400);
		colModel = (DefaultTableColumnModel) headerTable.getColumnModel();
		colModel.getColumn(0).setPreferredWidth(310);
		colModel.getColumn(1).setPreferredWidth(695);
		easttable.setFillsViewportHeight(true);
		westtable.setFillsViewportHeight(true);
		headerTable.setFillsViewportHeight(true);
		westtable.getTableHeader().setResizingAllowed(false);
		easttable.getTableHeader().setResizingAllowed(false);
		headerTable.getTableHeader().setResizingAllowed(false);

		westtable.getTableHeader().setReorderingAllowed(false);
		easttable.getTableHeader().setReorderingAllowed(false);
		headerTable.getTableHeader().setReorderingAllowed(false);

		westtable.setSelectionMode(0);
		easttable.setSelectionMode(0);
		headerTable.setSelectionMode(0);

		JScrollPane westscrollPane = new JScrollPane(westtable);
		JScrollPane eastscrollPane = new JScrollPane(easttable);
		JScrollPane headerScrollPane = new JScrollPane(headerTable);

		westscrollPane.getViewport().setBackground(Color.white);
		eastscrollPane.getViewport().setBackground(Color.white);

		headerScrollPane.setBounds(0, 60, 475, 30);
		westscrollPane.setBounds(0, 85, 155, 560);
		eastscrollPane.setBounds(150, 85, 325, 560);

		butPanel = new JPanel();
		// butPanel.setLayout(FlowLayout);

		schedulerBut = new JButton("Scheduler");
		timeTableBut = new JButton("TimeTable");
		moneyBut = new JButton("Money");
		schedulerBut.addActionListener(lSelButtons);
		timeTableBut.addActionListener(lSelButtons);
		moneyBut.addActionListener(lSelButtons);

		butPanel.add(schedulerBut);
		butPanel.add(timeTableBut);
		butPanel.add(moneyBut);

		butPanel.setBounds(190, 10, 300, 40);

		eastPanel.add(butPanel);
		eastPanel.add(headerScrollPane);
		eastPanel.add(westscrollPane);
		eastPanel.add(eastscrollPane);

		add(westPanel);
		add(eastPanel);
		setVisible(true);

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.showAddDiaryView();
			}
		});
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean ok=false;
				try {
					if( textTitle.getText().equals("")||textContents.getText().equals(""))
						JOptionPane.showMessageDialog(null, "날짜를 선택해주세요");
					else
						ok = main.dao.deleteDiary(main.id, textTitle.getText(), selectedDate, textContents.getText());
					
					if (ok == true) {
						JOptionPane.showMessageDialog(null, "정상적으로 삭제되었습니다.");
						dispose();
						main.showDiaryView();
					}
				} catch (Exception evt) {
					evt.printStackTrace();
					JOptionPane.showMessageDialog(null, "일정을 삭제 할 수 없습니다.");
				}

			}
		});

		westtable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = westtable.getSelectedRow();
				int column = westtable.getSelectedColumn();
				
				try {
				String value = (String) westtable.getValueAt(row, 1);

				int sum = 0;
				
				easttableModel.setNumRows(0);
				if (value.equals("<전체>")) {
					for (int i = 0; i < title.size(); i++) {
						easttableModel.addRow(new String[] { Integer.toString(i + 1), tabledate.get(i), title.get(i) });
					}
				} else {
					for (int i = 1; i <= row - 1; i++) {
						sum += Integer.parseInt((String) westtable.getValueAt(i, 2));
					}
					for (int i = 0; i < Integer.parseInt((String) westtable.getValueAt(row, 2)); i++) {
						easttableModel.addRow(
								new String[] { Integer.toString(i + 1), tabledate.get(sum + i), title.get(sum + i) });
					}
				}
				}catch(ArrayIndexOutOfBoundsException evt) {
				}
			}
		});
		easttable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				int row = easttable.getSelectedRow();
				int column = easttable.getSelectedColumn();
				if (row > -1) {
					int sum = 0;
					for (int i = 1; i <= westtable.getSelectedRow() - 1; i++) {
						sum += Integer.parseInt((String) westtable.getValueAt(i, 2));
					}
					if (westtable.getSelectedRow() == 0)
						sum = -1;
					else
						sum--;
					textTitle.setText(title.get(sum + (Integer.parseInt((String) easttable.getValueAt(row, 0)))));
					selectedDate = tabledate.get(sum + (Integer.parseInt((String) easttable.getValueAt(row, 0))));
					textContents.setText(contents.get(sum + (Integer.parseInt((String) easttable.getValueAt(row, 0)))));
				}

			}
		});
	}

	private class ListenSelButtons implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == schedulerBut)
				main.showCalendarView();
			else if (e.getSource() == moneyBut)
				main.showMoneyView();
			else if (e.getSource() == timeTableBut)
				main.showTimetable();
			dispose();
		}

	}

	public void loadData() {
		main.dao.loadDiary(main.id, title, tabledate, contents);
		westtableModel.addRow(new Object[] { "1", "<전체>", Integer.toString(tabledate.size()) });
		for (int i = 0, cnt = 1, num = 2; i < tabledate.size(); i++) {
			if (i + 1 == tabledate.size()) {
				westtableModel.addRow(new String[] { Integer.toString(num), tabledate.get(i).substring(0, 7),
						Integer.toString(cnt) });
				break;
			}
			if (tabledate.get(i).substring(0, 7).equals(tabledate.get(i + 1).substring(0, 7)) == false) {
				westtableModel.addRow(new String[] { Integer.toString(num), tabledate.get(i).substring(0, 7),
						Integer.toString(cnt) });
				cnt = 1;
				num++;
			} else
				cnt++;
		}
	}

}
