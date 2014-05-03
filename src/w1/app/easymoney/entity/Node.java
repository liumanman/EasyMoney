//package w1.app.easymoney.entity;
//
//import java.util.Date;
//import java.util.List;
//
//public class Node {
//	public static String CODE_OUTGOING_CATEGORY = "OC";
//	public static String CODE_INCOMING_CATEGORY = "IC";
//	public static String CODE_MEMBER = "MEM";
//	public static String CODE_MERCHANT = "MER";
//	public static String CODE_ACCOUNT = "ACCT";
//	public static String CODE_VIRTUAL_ACCOUNTING = "VA";
//	public static String CODE_PROJECT = "PRO";
//	public static String CODE_TRANSFER = "TRAN";
//
//	public static String COLUMN_CODE = "Code";
//	public static String COLUMN_ID = "ID";
//	public static String COLUMN_NAME = "Name";
//	public static String COLUMN_DESCRIPTION = "Description";
//	public static String COLUMN_PARENTID = "ParentID";
//	public static String COLUMN_LEVEL = "Level";
//	public static String COLUMN_INDATE = "InDate";
//	public static String COLUMN_INUSERID = "InUserID";
//	public static String COLUMN_EDITDATE = "EditDate";
//	public static String COLUMN_EDITUSERID = "EditUserID";
//
//	public static String TABLE_NAME = "Node";
//
//	public static int ROOT_NODE_ID = -1;
//
//
//
//	private int mID;
//	private String mName;
//	private String mDescription;
//	private int mParentID;
//	private String mLevel;
//	private Date mInDate;
//	private String mInUserID;
//	private Date mEditDate;
//	private String mEditUserID;
//	private String mCode;
//
//	public int getID() {
//		return mID;
//	}
//	public void setID(int mID) {
//		this.mID = mID;
//	}
//	public String getName() {
//		return mName;
//	}
//	public void setName(String mName) {
//		this.mName = mName;
//	}
//	public String getDescription() {
//		return mDescription;
//	}
//	public void setDescription(String mDescription) {
//		this.mDescription = mDescription;
//	}
//	public int getParentID() {
//		return mParentID;
//	}
//	public void setParentID(int mParentID) {
//		this.mParentID = mParentID;
//	}
//	public String getLevel() {
//		return mLevel;
//	}
//	public void setLevel(String mLevel) {
//		this.mLevel = mLevel;
//	}
//	public Date getInDate() {
//		return mInDate;
//	}
//	public void setInDate(Date mInDate) {
//		this.mInDate = mInDate;
//	}
//	public String getInUserID() {
//		return mInUserID;
//	}
//	public void setInUserID(String mInUserID) {
//		this.mInUserID = mInUserID;
//	}
//	public Date getEditDate() {
//		return mEditDate;
//	}
//	public void setEditDate(Date mEditDate) {
//		this.mEditDate = mEditDate;
//	}
//	public String getEditUserID() {
//		return mEditUserID;
//	}
//	public void setEditUserID(String mEditUserID) {
//		this.mEditUserID = mEditUserID;
//	}
//
//	public String getCode(){
//		return mCode;
//	}
//
//	public void setCode(String code){
//		this.mCode = code;
//	}
//
//    private List<Node> mChildren;
//    public List<Node> getChildren(){
//        return this.mChildren;
//    }
//    public void setChildren(List<Node> children){
//        this.mChildren = children;
//    }
//
//    private Node mParent;
//
//    public Node getParent() {
//        return mParent;
//    }
//
//    public void setParent(Node parent) {
//        this.mParent = parent;
//    }
//}
