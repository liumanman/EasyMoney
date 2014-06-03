package w1.app.easymoney.model;


import w1.app.easymoney.data.DatabaseOperator;
import w1.app.easymoney.data.NodeDBH;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class Node {
    //Constant
//    public static final String CODE_INCOMING_CATEGORY = "IC";


    //public static final String CODE_VIRTUAL_ACCOUNTING = "VA";

//    public static final String CODE_TRANSFER = "TRAN";

    public static final String COLUMN_CODE = "Code";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_DESCRIPTION = "Description";
    public static final String COLUMN_PARENTID = "ParentID";
    public static final String COLUMN_LEVEL = "Level";
    public static final String COLUMN_INDATE = "InDate";
    public static final String COLUMN_INUSERID = "InUserID";
    public static final String COLUMN_EDITDATE = "EditDate";
    public static final String COLUMN_EDITUSERID = "EditUserID";

    public static final String TABLE_NAME = "Node";

    public static final int ROOT_NODE_ID = -1;
//
//    public static Node create(String name, String description, int parentID, String inUserID) throws SQLException, ParseException {
//        DatabaseOperator.getOperator().beginTransaction();
//        try {
//            Node node = new Node();
//
//            node.setName(name);
//            node.setDescription(description);
//            node.setParentID(parentID);
//            node.setInUserID(inUserID);
//            node.setInDate(new Date());
//            node.setLevel("");
//
//            int id = NodeDBH.insert(node);
//
//            if (parentID == Node.ROOT_NODE_ID) {
//                node.setLevel(String.valueOf(id));
//            } else {
//                Node parent = NodeDBH.get(parentID);
//                node.setLevel(parent.getLevel() + "-" + String.valueOf(id));
//            }
//
//            node.setID(id);
//
//            NodeDBH.update(node);
//            DatabaseOperator.getOperator().setTransactionSuccessful();
//
//            return node;
//        } finally {
//            DatabaseOperator.getOperator().endTransaction();
//        }
//    }

    public static void create(Node node) throws SQLException, ParseException {
        node.mInDate = new Date();
        node.mLevel = "";

        DatabaseOperator.getOperator().beginTransaction();
        try {
            int id = NodeDBH.insert(node);
            node.mID = id;

            if (node.mParentID > 0) {
                node.mLevel = Node.get(node.mParentID).getLevel();
            }
            node.mLevel = node.mLevel + String.valueOf(id) + "-";

            NodeDBH.update(node);
            DatabaseOperator.getOperator().setTransactionSuccessful();
        }finally{
            DatabaseOperator.getOperator().endTransaction();
        }
    }

//    public static Node modify(int id, String name, String description, String userID) throws SQLException, ParseException {
//        Node node = NodeDBH.get(id);
//
//        node.setDescription(description);
//        node.setName(name);
//        node.setEditDate(new Date());
//        node.setEditUserID(userID);
//
//        NodeDBH.update(node);
//
//        return node;
//
//    }

    public static void modify(Node node) throws ParseException, SQLException {
        Node node2 = NodeDBH.get(node.mID);
        node2.mDescription = node.mDescription;
        node2.mName = node.mName;
        node2.mEditDate = node.mEditDate = new Date();
        node2.mEditUserID = node.mEditUserID;

        NodeDBH.update(node2);
    }

    public static void delete(int id) throws Exception {
        List<Node> childNodes = Node.getChildNodes(id);
        if (childNodes != null && childNodes.size() > 0) {
            throw new Exception("Can't delete a node which has child nodes.");
        }

        List<Transaction> TransactionList = Transaction.getByNodeID(id);
        if (TransactionList != null && TransactionList.size() > 0) {
            throw new Exception("Can't delete a node which has transaction.");
        }

        NodeDBH.delete(id);
    }

    public static List<Node> getChildNodes(int id) throws ParseException {
        List<Node> nodes = NodeDBH.getByParentID(id);

        return nodes;
    }

    public static Node getByCode(String code) throws ParseException {
        List<Node> nodes = NodeDBH.getByCode(code);
        if (nodes == null || nodes.size() < 1) {
            return null;
        } else {
            return nodes.get(0);
        }
    }

    public static Node get(int id) throws ParseException {
        return NodeDBH.get(id);
    }

    public static Node buildByCode(String code) throws ParseException {
        Node node = getByCode(code);
        buildTree(node);
        return node;
    }

    public static Node buildByID(int id) throws ParseException {
        Node node = get(id);
        buildTree(node);
        return node;
    }

    public static boolean isChildOf(){
        return false;
    }

    private static void buildTree(Node node) throws ParseException {
        List<Node> list = NodeDBH.getByParentID(node.getID());
        if (list != null && list.size() > 0) {
            node.setChildren(list);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setParent(node);
                buildTree(list.get(i));
            }
        }
    }

    //getter and setter
    protected int mID;
    protected String mName;
    protected String mDescription;
    protected int mParentID;
    protected String mLevel;
    protected Date mInDate;
    protected String mInUserID;
    protected Date mEditDate;
    protected String mEditUserID;
    protected String mCode;
    protected List<Node> mChildren;
    protected Node mParent;

    public int getID() {
        return mID;
    }

    public void setID(int mID) {
        this.mID = mID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getParentID() {
        return mParentID;
    }

    public void setParentID(int mParentID) {
        this.mParentID = mParentID;
    }

    public String getLevel() {
        return mLevel;
    }

    public void setLevel(String mLevel) {
        this.mLevel = mLevel;
    }

    public Date getInDate() {
        return mInDate;
    }

    public void setInDate(Date mInDate) {
        this.mInDate = mInDate;
    }

    public String getInUserID() {
        return mInUserID;
    }

    public void setInUserID(String mInUserID) {
        this.mInUserID = mInUserID;
    }

    public Date getEditDate() {
        return mEditDate;
    }

    public void setEditDate(Date mEditDate) {
        this.mEditDate = mEditDate;
    }

    public String getEditUserID() {
        return mEditUserID;
    }

    public void setEditUserID(String mEditUserID) {
        this.mEditUserID = mEditUserID;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        this.mCode = code;
    }

    public List<Node> getChildren() {
        return this.mChildren;
    }

    public void setChildren(List<Node> children) {
        this.mChildren = children;
    }


    public Node getParent() throws ParseException {
        if (mParent == null && mParentID != Node.ROOT_NODE_ID){
           mParent = Node.get(mParentID) ;
        }

        return mParent;
    }

    protected void setParent(Node parent) {
        this.mParent = parent;
    }


    public boolean isChildOf(int id) throws ParseException {
        Node n = this;
        do {
           if(n.mID == id) {
               return true;
           }


        }while ((n = n.getParent()) != null);

        return false;
    }

    public boolean isChildOf(String code) throws ParseException {
        Node node = Node.getByCode(code);

        return isChildOf(node.getID());
    }

    public void save() throws Exception {
        if (mID > 0){
            Node.modify(this);
        }else{
            Node.create(this);
        }


    }

    public void delete() throws Exception {
        Node.delete(mID);
    }
}