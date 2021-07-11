import java.awt.BorderLayout;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import javax.swing.JRadioButton;


public class Join extends JFrame {
	private UserDAO dao= new UserDAO();
	
	private JPanel contentPane;
	private JLabel textLabel;
	private JLabel txtId;
	private JTextField txtId_1;
	private JLabel txtPw;
	private JPasswordField txtPW;
	private JLabel txtRpw;
	private JPasswordField txtRPW;
	private JLabel txtName;
	private JTextField txtName_1;
	private boolean isIdCheck;



	public Join() {
		setTitle("Join");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(290,383);
		setResizable(false);
        setLocationRelativeTo(null);
        
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(0, 0, 289, 535);
		contentPane.add(panel);
		panel.setLayout(null);
		
		textLabel = new JLabel();
		textLabel.setFont(new Font("����ü", Font.BOLD, 19));
		textLabel.setText("ȸ�� ����");
		textLabel.setBounds(97, 21, 116, 31);
		panel.add(textLabel);
		
		txtId = new JLabel();
		txtId.setText("ID :");
		txtId.setBounds(51, 69, 33, 21);
		panel.add(txtId);
		
		txtId_1 = new JTextField();
		txtId_1.setBounds(123, 69, 116, 21);
		panel.add(txtId_1);
		txtId_1.setColumns(10);
		
		JButton btnIDCheck = new JButton("IDCheck");
		btnIDCheck.setBounds(123, 100, 116, 23);
		panel.add(btnIDCheck);
		
		txtPw = new JLabel();
		txtPw.setText("PW :");
		txtPw.setBounds(51, 133, 33, 21);
		panel.add(txtPw);
		
		txtPW = new JPasswordField();
		txtPW.setColumns(10);
		txtPW.setBounds(123, 133, 116, 21);
		panel.add(txtPW);
		
		txtRpw = new JLabel();
		txtRpw.setText("RPW :");
		txtRpw.setBounds(51, 172, 45, 21);
		panel.add(txtRpw);
		
		txtRPW = new JPasswordField();
		txtRPW.setColumns(10);
		txtRPW.setBounds(123, 172, 116, 21);
		panel.add(txtRPW);
		
		txtName = new JLabel();
		txtName.setText("Name :");
		txtName.setBounds(51, 211, 51, 21);
		panel.add(txtName);
		
		txtName_1 = new JTextField();
		txtName_1.setColumns(10);
		txtName_1.setBounds(123, 211, 116, 21);
		panel.add(txtName_1);
		
		JButton btnJoin = new JButton("Join");
		btnJoin.setBounds(51, 285, 73, 23);
		panel.add(btnJoin);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(166, 285, 73, 23);
		panel.add(btnCancel);
		
		setVisible(true);
		
		btnIDCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean pattern = Pattern.matches("^[a-zA-Z0-9]*$",txtId_1.getText().trim());
				System.out.println(pattern);
				if(txtId_1.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "���̵� �Է����ּ���");
				}
				else if(!pattern) {
					JOptionPane.showMessageDialog(null, "���̵�� �����ڿ� ������ ���ո� �����մϴ�.");
				}
				else if(dao.isloginCheck(txtId_1.getText().trim(), "")!=0) {
					JOptionPane.showMessageDialog(null, "����� ������ ���̵��Դϴ�.");
					isIdCheck=true;
				}else {
					JOptionPane.showMessageDialog(null, "����� �Ұ����� ���̵��Դϴ�.");
				}
			}
		});
		
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtId_1.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "���̵� �Է����ּ���");
				}else if(txtPW.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "��й�ȣ�� �Է����ּ���");
				}else if(!txtPW.getText().trim().equals(txtRPW.getText().trim())) {
					JOptionPane.showMessageDialog(null, "��й�ȣ�� Ȯ�����ּ���");
				}else if(txtName_1.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "�̸��� �Է����ּ���");
				}else if(isIdCheck==false) {
					JOptionPane.showMessageDialog(null, "���̵� �ߺ� Ȯ���� ���ּ���");
				}else {
					insertMember(txtId_1.getText(),txtPW.getText(),txtName_1.getText());
				}
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	private void insertMember(String id,String password, String name) {

		boolean ok = dao.insertMember(id,password,name);
		if(ok) {
			JOptionPane.showMessageDialog(null, "������ �Ϸ�Ǿ����ϴ�.");
			dispose();
		}else {
			JOptionPane.showMessageDialog(null,"������ ���������� ó������ �ʾҽ��ϴ�.");
		}
	}
}
