package swing_model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BMIDao {
	
	private static BMIDao b_dao;
	
	private BMIDao() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static BMIDao getInstance() {
		if (b_dao == null) {
			b_dao = new BMIDao();
		}
		
		return b_dao;
	}
	
	public Connection getConnection() throws SQLException {
		Connection con = null;
		
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String id = "scott";
		String pw = "tiger";
		
		con = DriverManager.getConnection(url, id, pw);
		
		return con;
	}
	
	public List<HistoryVO> selectAllHistory() throws SQLException {
		List<HistoryVO> list = new ArrayList<HistoryVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			
			String selectAllHistory = 
				"SELECT name, TO_CHAR(input_date, 'yyyy-mm-dd') input_date, bmi_result, height, weight, bmi_num "
				+ "FROM bmi_history "
				+ "ORDER BY input_date DESC";
			pstmt = con.prepareStatement(selectAllHistory);
			
			rs = pstmt.executeQuery();

			HistoryVO tempVO = null;
			while(rs.next()) {
				tempVO = new HistoryVO(rs.getString("name"), rs.getString("input_date"),
						rs.getDouble("height"), rs.getDouble("weight"), rs.getDouble("bmi_num"), rs.getString("bmi_result"));
				list.add(tempVO);
			}
			
		} finally {
			if (rs != null) { rs.close(); }
			if (pstmt != null) { pstmt.close(); }
			if (con != null) { con.close(); }
		}
		
		return list;
	}
	
	public void insertOneData(HistoryInsertVO hivo) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
				
		try {

			con = getConnection();
			
			String insertOneData = "INSERT INTO bmi_history(name,height, weight, bmi_num, bmi_result)"
					+ "VALUES(?,?,?,?,?)";
			pstmt = con.prepareStatement(insertOneData);
			pstmt.setString(1, hivo.getName());
			pstmt.setDouble(2, hivo.getHeight());
			pstmt.setDouble(3, hivo.getWeight());
			pstmt.setDouble(4, hivo.getBmiNum());
			pstmt.setString(5, hivo.getBmiResult());
			
			pstmt.executeUpdate();
			
		} finally {
			if (pstmt != null) { pstmt.close(); }
			if (con != null) { con.close(); }
		}
	}
	
	public int insertLoadData(List<HistoryVO> list) throws SQLException {
		int cnt = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		con = getConnection();
		String insertLoadData = 
			"INSERT INTO bmi_history(name, input_date, bmi_result, height, weight, bmi_num) "
			+ "VALUES(?,?,?,?,?,?)";
		HistoryVO tempVO = null;
		for(int i=0; i<list.size(); i++) {
			pstmt = con.prepareStatement(insertLoadData);

			tempVO = list.get(i);
			
			pstmt.setString(1, tempVO.getName());
			pstmt.setString(2, tempVO.getDate());
			pstmt.setString(3, tempVO.getBmiResult());
			pstmt.setDouble(4, tempVO.getHeight());
			pstmt.setDouble(5, tempVO.getWeight());
			pstmt.setDouble(6, tempVO.getBmiNum());
			
			cnt = cnt + pstmt.executeUpdate();
			
			pstmt.close();
		}
		
		return cnt;
	}
}
