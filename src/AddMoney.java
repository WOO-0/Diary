import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;

public class AddMoney extends JFrame{
	private MainProcess main;
//	private UserDAO dao=new UserDAO();
	
	private JLabel usagelabel;
	private JTextField usagetxt;
	private JLabel wheusagelabel;
	private JTextField wheusagetxt;
	private JLabel datelabel;
	private JTextField datetxt;
	private JLabel category;
	private JComboBox combox;
	private JLabel moneyLabel;
	private JTextField moneytxt;
	private JRadioButton expense;
	private JRadioButton income;
	private JButton saveBut;
	private JButton cancelBut;
	
	String clsfy="지출";
	
	
	public AddMoney(MainProcess main) {
		this.main=main;
		setTitle("Timetable");
		setSize(400, 360);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		usagelabel = new JLabel("사용 내역:");
		usagelabel.setFont(new Font("굴림체",Font.BOLD,17));
		usagelabel.setBounds(30,20, 90,30);
		usagetxt = new JTextField("ex) 장보기");
		usagetxt.setFont(new Font("굴림체",Font.BOLD,17));
		usagetxt.setBounds(120,20,240,30);
		
		wheusagelabel = new JLabel("사용처: ");
		wheusagelabel.setFont(new Font("굴림체",Font.BOLD,17));
		wheusagelabel.setBounds(30,60,90,30);
		wheusagetxt = new JTextField("ex) OO마트");
		wheusagetxt.setFont(new Font("굴림체",Font.BOLD,17));
		wheusagetxt.setBounds(120,60,240,30);
		
		datelabel = new JLabel("날짜:");
		datelabel.setFont(new Font("굴림체",Font.BOLD,17));
		datelabel.setBounds(30,100,90,30);
		datetxt = new JTextField();
		if((Calendar.getInstance().get(Calendar.MONTH)+1)<10&&(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)<10))
			datetxt.setText(Calendar.getInstance().get(Calendar.YEAR)+"-0"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-0"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		else if((Calendar.getInstance().get(Calendar.MONTH)+1)>10&&(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)<10))
			datetxt.setText(Calendar.getInstance().get(Calendar.YEAR)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-0"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		else if((Calendar.getInstance().get(Calendar.MONTH)+1)>10&&(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)>10))
			datetxt.setText(Calendar.getInstance().get(Calendar.YEAR)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		else
			datetxt.setText(Calendar.getInstance().get(Calendar.YEAR)+"-0"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		datetxt.setFont(new Font("굴림체",Font.BOLD,17));
		datetxt.setBounds(120,100,240,30);
		
		category = new JLabel("분류");
		category.setFont(new Font("굴림체",Font.BOLD,17));
		category.setBounds(30,140,90,30);
		combox=new JComboBox(new String[] {"식비","주거/통신","생활용품","의복/미용","건강/문화","교육/육아","교통/차량","경조사/회비","세금/이자","기타"});
		combox.setFont(new Font("굴림체",Font.BOLD,17));
		combox.setBounds(120,140,150,30);
		
		moneyLabel = new JLabel("금액");
		moneyLabel.setFont(new Font("굴림체",Font.BOLD,17));
		moneyLabel.setBounds(30,180,90,30);
		moneytxt = new JTextField();
		moneytxt.setFont(new Font("굴림체",Font.BOLD,17));
		moneytxt.setBounds(120,180,240,30);
		
		expense = new JRadioButton("지출");
		income = new JRadioButton("수입");
		expense.setSelected(true);
		income.setSelected(true);
		ButtonGroup groupRd = new ButtonGroup();
		groupRd.add(expense);
		groupRd.add(income);
		income.setBounds(150,220,90,30);
		expense.setBounds(210,220,90,30);
		
		saveBut = new JButton("저장");
		saveBut.setBounds(145,290,55,25);
		cancelBut = new JButton("취소");
		cancelBut.setBounds(200,290,55,25);
		
		add(usagelabel);
		add(usagetxt);
		add(wheusagelabel);
		add(wheusagetxt);
		add(datelabel);
		add(datetxt);
		add(category);
		add(combox);
		add(moneyLabel);
		add(moneytxt);
		add(saveBut);
		add(cancelBut);
		add(expense);
		add(income);
		getContentPane().setBackground(Color.WHITE);
		setVisible(true);
		SelectItemListener sItemListener = new SelectItemListener();
		expense.addItemListener(sItemListener);
		income.addItemListener(sItemListener);
		
		saveBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddMoneyData data=new AddMoneyData();
				main.dao.insertMoney(main.id,data);
				
				main.disposeMoneyView();
				dispose();
				main.showMoneyView();
			}
		});
		cancelBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
//	public static void main(String args[]) {
//		new AddMoney();
//	}
	
	
	public class AddMoneyData {
		String use;
		String whereuse;
		String date;
		String category;
		String money;
		String classify;
		public AddMoneyData() {
			this.use=usagetxt.getText();
			this.whereuse=wheusagetxt.getText();
			this.date = datetxt.getText();
			this.category= (String) combox.getSelectedItem();
			this.money = moneytxt.getText();
			this.classify = clsfy;
			
		}
	}
	class SelectItemListener implements ItemListener
    {
          public void itemStateChanged(ItemEvent e)
          {
                 AbstractButton sel = (AbstractButton)e.getItemSelectable();
                 if(e.getStateChange() == ItemEvent.SELECTED)
                 {
                        if ( sel.getText().equals(expense.getText()) )
                        {
                             clsfy=expense.getText();
                        }
                        else if ( sel.getText().equals(income.getText()) )
                        {
                        	clsfy=income.getText();
                        }
                 }
          }

    }
}