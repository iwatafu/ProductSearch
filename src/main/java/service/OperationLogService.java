package Service;

import java.sql.SQLException;
import java.util.ArrayList;

import Service.beans.OperationLog;
import Service.query.OperationLogDAO;

public class OperationLogService {
	
	public ArrayList<OperationLog> getOperationLogList(){
		
		OperationLogDAO olDAO = new  OperationLogDAO();
		ArrayList<OperationLog> operationLogList = null;
		
		try {
			operationLogList = olDAO.selectOperationLogList();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		return operationLogList;
		
	}
	

}
