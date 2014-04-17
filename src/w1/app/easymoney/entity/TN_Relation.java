package w1.app.easymoney.entity;

import java.util.Date;

public class TN_Relation {
	public static String COLUMN_ID = "ID";
	public static String COLUMN_TRANID = "TranID";
	public static String COLUMN_NODEID = "NodeID";
	public static String COLUMN_INDATE = "InDate";
	public static String COLUMN_INUSERID = "InUserID";
	public static String COLUMN_FLAG = "Flag";
	
	public static String TABLE_NAME = "TN_Relation";
	
	private int mID;
	private int mTranID;
	private int mNodeID;
	private Date mInDate;
	private String mInUserID;
	private int mFlag;
	
	public int getID() {
		return mID;
	}
	public void setID(int id) {
		this.mID = id;
	}
	public int getTranID() {
		return mTranID;
	}
	public void setTranID(int tranID) {
		this.mTranID = tranID;
	}
	public int getNodeID() {
		return mNodeID;
	}
	public void setNodeID(int nodeID) {
		this.mNodeID = nodeID;
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
	public int getFlag() {
		return mFlag;
	}
	public void setFlag(int flag) {
		this.mFlag = flag;
	}
	
}
