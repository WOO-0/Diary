import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

public class Timetable extends JFrame {
	private MainProcess main;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private JLabel label;

	private JPanel butPanel;
	private JButton addBut;
	private JButton schedulerBut;
	private JButton diaryBut;
	private JButton moneyBut;

	private JPanel panel;
	private JLabel addLabel;

	ListenSelButtons lSelButtons = new ListenSelButtons();
	int selectedNumber=-1;

	public Timetable(MainProcess main) {
		this.main = main;
		setTitle("Timetable");
		setSize(720, 750);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		} catch (Exception e) {
			e.printStackTrace();
		}

		panel = new JPanel();
		panel.setBounds(0, 0, 720, 900);
		panel.setBackground(Color.white);
		panel.setLayout(null);

		model = new DefaultTableModel() {
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};
		table = new JTable(model);
		table.setCellSelectionEnabled(false);
		table.setFocusable(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.setCellSelectionEnabled(false);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(false);
		table.setRowHeight(60);

		String[] columnNames = { "", "월", "화", "수", "목", "금" };
		String[][] data = { { "09", "", "", "", "", "" }, { "10", "", "", "", "", "" }, { "11", "", "", "", "", "" },
				{ "12", "", "", "", "", "" }, { "01", "", "", "", "", "" }, { "02", "", "", "", "", "" },
				{ "03", "", "", "", "", "" }, { "04", "", "", "", "", "" }, { "05", "", "", "", "", "" },
				{ "06", "", "", "", "", "" } };
		for (int col = 0; col < columnNames.length; col++) {
			model.addColumn(columnNames[col]);
		}
		for (int row = 0; row < data.length; row++) {
			model.addRow(data[row]);
		}
		DefaultTableColumnModel colModel = (DefaultTableColumnModel) table.getColumnModel();
		colModel.setColumnSelectionAllowed(false);
		colModel.getColumn(0).setPreferredWidth(30);
		colModel.getColumn(1).setPreferredWidth(200);
		colModel.getColumn(2).setPreferredWidth(200);
		colModel.getColumn(3).setPreferredWidth(200);
		colModel.getColumn(4).setPreferredWidth(200);
		colModel.getColumn(5).setPreferredWidth(200);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 95, 700, 760);

		if (Calendar.getInstance().get(Calendar.MONTH) < 7)
			label = new JLabel(Calendar.getInstance().get(Calendar.YEAR) + "년 1학기");
		else
			label = new JLabel(Calendar.getInstance().get(Calendar.YEAR) + "년 2학기");

		label.setFont(new Font("돋움체", Font.BOLD, 40));
		label.setBounds(10, 10, 300, 70);

		butPanel = new JPanel();
		addBut = new JButton("+");
		schedulerBut = new JButton("Scheduler");
		diaryBut = new JButton("Diary");
		moneyBut = new JButton("Money");
		addBut.addActionListener(lSelButtons);
		schedulerBut.addActionListener(lSelButtons);
		moneyBut.addActionListener(lSelButtons);
		diaryBut.addActionListener(lSelButtons);

		butPanel.add(addBut);
		butPanel.add(schedulerBut);
		butPanel.add(diaryBut);
		butPanel.add(moneyBut);
		butPanel.setBackground(Color.WHITE);
		butPanel.setBounds(400, 30, 300, 200);

		ArrayList<String[]> list = main.dao.loadUserTableData(main.id);
		int cnt=0;
		for (int i = 0; i < list.size(); i++) {
			StringTokenizer token = new StringTokenizer(list.get(i)[6]);
			while (token.hasMoreTokens()) {
				String str = token.nextToken();
				JButton button=	new loadButton(str,list.get(i),i);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String str= ((JButton)e.getSource()).getText();
						for(int i=0;i<list.size();i++) {
							if(str.equals(list.get(i)[3])) {
								selectedNumber=i;
								break;
							}
						}
						JFrame subFrame = new JFrame();
						subFrame.setTitle("상세 정보");
						subFrame.setLayout(null);
						subFrame.setSize(300,320);
						subFrame.setResizable(false);
						subFrame.setLocationRelativeTo(null);
						subFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						subFrame.getContentPane().setBackground(Color.WHITE);
						JLabel numLabel = new JLabel("수강번호");
						numLabel.setBounds(20,20,50,23);
						numLabel.setOpaque(true);
						numLabel.setBackground(Color.red);
						JLabel numLb = new JLabel(list.get(selectedNumber)[0]);
						numLb.setBounds(90,20,200,23);
						
						JLabel gradeLabel = new JLabel("학년");
						gradeLabel.setBounds(20,45,50,23);
						gradeLabel.setOpaque(true);
						gradeLabel.setBackground(Color.ORANGE);
						JLabel gradeLb = new JLabel(list.get(selectedNumber)[1]);
						gradeLb.setBounds(90,45,200,23);
						
						JLabel attLabel = new JLabel("이수구분");
						attLabel.setBounds(20,70,50,23);
						attLabel.setOpaque(true);
						attLabel.setBackground(Color.yellow);
						JLabel attLb = new JLabel(list.get(selectedNumber)[2]);
						attLb.setBounds(90,70,200,23);
						
						JLabel namLabel = new JLabel("과목명");
						namLabel.setBounds(20,95,50,23);
						namLabel.setOpaque(true);
						namLabel.setBackground(Color.GREEN);
						JLabel namLb = new JLabel(list.get(selectedNumber)[3]);
						namLb.setBounds(90,95,200,23);
						
						JLabel grnLabel = new JLabel("학점");
						grnLabel.setBounds(20,120,50,23);
						grnLabel.setOpaque(true);
						grnLabel.setBackground(Color.CYAN);
						JLabel grnLb = new JLabel(list.get(selectedNumber)[4]);
						grnLb.setBounds(90,120,200,23);
						
						JLabel proLabel = new JLabel("담당교수");
						proLabel.setBounds(20,145,50,23);
						proLabel.setOpaque(true);
						proLabel.setBackground(Color.red);
						JLabel proLb = new JLabel(list.get(selectedNumber)[5]);
						proLb.setBounds(90,145,200,23);
						
						JLabel timLabel = new JLabel("강의시간");
						timLabel.setBounds(20,170,50,23);
						timLabel.setOpaque(true);
						timLabel.setBackground(Color.orange);
						JLabel timLb = new JLabel(list.get(selectedNumber)[6]);
						timLb.setBounds(90,170,200,23);
						
						JLabel plaLabel = new JLabel("강의실");
						plaLabel.setBounds(20,195,50,23);
						plaLabel.setOpaque(true);
						plaLabel.setBackground(Color.yellow);
						JLabel plaLb = new JLabel(list.get(selectedNumber)[7]);
						plaLb.setBounds(90,195,200,23);
						
						JLabel depLabel = new JLabel("개설학과");
						depLabel.setBounds(20,220,50,23);
						depLabel.setOpaque(true);
						depLabel.setBackground(Color.green);
						JLabel depLb = new JLabel(list.get(selectedNumber)[8]);
						depLb.setBounds(90,220,200,23);
						
						JButton delBut = new JButton("삭제");
						delBut.setBounds(120,245,70,23);
						
						subFrame.add(numLabel);subFrame.add(gradeLabel);subFrame.add(attLabel);subFrame.add(namLabel);
						subFrame.add(grnLabel);subFrame.add(proLabel);subFrame.add(timLabel);subFrame.add(plaLabel);
						subFrame.add(numLb);subFrame.add(gradeLb);subFrame.add(attLb);subFrame.add(namLb); subFrame.add(depLabel);
						subFrame.add(grnLb);subFrame.add(proLb);subFrame.add(timLb);subFrame.add(plaLb);subFrame.add(depLb);
						subFrame.add(delBut);
						subFrame.setVisible(true);
						
						delBut.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								boolean ok=false;
								try {
							
										ok = main.dao.deleteTimetable(main.id, list.get(selectedNumber));
									
									if (ok == true) {
										JOptionPane.showMessageDialog(null, "정상적으로 삭제되었습니다.");
										main.disposeTimeTableView();
										subFrame.dispose();
										main.showTimetable();
									}
								} catch (Exception evt) {
									evt.printStackTrace();
									JOptionPane.showMessageDialog(null, "일정을 삭제 할 수 없습니다.");
								}
							}
						});
					}
				});
				panel.add(button);
				cnt++;
			}
		}
		panel.setComponentZOrder(label, cnt);
		panel.setComponentZOrder(scrollPane, cnt+1);
		panel.setComponentZOrder(butPanel, cnt+2);
		add(panel);

		 getContentPane().setBackground(Color.WHITE);

		setVisible(true);
	}

	private class ListenSelButtons implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == schedulerBut) {
				main.showCalendarView();
				dispose();
			} else if (e.getSource() == moneyBut) {
				main.showMoneyView();
				dispose();
			} else if (e.getSource() == diaryBut) {
				main.showDiaryView();
				dispose();
			} else if (e.getSource() == addBut)
				main.showAddTimeTableView();
		}

	}
	
	
	class loadButton extends JButton{
		
		public loadButton(String strTime,String arr[],int count) {
			for (int j = 1; j < 6; j++) {
				if (strTime.substring(0, 1).equals(table.getColumnName(j))) {
					String str1=strTime.substring(1,3);
					String str2=strTime.substring(7,9);
					int firstTime = Integer.parseInt(str1);
					int total = Integer.parseInt(str2)-Integer.parseInt(str1);
					str1=strTime.substring(4,6);
					str2=strTime.substring(10, 12);
					firstTime = (firstTime-9)*60+ Integer.parseInt(str1);
					try {
					total= total*60+(Integer.parseInt(str2)-Integer.parseInt(str1));
					}catch(Exception e) {
						e.printStackTrace();
						return;
					}
					setBorderPainted(false);
					setText(arr[3]);
					setOpaque(false);
					setBackground(Color.PINK);
					switch(count) {
					case 0:
						setBackground(Color.PINK);
						break;
					case 1:
						setBackground(Color.ORANGE);
						break;
					case 2:
						setBackground(Color.BLUE);
						break;
					case 3:
						setBackground(Color.RED);
						break;
					case 4:
						setBackground(Color.GREEN);
						break;
					case 5:
						setBackground(Color.YELLOW);
						break;
					case 6:
						setBackground(Color.CYAN);
						break;
					case 7:
						setBackground(Color. LIGHT_GRAY);
						break;
					}
					setBounds(38+(134*(j-1)),122+firstTime,134,total);
				}
			}
		}


		
	}

//	public static void main(String[] args) {
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				new Timetable();
//			}
//		});
//	}
}
