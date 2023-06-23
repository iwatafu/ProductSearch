package service.beans;

public class OperationLog {
	
	private String function;	          // 機能
	private String operation;            // 操作
	private String operationDate;        // 日時
	private String note;                 // 特記
	
	public OperationLog() {} //デフォルトコンストラクタ

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	

}
