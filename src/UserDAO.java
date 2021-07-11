import java.sql.*;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;


public class UserDAO { // DAO : 데이터베이스 접근 객체
	private final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; // 드라이버
	private final String DB_URL = "jdbc:mysql://localhost/s21511765?&uesUnicode=true&characterEncoding=utf8&useSSL=false";
	private final String USER_NAME = "root"; // DB에 접속할 사용자 이름을 상수로 정의
	private final String PASSWORD = "moojin";
	private Connection conn; // DB에 접근하게 해주는 객체
	private PreparedStatement pstmt;
	private ResultSet rs;

	public UserDAO() {// 생성자가 실행될 때마다 자동으로 DB연결이 이루어 질 수 있도록 함.
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			System.out.println("MYSQL Connection");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int isloginCheck(String userID, String userPassword) {
		String sql = "SELECT password FROM USER_info WHERE ID =?";
		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString(1).equals(userPassword))
					return 1;
				else
					return 0;
			}
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2;
	}

	public boolean insertSchedule(String id,String title,String startdate, String enddate, String contents) {
		boolean ok = false;
		try {
			String sql = "insert into schedule(" + "id,title,startdate,enddate,contents)" + "values(?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, title);
			pstmt.setString(3, startdate);
			pstmt.setString(4, enddate);
			pstmt.setString(5, contents);
			pstmt.executeUpdate();
			ok = true;
		} catch (Exception e) {
			ok = false;
			e.printStackTrace();
		}
		return ok;
	}
	
	public ArrayList<ScheduleData> loadSchedule(String id) {
		try {
			String sql = "SELECT * FROM schedule where id=? ORDER BY startdate";
			ArrayList<ScheduleData> list = new ArrayList<ScheduleData>();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ScheduleData data=new ScheduleData();
				data.setData(rs.getString("title"), rs.getString("startdate"), rs.getString("enddate"), rs.getString("contents"));
				list.add(data);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean deleteSchedule(String id,ScheduleData data) {
		boolean ok = false;
		try {
			String sql = "DELETE from schedule WHERE (id, title, startdate, enddate, contents)=(?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, data.title);
			pstmt.setString(3, data.startdate);
			pstmt.setString(4, data.enddate);
			pstmt.setString(5, data.contents);
			pstmt.executeUpdate();
			ok=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok;
	}
	
	
	
	public boolean insertMember(String id,String password,String name) {
		boolean ok = false;
		try {
			String sql = "insert into user_info(" + "id,password,name)" + "values(?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			pstmt.setString(3, name);
			pstmt.executeUpdate();
			ok = true;
		} catch (Exception e) {
			ok = false;
			e.printStackTrace();
		}
		return ok;
	}

	public boolean insertDiary(String id, String day, String title, String content) {
		boolean ok = false;
		try {
			String sql = "insert into diary(" + "id,date,title,contents)" + "values(?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, day);
			pstmt.setString(3, title);
			pstmt.setString(4, content);
			pstmt.executeUpdate();
			ok = true;
		} catch (Exception e) {
			ok = false;
			e.printStackTrace();
		}
		return ok;
	}

	public boolean loadDiary(String id, ArrayList<String> title, ArrayList<String> date, ArrayList<String> contents) {
		boolean ok = false;
		try {
			String sql = "SELECT * FROM diary where id=? ORDER BY date DESC";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				title.add(rs.getString("title"));
				date.add(rs.getString("date"));
				contents.add(rs.getString("contents"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok;
	}
	
	public boolean deleteDiary(String id,String title,String date,String contents) {
		boolean ok = false;
		try {
			String sql = "DELETE FROM diary where (id,title,date,contents)=(?,?,?,?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, title);
			pstmt.setString(3, date);
			pstmt.setString(4, contents);
			pstmt.executeUpdate();
			ok=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok;
	}
	
	public boolean deleteMoney(String id,MoneyData data) {
		boolean ok = false;
		try {
			String sql = "DELETE from money WHERE (id, whyuse, wheuse, moneydate, category, money, classify)=(?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, data.use);
			pstmt.setString(3, data.whereuse);
			pstmt.setString(4, data.date);
			pstmt.setString(5, data.category);
			pstmt.setString(6, data.money);
			pstmt.setString(7, data.classify);
			pstmt.executeUpdate();
			ok=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok;
	}
	

	public String[] loadTableHeader() {
		try {
			String sql = "SELECT * FROM alltimetable";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.last();
			int rowCount = rs.getRow();
			ResultSetMetaData metaInfo = rs.getMetaData();
			int count = metaInfo.getColumnCount();

			String header[] = new String[count];

			for (int i = 0; i < count; i++) {
				header[i] = metaInfo.getColumnName(i + 1);
			}
			return header;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String[][] loadTableData(String header[]) {
		try {
			String sql = "SELECT * FROM alltimetable";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			rs.last();
			int rowCount = rs.getRow();
			String data[][] = new String[rowCount - 1][header.length];
			int cnt = 0;
			rs.first();
			while (rs.next()) {
				for (int i = 0; i < header.length; i++) {
					data[cnt][i] = rs.getString(header[i]);
				}
				cnt++;
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean saveTableData(String id, String header[], String data[]) {
		boolean ok = false;
		try {
			String sql = "insert into usertimetable(id" + "," + header[0] + "," + header[1] + "," + header[2] + ","
					+ header[3] + "," + header[4] + "," + header[5] + "," + header[6] + "," + header[7] + ","
					+ header[8] + ")values(?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, data[0]);
			pstmt.setString(3, data[1]);
			pstmt.setString(4, data[2]);
			pstmt.setString(5, data[3]);
			pstmt.setString(6, data[4]);
			pstmt.setString(7, data[5]);
			pstmt.setString(8, data[6]);
			pstmt.setString(9, data[7]);
			pstmt.setString(10, data[8]);
			pstmt.executeUpdate();
			ok = true;
		} catch (Exception e) {
			ok = false;
			e.printStackTrace();
		}
		return ok;
	}

	public ArrayList<String[]> loadUserTableData(String id) {
		try {
			String sql = "SELECT * FROM usertimetable where id=?";
			ArrayList<String[]> list = new ArrayList<String[]>();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String arr[]=new String[9];
				arr[0]=(rs.getString("number"));
				arr[1]=(rs.getString("grade"));
				arr[2]=(rs.getString("attribute"));
				arr[3]=(rs.getString("name"));
				arr[4]=(rs.getString("gradenumber"));
				arr[5]=(rs.getString("professor"));
				arr[6]=(rs.getString("time"));
				arr[7]=(rs.getString("place"));
				arr[8]=(rs.getString("department"));
				list.add(arr);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean deleteTimetable(String id,String[] data) {
		boolean ok = false;
		try {
			String sql = "DELETE from usertimetable WHERE (id, number, grade, attribute, name, gradenumber, professor,time,place,department)=(?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, data[0]);
			pstmt.setString(3, data[1]);
			pstmt.setString(4, data[2]);
			pstmt.setString(5, data[3]);
			pstmt.setString(6, data[4]);
			pstmt.setString(7, data[5]);
			pstmt.setString(8, data[6]);
			pstmt.setString(9, data[7]);
			pstmt.setString(10, data[8]);
			pstmt.executeUpdate();
			ok=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok;
	}
	
	
	public boolean insertMoney(String id, AddMoney.AddMoneyData data) {
		boolean ok = false;
		try {
			String sql = "insert into money"
					+ " values(?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, data.use);
			pstmt.setString(3, data.whereuse);
			pstmt.setString(4, data.date);
			pstmt.setString(5, data.category);
			pstmt.setString(6, data.money);
			pstmt.setString(7, data.classify);
			pstmt.executeUpdate();
			ok = true;
		} catch (Exception e) {
			ok = false;
			e.printStackTrace();
		}
		return ok;
	}
	public ArrayList<MoneyData> loadMoneyData(String id){
		try {
			String sql = "SELECT * FROM money where id=? ORDER BY category";
			ArrayList<MoneyData> list = new ArrayList<MoneyData>();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MoneyData data=new MoneyData();
				data.setData(rs.getString("whyuse"), rs.getString("wheuse"), rs.getString("moneydate"), rs.getString("category"), rs.getString("money"), rs.getString("classify"));
				list.add(data);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
