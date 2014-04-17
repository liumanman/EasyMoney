package w1.app.easymoney.entity;

import java.util.Date;

public class Transaction {
	public static String COLUMN_ID = "ID";
	public static String COLUMN_AMOUNT = "Amount";
	public static String COLUMN_COMMENT = "Comment";
	public static String COLUMN_TRANDATE = "TranDate";
	public static String COLUMN_INDATE = "InDate";
	public static String COLUMN_INUSERID = "InUserID";
	public static String COLUMN_EDITDATE = "EditDate";
	public static String COLUMN_EDITUSERID = "EditUserID";
	
	public static String TABLE_NAME = "[Transaction]";
	
	private int mID;
	private float mAmount;
	private String mComment;
	private Date mTranDate;
	private Date mInDate;
	private String mInUserID;
	private Date mEditDate;
	private String mEditUserID;
	
	public int getID() {
		return mID;
	}
	public void setID(int id) {
		this.mID = id;
	}
	public float getAmount() {
		return mAmount;
	}
	public void setAmount(float amount) {
		this.mAmount = amount;
	}
	public String getComment() {
		return mComment;
	}
	public void setComment(String comment) {
		this.mComment = comment;
	}
	public Date getTranDate() {
		return mTranDate;
	}
	public void setTranDate(Date tranDate) {
		this.mTranDate = tranDate;
	}
	public Date getInDate() {
		return mInDate;
	}
	public void setInDate(Date inDate) {
		this.mInDate = inDate;
	}
	public String getInUserID() {
		return mInUserID;
	}
	public void setInUserID(String inUserID) {
		this.mInUserID = inUserID;
	}
	public Date getEditDate() {
		return mEditDate;
	}
	public void setEditDate(Date editDate) {
		this.mEditDate = editDate;
	}
	public String getEditUserID() {
		return mEditUserID;
	}
	public void setEditUserID(String editUserID) {
		this.mEditUserID = editUserID;
	}
	
}
