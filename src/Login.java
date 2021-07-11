import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.*;

public class Login extends JFrame {
	private MainProcess main;
	
	JLabel pwLabel, idLabel;
	JTextField id;
	JPasswordField passwd;
	JPanel idPanel, paPanel, loginPanel;
	JButton loginBut, joinBut;
	JTextArea content;
	private boolean loginCheck;

	public Login() {
		super("Login");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLayout(new FlowLayout());
		idPanel = new JPanel();
		paPanel = new JPanel();
		idLabel = new JLabel("ID:");
		pwLabel = new JLabel("PWD:");
		id = new JTextField(10);
		passwd = new JPasswordField(10);
		idPanel.add(idLabel);
		idPanel.add(id);
		paPanel.add(pwLabel);
		paPanel.add(passwd);
		loginPanel = new JPanel();
		loginBut = new JButton("로그인");
		joinBut = new JButton("회원가입");
		loginPanel.add(loginBut);
		loginPanel.add(joinBut);
		add(idPanel);
		add(paPanel);
		add(loginPanel);

		setSize(250, 180);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);

		loginBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ("Admin".equals(id.getText()) && "Admin".equals(new String(passwd.getPassword()))) {
					new GUIOfAdmin();
					return;
				}
				isLogin();
			}
		});
		joinBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.showJoinView();
			}
		});
	}

	public void isLogin() {
		if (main.dao.isloginCheck(id.getText(), new String(passwd.getPassword())) == 1) {
			loginCheck = true;
		} else if (main.dao.isloginCheck(id.getText(), new String(passwd.getPassword())) == 0) {
			JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.");
			passwd.setText("");
		} else if (main.dao.isloginCheck(id.getText(), new String(passwd.getPassword())) == -1) {
			JOptionPane.showMessageDialog(null, "ID가 존재하지 않습니다.");
			id.setText("");
			passwd.setText("");
		}
		if (loginCheck) {
			main.sendID(id.getText());
			main.showCalendarView(); // 메인창 메소드를 이용해 창뛰우기
//			main.showDiaryView(main);
		}
	}

	public void setMain(MainProcess main) {
		this.main = main;
	}

}
