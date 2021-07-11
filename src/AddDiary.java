import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
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

public class AddDiary extends JFrame {
//	private UserDAO dao;
	private MainProcess main;
	private JPanel contentPane;
	private JTextPane title;
	private JPanel dayPanel;
	private JLabel dayLabel;
	private JTextPane dayText;
	private JTextPane content;
	private JButton saveBut;
	private JButton cancelBut;
	public AddDiary(MainProcess main) 
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
		title.setText("일기 제목");
		title.setFont(new Font("굴림체",Font.BOLD,35));
		title.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		title.setBounds(50, 60, 600, 60);
		panel.add(title);

		
		dayPanel = new JPanel();
		dayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		dayPanel.setBounds(50, 140, 400, 30);
		dayPanel.setLayout(null);
		dayLabel = new JLabel("<html><font color=gray>날짜: </html>");
		dayLabel.setOpaque(true);
		dayLabel.setBackground(Color.WHITE);
		dayLabel.setBounds(2,2,35,25);
		dayText = new JTextPane();
		if((Calendar.getInstance().get(Calendar.MONTH)+1)<10&&(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)<10))
			dayText.setText(Calendar.getInstance().get(Calendar.YEAR)+"-0"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-0"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		else if((Calendar.getInstance().get(Calendar.MONTH)+1)>10&&(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)<10))
			dayText.setText(Calendar.getInstance().get(Calendar.YEAR)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-0"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		else if((Calendar.getInstance().get(Calendar.MONTH)+1)>10&&(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)>10))
			dayText.setText(Calendar.getInstance().get(Calendar.YEAR)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		else
			dayText.setText(Calendar.getInstance().get(Calendar.YEAR)+"-0"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		dayText.setBounds(37,2,360,25);
		dayPanel.add(dayLabel);
		dayPanel.add(dayText);
		panel.add(dayPanel);
		

		content = new JTextPane();
		content.setText("내용을 입력해 주세요.");
		content.setFont(new Font("굴림체",Font.PLAIN,15));
		content.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		content.setBounds(50,190,600,450);
		panel.add(content);
		
		saveBut = new JButton("Save");
		saveBut.setBounds(150, 670, 80, 30);
		panel.add(saveBut);

		cancelBut = new JButton("Cancel");
		cancelBut.setBounds(450, 670, 80, 30);
		panel.add(cancelBut);
		
		
		setVisible(true);
		main.dao=new UserDAO();
		saveBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean p = Pattern.matches("(^(\\d{4})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$)",dayText.getText().trim());
				boolean ok=false;
				if(p) {
				 ok = main.dao.insertDiary(main.id,dayText.getText(),title.getText(),content.getText());
				 if (ok) {
						JOptionPane.showMessageDialog(null, "저장이 완료되었습니다.");
						main.disposeDiaryView();
						dispose();
						main.showDiaryView();
					} else {
						JOptionPane.showMessageDialog(null, "저장하지 못했습니다.");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "날짜를 확인해주세요");
				}
				
				
			}
		});
		cancelBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
	}
//    public static void main(String args[]) {
//    	new AddDiary();
//    	
//    }
}