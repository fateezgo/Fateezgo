package tw.com.fateezgo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbHelper {
	Connection conn = null;
	DbHelper() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://m.snpy.org/a105t2?user=a105t2&password=a5t2#&useUnicode=true&characterEncoding=UTF-8");		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	String query(String qStr) {
		String str = "";
		Statement s;
		try {
			s = conn.createStatement();
			ResultSet rs = s.executeQuery(qStr);
			int columnCount = rs.getMetaData().getColumnCount();
			System.out.println("column count: " + columnCount);
			while (rs.next()) {
				for (int i = 0; i < columnCount; i++) {
					str += rs.getString(i+1);
					str += ",";
				}
				str += "\n";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return str;
	}
	public void finish() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

