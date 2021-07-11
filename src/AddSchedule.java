import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class AddSchedule extends JFrame {
	private MainProcess main;
	private JPanel contentPane;
	private JTextPane title;
	
	private JPanel startPanel;
	private JLabel startLabel;
	private JTextPane startday;
	
	private JPanel endPanel;
	private JLabel endLabel;
	private JTextPane endday;
	
	private JTextPane content;
	private JButton saveBut;
	private JButton cancelBut;
	
	public AddSchedule(MainProcess main, String date) 
	{
    	this.main=main;
		setTitle("Add-Schedule");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(700, 760);
		setResizable(false);
		setLocationRelativeTo(null);

		 try {
	     	   UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

	     	  } catch (Exception e) {
	     	   e.printStackTrace();
	     	  }
		 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(0, 0, 700, 760);
		panel.setBackground(Color.WHITE);
		contentPane.add(panel);
		panel.setLayout(null);
		
		title = new JTextPane();
		title.setText("일정 제목");
		title.setFont(new Font("굴림체",Font.PLAIN,40));
		title.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		title.setBounds(50, 60, 600, 60);
		panel.add(title);

		startPanel = new JPanel();
		startPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		startPanel.setBounds(50, 140, 400, 30);
		startPanel.setLayout(null);
		startLabel = new JLabel("<html><font color=gray>시작: </html>");
		startLabel.setOpaque(true);
		startLabel.setBackground(Color.WHITE);
		startLabel.setBounds(2,2,35,25);
		startday = new JTextPane();
//		startday.setContentType("text/html");
		startday.setText(date);
		startday.setBounds(37,2,360,25);
		startPanel.add(startLabel);
		startPanel.add(startday);
		panel.add(startPanel);

		
		
		endPanel = new JPanel();
		endPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		endPanel.setBounds(50, 190, 400, 30);
		endPanel.setLayout(null);
		endLabel = new JLabel("<html><font color=gray>종료: </html>");
		endLabel.setOpaque(true);
		endLabel.setBackground(Color.WHITE);
		endLabel.setBounds(2,2,35,25);
		endday = new JTextPane();
//		endday.setContentType("text/html");
		endday.setText(date);
		endday.setBounds(37,2,360,25);
		endPanel.add(endLabel);
		endPanel.add(endday);
		panel.add(endPanel);
		

		content = new JTextPane();
//		content.setContentType("text/html");
		content.setText("일정 내용을 입력해 주세요.");
		content.setFont(new Font("굴림체",Font.PLAIN,15));
		content.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		content.setBounds(50,240,600,400);
		panel.add(content);
		
		saveBut = new JButton("Save");
		saveBut.setBounds(150, 670, 80, 30);
		panel.add(saveBut);

		cancelBut = new JButton("Cancel");
		cancelBut.setBounds(450, 670, 80, 30);
		panel.add(cancelBut);
		
		setVisible(true);
		
		
		cancelBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		saveBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean p1 = Pattern.matches("(^(\\d{4})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$)",startday.getText().trim());
				boolean p2 = Pattern.matches("(^(\\d{4})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$)",endday.getText().trim());
				
				boolean ok=false;
				if((p1&&p2)&&(Integer.parseInt(startday.getText().trim().replaceAll("[^0-9]", ""))<=Integer.parseInt(endday.getText().trim().replaceAll("[^0-9]", "")))) {
				 ok = main.dao.insertSchedule(main.id,title.getText(),startday.getText(),endday.getText(),content.getText());
				 if (ok) {
						JOptionPane.showMessageDialog(null, "저장이 완료되었습니다.");
						main.disposeScheduleView();
						dispose();
						main.showCalendarView();
					} else {
						JOptionPane.showMessageDialog(null, "저장하지 못했습니다.");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "날짜를 확인해주세요");
				}
				
				
			}
		});
	}
}