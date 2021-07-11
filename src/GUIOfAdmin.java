import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class GUIOfAdmin {
	GUIOfAdmin() {
		JFileChooser chooser = new JFileChooser("./");
		chooser.setFileFilter(new FileNameExtensionFilter("Excel 파일 (*.xls)", new String[] { "xls" }));
		int ret = chooser.showOpenDialog(null);
		if (ret != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "경로를 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
			return;
		}
		File file = chooser.getSelectedFile();
		loadFile(file);
	}

	public void loadFile(File file) {
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; // 드라이버
		final String DB_URL = "jdbc:mysql://localhost/s21511765?&uesUnicode=true&characterEncoding=utf8&useSSL=false";
		final String USER_NAME = "root"; // DB에 접속할 사용자 이름을 상수로 정의
		final String PASSWORD = "moojin";
		Connection conn; // DB에 접근하게 해주는 객체
		PreparedStatement pstmt;
		ResultSet rs;
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Workbook workbook = Workbook.getWorkbook(file);
			Sheet sheet = workbook.getSheet(0);
			int endIdx = sheet.getColumn(1).length - 1;
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			System.out.println("MYSQL Connection");

			String sql = "insert into alltimetable("
					+ "number,grade,attribute,name,gradenumber,professor,time,place,department)"
					+ "values(?,?,?,?,?,?,?,?,?)";

			for (int i = 1; i <= endIdx; i++) {
				try {
				Integer.parseInt(sheet.getCell(0, i).getContents());
				String number = sheet.getCell(0, i).getContents();
				String grade = sheet.getCell(1, i).getContents();
				String attribute = sheet.getCell(2, i).getContents();
				String name = sheet.getCell(3, i).getContents();
				String gradenumber = sheet.getCell(4, i).getContents();
				String professor = sheet.getCell(5, i).getContents();
				String time = sheet.getCell(6, i).getContents();
				String place = sheet.getCell(7, i).getContents();
				String department = sheet.getCell(8, i).getContents();

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, number);
				pstmt.setString(2, grade);
				pstmt.setString(3, attribute);
				pstmt.setString(4, name);
				pstmt.setString(5, gradenumber);
				pstmt.setString(6, professor);
				pstmt.setString(7, time);
				pstmt.setString(8, place);
				pstmt.setString(9, department);
				pstmt.executeUpdate();
				}catch(Exception e) {
					continue;
				}
			}
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}