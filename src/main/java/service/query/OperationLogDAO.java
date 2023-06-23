package Service.query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Service.beans.OperationLog;

public class OperationLogDAO {

    public ArrayList<OperationLog> selectOperationLogList() throws SQLException {
    	
    	ArrayList<OperationLog> operationLogList = new ArrayList<OperationLog>();
    	
        String dbname = "C:\\work\\商品検索まとめサービス\\DB\\ProductSearch.db"; // SQLite3のファイルパス
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);

            stmt = conn.createStatement();

            // OPERATION_LOGテーブルからデータを取得
            ResultSet rs = stmt.executeQuery("SELECT * FROM OPERATION_LOG");
            while (rs.next()) {
            	OperationLog ol = new OperationLog();
            	ol.setFunction(rs.getString("FUNCTION"));
            	ol.setOperation(rs.getString("OPERATION"));
            	ol.setOperationDate(rs.getString("OPERATIONDATE"));
            	ol.setNote(rs.getString("NOTE"));
            	operationLogList.add(ol);
            	
//            	String function       = rs.getString("FUNCTION");
//                String operation      = rs.getString("OPERATION");
//                String operationDate = rs.getString("OPERATIONDATE");
//                String note            = rs.getString("NOTE"); 
//                System.out.println(function + "," + operation + "," + operationDate + "," + note);
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		return operationLogList;
    }
}