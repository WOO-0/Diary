import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.UIManager;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;


public class MoneyAdmin {
	private MainProcess main;

	private JFrame frame;
	private JPanel westPanel;
	private JLabel Label;
	private JTable westtable;
	private JTable easttable;
	private JTable headerTable;
	private DefaultTableModel westtableModel;
	private DefaultTableModel easttableModel;
	private DefaultTableModel headerTableModel;

	private DrawingPanel drawPanel;
	private DrawingPiePanel piePanel;

	private JPanel eastPanel;
	private JLabel today;
	private JButton addBut;
	private JButton subBut;
	private JButton schedulerBut;
	private JButton timeTableBut;
	private JButton diaryBut;
	ListenSelButtons lForSelButtons = new ListenSelButtons();
	private ArrayList<MoneyData> list;
	int position=-1;
	public MoneyAdmin(MainProcess main) {
		this.main = main;
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1350, 700);
		frame.setResizable(false);
		frame.setTitle("Money");
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		} catch (Exception e) {
			e.printStackTrace();
		}

		westPanel = new JPanel();
		westPanel.setBackground(Color.white);
		westPanel.setBounds(0, 0, 700, 700);
		westPanel.setLayout(null);
		String[] westheaderNames = { "", "�׷�", "��" };
		String[] eastheaderNames = { "", "��¥", "����", "�հ�" };
		String[] mainHeaderNames = { "��¥", "��ü ���" };
		headerTableModel = new DefaultTableModel();
		for (int i = 0; i < mainHeaderNames.length; i++) {
			headerTableModel.addColumn(mainHeaderNames[i]);
		}

		// table model
		westtableModel = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				if (column == 0) {
					return true;
				} else {
					return false;
				}
			}

		};
		easttableModel = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				if (column == 0) {
					return true;
				} else {
					return false;
				}
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
		colModel.getColumn(1).setPreferredWidth(150);
		colModel.getColumn(2).setPreferredWidth(250);
		colModel.getColumn(3).setPreferredWidth(100);
		colModel = (DefaultTableColumnModel) headerTable.getColumnModel();
		colModel.getColumn(0).setPreferredWidth(400);
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

		headerScrollPane.setBounds(10, 10, 675, 30);
		westscrollPane.setBounds(10, 35, 250, 610);
		eastscrollPane.setBounds(255, 35, 430, 610);
		westPanel.add(headerScrollPane);
		westPanel.add(eastscrollPane);
		westPanel.add(westscrollPane);
		frame.add(westPanel);

		eastPanel = new JPanel();
		eastPanel.setBounds(700, 0, 700, 700);
		eastPanel.setBackground(Color.WHITE);
		eastPanel.setLayout(null);

		drawPanel = new DrawingPanel();
		chartCalculater();
		drawPanel.setBounds(0, 270, 650, 400);
		eastPanel.add(drawPanel);

		piePanel = new DrawingPiePanel();
		pieCalculater();
		piePanel.setBounds(200, 50, 450, 300);
		eastPanel.add(piePanel);

		today = new JLabel("<html><Font size =20><Center>" + Calendar.getInstance().get(Calendar.YEAR) + "�� "
				+ (Calendar.getInstance().get(Calendar.MONTH) + 1) + "��</Center></html>");
		// today.setFont(new Font());
		today.setBounds(10, 50, 200, 40);
		addBut = new JButton("+");
		addBut.setBounds(10, 10, 35, 35);
		subBut = new JButton("-");
		subBut.setBounds(50, 10, 35, 35);
		schedulerBut = new JButton("Scheduler");
		schedulerBut.setBounds(360, 10, 90, 35);
		schedulerBut.addActionListener(lForSelButtons);
		timeTableBut = new JButton("TimeTable");
		timeTableBut.setBounds(455, 10, 90, 35);
		timeTableBut.addActionListener(lForSelButtons);
		diaryBut = new JButton("Diary");
		diaryBut.setBounds(550, 10, 70, 35);
		diaryBut.addActionListener(lForSelButtons);
		eastPanel.add(today);
		eastPanel.add(addBut);
		eastPanel.add(subBut);
		eastPanel.add(schedulerBut);
		eastPanel.add(timeTableBut);
		eastPanel.add(diaryBut);

		frame.add(eastPanel);
		frame.setVisible(true);
		addBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.addMoneyView();
			}
		});
		subBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean ok=false;
				try {
					if( position<0)
						JOptionPane.showMessageDialog(null, "��¥�� �������ּ���");
					else
						ok = main.dao.deleteMoney(main.id, list.get(position));
					
					if (ok == true) {
						JOptionPane.showMessageDialog(null, "���������� �����Ǿ����ϴ�.");
						dispose();
						main.showMoneyView();
					}
				} catch (Exception evt) {
					evt.printStackTrace();
					JOptionPane.showMessageDialog(null, "������ ���� �� �� �����ϴ�.");
				}

			}
		});
		
		westtable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = westtable.getSelectedRow();
				int column = westtable.getSelectedColumn();
				String value = (String) westtable.getValueAt(row, 1);
				int sum = 0;
				easttableModel.setNumRows(0);
				if (value.equals("<��ü>")) {
					for (int i = 0; i < list.size(); i++) {
						if(list.get(i).classify.equals("����"))
							easttableModel.addRow(new String[] { Integer.toString(i + 1), list.get(i).date, list.get(i).use,"-"+list.get(sum + i).money });
						else
							easttableModel.addRow(new String[] { Integer.toString(i + 1), list.get(i).date, list.get(i).use,"+"+list.get(sum + i).money });
					}
				} else {
					for (int i = 1; i <= row - 1; i++) {
						sum += Integer.parseInt((String) westtable.getValueAt(i, 2));
					}
					for (int i = 0; i < Integer.parseInt((String) westtable.getValueAt(row, 2)); i++) {
						if(list.get(sum+i).classify.equals("����"))
							easttableModel.addRow(new String[] { Integer.toString(i + 1), list.get(sum + i).date, list.get(sum + i).use,"-"+list.get(sum + i).money });
						else
							easttableModel.addRow(new String[] { Integer.toString(i + 1), list.get(sum + i).date, list.get(sum + i).use,"+"+list.get(sum + i).money });
					}	
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
					position = sum + (Integer.parseInt((String) easttable.getValueAt(row, 0)));;
				}

			}
		});
	}

	private class ListenSelButtons implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == schedulerBut)
				main.showCalendarView();
			else if (e.getSource() == diaryBut)
				main.showDiaryView();
			else if (e.getSource() == timeTableBut)
				main.showTimetable();
			frame.dispose();
		}

	}

	public void loadData() {
		list = main.dao.loadMoneyData(main.id);
		
		westtableModel.addRow(new Object[] { "1", "<��ü>", Integer.toString(list.size()) });
		for (int i = 0, cnt = 1, num = 2; i < list.size(); i++) {
			if (i + 1 == list.size()) {
				westtableModel.addRow(new String[] { Integer.toString(num), list.get(i).category, Integer.toString(cnt)});
				break;
			}
			if (list.get(i).category.equals(list.get(i + 1).category) == false) {
				westtableModel.addRow(new String[] { Integer.toString(num), list.get(i).category,Integer.toString(cnt)});
				cnt = 1;
				num++;
			} else
				cnt++;
		}
	}
	public void chartCalculater() {
		int jan=0, feb=0, mar=0, apr=0, may=0, jun=0, july=0, aug=0, sep=0, oct=0, nov=0, dec=0;
		for(int i=0;i<list.size();i++) {
			String month= list.get(i).date.substring(0, 7);
			String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
			if(month.equals(year+"-01")&&list.get(i).classify.equals("����")) {
				jan+=Integer.parseInt(list.get(i).money);
			}else if(month.equals(year+"-02")&&list.get(i).classify.equals("����")) {
				feb+=Integer.parseInt(list.get(i).money);
			}else if(month.equals(year+"-03")&&list.get(i).classify.equals("����")) {
				mar+=Integer.parseInt(list.get(i).money);
			}else if(month.equals(year+"-04")&&list.get(i).classify.equals("����")) {
				apr+=Integer.parseInt(list.get(i).money);
			}else if(month.equals(year+"-05")&&list.get(i).classify.equals("����")) {
				may+=Integer.parseInt(list.get(i).money);
			}else if(month.equals(year+"-06")&&list.get(i).classify.equals("����")) {
				jun+=Integer.parseInt(list.get(i).money);
			}else if(month.equals(year+"-07")&&list.get(i).classify.equals("����")) {
				july+=Integer.parseInt(list.get(i).money);
			}else if(month.equals(year+"-08")&&list.get(i).classify.equals("����")) {
				aug+=Integer.parseInt(list.get(i).money);
			}else if(month.equals(year+"-09")&&list.get(i).classify.equals("����")) {
				sep+=Integer.parseInt(list.get(i).money);
			}else if(month.equals(year+"-10")&&list.get(i).classify.equals("����")) {
				oct+=Integer.parseInt(list.get(i).money);
			}else if(month.equals(year+"-11")&&list.get(i).classify.equals("����")) {
				nov+=Integer.parseInt(list.get(i).money);
			}else if(month.equals(year+"-12")&&list.get(i).classify.equals("����")) {
				dec+=Integer.parseInt(list.get(i).money);
			}
			drawPanel.setNumber(jan/10000, feb/10000, mar/10000, apr/10000, may/10000, jun/10000, july/10000, aug/10000, sep/10000, oct/10000, nov/10000, dec/10000);
		}
	}
	public void pieCalculater() {
		int num1=0;
		int num2=0;
		int num3=0;
		int num4=0;
		int num5=0;
		int num6=0;
		int num7=0;
		int num8=0;
		int num9=0;
		int num10=0;
		for(int i=0;i<list.size();i++) {
			if(list.get(i).category.equals("�ĺ�")&&list.get(i).classify.equals("����")) {
				num1+=Integer.parseInt(list.get(i).money);
			}else if(list.get(i).category.equals("�ְ�/���")&&list.get(i).classify.equals("����")) {
				num2+=Integer.parseInt(list.get(i).money);
			}else if(list.get(i).category.equals("��Ȱ��ǰ")&&list.get(i).classify.equals("����")) {
				num3+=Integer.parseInt(list.get(i).money);
			}else if(list.get(i).category.equals("�Ǻ�/�̿�")&&list.get(i).classify.equals("����")) {
				num4+=Integer.parseInt(list.get(i).money);
			}else if(list.get(i).category.equals("�ǰ�/��ȭ")&&list.get(i).classify.equals("����")) {
				num5+=Integer.parseInt(list.get(i).money);
			}else if(list.get(i).category.equals("����/����")&&list.get(i).classify.equals("����")) {
				num6+=Integer.parseInt(list.get(i).money);
			}else if(list.get(i).category.equals("����/����")&&list.get(i).classify.equals("����")) {
				num7+=Integer.parseInt(list.get(i).money);
			}else if(list.get(i).category.equals("������/ȸ��")&&list.get(i).classify.equals("����")) {
				num8+=Integer.parseInt(list.get(i).money);
			}else if(list.get(i).category.equals("����/����")&&list.get(i).classify.equals("����")) {
				num9+=Integer.parseInt(list.get(i).money);
			}else if(list.get(i).category.equals("��Ÿ")&&list.get(i).classify.equals("����")) {
				num10+=Integer.parseInt(list.get(i).money);
			}
			piePanel.setNumbers(num1, num2, num3, num4, num5, num6, num7, num8, num9, num10);
		}
	}

//	public static void main(String args[]) {
//		new MoneyAdmin();
//	}

	public void dispose() {
		frame.dispose();
	}
}

class MoneyData {
	String use;
	String whereuse;
	String date;
	String category;
	String money;
	String classify;

	public void setData(String use, String whereuse, String date, String category, String money, String classfy) {
		this.use = use;
		this.whereuse = whereuse;
		this.date = date;
		this.category = category;
		this.money = money;
		this.classify = classfy;
	}
}

class DrawingPanel extends JPanel {
	int jan, feb, mar, apr, may, jun, july, aug, sep, oct, nov, dec;

	public void paint(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		g.drawLine(50, 350, 630, 350);
		for (int cnt = 1; cnt < 11; cnt++) {
			g.drawString(cnt * 10 + "��", 15, 360 - 30 * cnt);
			g.drawLine(50, 350 - 30 * cnt, 630, 350 - 30 * cnt);
		}
		g.drawLine(50, 20, 50, 350);
		g.drawString("1��", 55, 370);
		g.drawString("2��", 105, 370);
		g.drawString("3��", 155, 370);
		g.drawString("4��", 205, 370);
		g.drawString("5��", 255, 370);
		g.drawString("6��", 305, 370);
		g.drawString("7��", 355, 370);
		g.drawString("8��", 405, 370);
		g.drawString("9��", 455, 370);
		g.drawString("10��", 505, 370);
		g.drawString("11��", 555, 370);
		g.drawString("12��", 605, 370);
		g.setColor(Color.RED);
		if (jan > 0)
			g.fillRect(60, 350 - jan * 3, 10, jan * 3);
		if (feb > 0)
			g.fillRect(110, 350 - feb * 3, 10, feb * 3);
		if (mar > 0)
			g.fillRect(160, 350 - mar * 3, 10, mar * 3);
		if (apr > 0)
			g.fillRect(210, 350 - apr * 3, 10, apr * 3);
		if (may > 0)
			g.fillRect(260, 350 - may * 3, 10, may * 3);
		if (jun > 0)
			g.fillRect(310, 350 - jun * 3, 10, jun * 3);
		if (july > 0)
			g.fillRect(360, 350 - july * 3, 10, july * 3);
		if (aug > 0)
			g.fillRect(410, 350 - aug * 3, 10, aug * 3);
		if (sep > 0)
			g.fillRect(460, 350 - sep * 3, 10, sep * 3);
		if (oct > 0)
			g.fillRect(510, 350 - oct * 3, 10, oct * 3);
		if (nov > 0)
			g.fillRect(560, 350 - nov * 3, 10, nov * 3);
		if (dec > 0)
			g.fillRect(610, 350 - dec * 3, 10, dec * 3);
	}

	void setNumber(int jan, int feb, int mar, int apr, int may, int jun, int july, int aug, int sep, int oct, int nov,
			int dec) {
		this.jan = jan;
		this.feb = feb;
		this.mar = mar;
		this.apr = apr;
		this.may = may;
		this.jun = jun;
		this.july = july;
		this.aug = aug;
		this.sep = sep;
		this.oct = oct;
		this.nov = nov;
		this.dec = dec;
	}
}

class DrawingPiePanel extends JPanel {
	int num1; // �ĺ�
	int num2; // �ְ�/���
	int num3; // ��Ȱ��ǰ
	int num4; // �Ǻ�/�̿�
	int num5; // �ǰ�/��ȭ
	int num6; // ����/����
	int num7; // ����/����
	int num8; // ������/ȸ��
	int num9; // ����/����
	int num10; // ��Ÿ

	public void paint(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		// ���� �Էµ����ʾ����� return;
//		if ((num1 < 0) || (num2 < 0) || (num3 < 0) || (num4 < 0))
//			return;
		// ��ü ���� ���Ѵ�
		int total = num1 + num2 + num3 + num4 +num5 +num6 + num7 +num8 + num9 + num10;
		if (total == 0)
			return;
		// ��ü������ ������ ����.
		// arc4 = ��ü - (arc1+arc2+arc3)�� ����
		int arc1 = (int) 360.0 * num1 / total;
		int arc2 = (int) 360.0 * num2 / total;
		int arc3 = (int) 360.0 * num3 / total;
		int arc4 = (int) 360.0 * num4 / total;
		int arc5 = (int) 360.0 * num5 / total;
		int arc6 = (int) 360.0 * num6 / total;
		int arc7 = (int) 360.0 * num7 / total;
		int arc8 = (int) 360.0 * num8 / total;
		int arc9 = (int) 360.0 * num9 / total;
		int arc10 = (int) 360.0 * num10 / total;
		g.setColor(new Color(204,61,61));// ��������
		g.fillArc(50, 20, 200, 200, 0, arc1);// (x��,y��,������,������,���۰�,����) - ��ȣ�� �׸�
		g.setColor(new Color(242,150,97));// ��������
		g.fillArc(50, 20, 200, 200, arc1, arc2);// (x��,y��,������,������,���۰�,����) - ��ȣ�� �׸�
		g.setColor(new Color(255,224,140));// ��������
		g.fillArc(50, 20, 200, 200, arc1 + arc2, arc3);// (x��,y��,������,������,���۰�,����) - ��ȣ�� �׸�
		g.setColor(new Color(134,229,127));// ��������
		g.fillArc(50, 20, 200, 200, arc1 + arc2 + arc3, arc4);// (x��,y��,������,������,���۰�,����) - ��ȣ�� �׸�
		g.setColor(new Color(92,209,229));// ��������
		g.fillArc(50, 20, 200, 200, arc1 + arc2 + arc3+arc4, arc5);// (x��,y��,������,������,���۰�,����) - ��ȣ�� �׸�
		g.setColor(new Color(0,84,255));// ��������
		g.fillArc(50, 20, 200, 200, arc1 + arc2 + arc3+arc4+arc5, arc6);// (x��,y��,������,������,���۰�,����) - ��ȣ�� �׸�
		g.setColor(Color.PINK);// ��������
		g.fillArc(50, 20, 200, 200, arc1 + arc2 + arc3+arc4+arc5+arc6, arc7);// (x��,y��,������,������,���۰�,����) - ��ȣ�� �׸�
		g.setColor(new Color(165,102,255));// ��������
		g.fillArc(50, 20, 200, 200, arc1 + arc2 + arc3+arc4+arc5+arc6+arc7, arc8);// (x��,y��,������,������,���۰�,����) - ��ȣ�� �׸�
		g.setColor(new Color(206,251,201));// ��������
		g.fillArc(50, 20, 200, 200, arc1 + arc2 + arc3+arc4+arc5+arc6+arc7+arc8, arc9);// (x��,y��,������,������,���۰�,����) - ��ȣ�� �׸�
		g.setColor(new Color(102,0,51));// ��������
		g.fillArc(50, 20, 200, 200, arc1 + arc2 + arc3+arc4+arc5+arc6+arc7+arc8+arc9, 360-(arc1+arc2+arc3+arc4+arc5+arc6+arc7+arc8+arc9));// (x��,y��,������,������,���۰�,����) - ��ȣ�� �׸�
		
		g.setColor(Color.BLACK);// ��������
		g.setFont(new Font("����ü", Font.PLAIN, 12));// ��Ʈ ����
		g.drawString("�ĺ�: ����", 300, 30);// ����(legend)
		g.drawString("�ְ�/���: ��Ȳ", 300, 50);// ����(legend)
		g.drawString("��Ȱ��ǰ: ���", 300, 70);// ����(legend)
		g.drawString("�Ǻ�/�̿�: �ʷ�", 300, 90);// ����(legend)
		g.drawString("�ǰ�/��ȭ: �ϴ�", 300, 110);
		g.drawString("����/����: �Ķ�", 300, 130);
		g.drawString("����/����: ��ũ", 300, 150);
		g.drawString("������/ȸ��: ����", 300, 170);
		g.drawString("����/����: ����", 300, 190);
		g.drawString("��Ÿ: �ڵ�", 300, 210);
	}

	// ���ڰ� �Է¹޴� �޼ҵ�
	void setNumbers(int num1, int num2, int num3, int num4, int num5, int num6, int num7, int num8, int num9,
			int num10) {
		this.num1 = num1;
		this.num2 = num2;
		this.num3 = num3;
		this.num4 = num4;
		this.num5 = num5;
		this.num6 = num6;
		this.num7 = num7;
		this.num8 = num8;
		this.num9 = num9;
		this.num10 = num10;
	}
}