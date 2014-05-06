package w1.app.easymoney.model;

import w1.app.easymoney.common.Utility;
import w1.app.easymoney.data.DatabaseOperator;
import w1.app.easymoney.data.TN_RelationDBH;
import w1.app.easymoney.data.TransactionDBH;
import w1.app.easymoney.entity.TN_Relation;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by el17 on 5/1/2014.
 */
public class Transaction {

    //Constants
    public static String COLUMN_ID = "ID";
    public static String COLUMN_AMOUNT = "Amount";
    public static String COLUMN_COMMENT = "Comment";
    public static String COLUMN_TRANDATE = "TranDate";
    public static String COLUMN_INDATE = "InDate";
    public static String COLUMN_INUSERID = "InUserID";
    public static String COLUMN_EDITDATE = "EditDate";
    public static String COLUMN_EDITUSERID = "EditUserID";

    public static String TABLE_NAME = "[Transaction]";

    public static Transaction create(Transaction tran, int[] nodeIDs, int[] negativeNodeIDs) throws Exception {
        if ((nodeIDs == null || nodeIDs.length < 1) && (negativeNodeIDs == null || negativeNodeIDs.length < 1)) {
            throw new Exception("Can't create a transaction without any node.");
        }

        for (int i = 0; nodeIDs != null && i < nodeIDs.length; i++) {
            List<Node> childNodes = Node.getChildNodes(nodeIDs[i]);
            if (childNodes != null && childNodes.size() > 0) {
                throw new Exception("Can't put a transction into a node which has child nodes.");
            }
        }

        for (int i = 0; negativeNodeIDs != null && i < negativeNodeIDs.length; i++) {
            List<Node> childNodes = Node.getChildNodes(negativeNodeIDs[i]);
            if (childNodes != null && childNodes.size() > 0) {
                throw new Exception("Can't put a transction into a node which has child nodes.");
            }
        }

        DatabaseOperator.getOperator().beginTransaction();

        try {
            tran.setInDate(new Date());
            tran.setID(TransactionDBH.insert(tran));

            for (int i = 0; nodeIDs != null && i < nodeIDs.length; i++) {
                TN_Relation r = new TN_Relation();
                r.setInDate(new Date());
                r.setNodeID(nodeIDs[i]);
                r.setTranID(tran.getID());
                r.setFlag(1);

                TN_RelationDBH.insert(r);
            }

            for (int i = 0; negativeNodeIDs != null && i < negativeNodeIDs.length; i++) {
                TN_Relation r = new TN_Relation();
                r.setInDate(new Date());
                r.setNodeID(negativeNodeIDs[i]);
                r.setTranID(tran.getID());
                r.setFlag(-1);

                TN_RelationDBH.insert(r);
            }

            //throw new Exception();

            DatabaseOperator.getOperator().setTransactionSuccessful();
        } finally {
            DatabaseOperator.getOperator().endTransaction();
        }

        return tran;
    }

    public static void create(Transaction tran) throws Exception {
        tran.setInDate(new Date());

        if (tran.getTN_Relation() == null || tran.getTN_Relation().size() < 1) {
            throw new Exception("Can't create a transaction without any node.");
        }


        int c = 0;
        for (int i = 0; i < tran.getTN_Relation().size(); i++) {
            Node node = Node.get(tran.getTN_Relation().get(i).getNodeID());
            if (node == null) {
                throw new Exception("Can't find node " + tran.getTN_Relation().get(i).getNodeID());
            }

            List<Node> list = Node.getChildNodes(tran.getTN_Relation().get(i).getNodeID());
            if (list != null && list.size() > 0) {
                throw new Exception("Can't put a transaction into a node which has child nodes.");
            }

            c = c + tran.getTN_Relation().get(i).getAmount();
        }

        if (c % tran.getAmount() != 0) {
            throw new Exception("TN amount is not correct.");
        }

        DatabaseOperator.getOperator().beginTransaction();
        try {
            tran.setID(TransactionDBH.insert(tran));
            for (int i = 0; i < tran.getTN_Relation().size(); i++) {
                if (tran.getTN_Relation().get(i).getAmount() <= 0){
                    throw new Exception("Relation amount can't be empty.");
                }
                tran.getTN_Relation().get(i).setTranID(tran.getID());
                tran.getTN_Relation().get(i).setInDate(new Date());
                //tran.getTN_Relation().get(i).setAmount(tran.getAmount());
                tran.getTN_Relation().get(i).setID(TN_RelationDBH.insert(tran.getTN_Relation().get(i)));

            }

            DatabaseOperator.getOperator().setTransactionSuccessful();
        }
        finally {
            DatabaseOperator.getOperator().endTransaction();
        }
    }

    public static void modify(Transaction tran, int[] nodeIDs, int[] negativeNodeIDs) throws Exception {

        if ((nodeIDs == null || nodeIDs.length < 1) && (negativeNodeIDs == null || negativeNodeIDs.length < 1)) {
            throw new Exception("Can't create a transaction without any node.");
        }

        for (int i = 0; nodeIDs != null && i < nodeIDs.length; i++) {
            List<Node> childNodes = Node.getChildNodes(nodeIDs[i]);
            if (childNodes != null && childNodes.size() > 0) {
                throw new Exception("Can't put a transaction into a node which has child nodes.");
            }
        }

        for (int i = 0; negativeNodeIDs != null && i < negativeNodeIDs.length; i++) {
            List<Node> childNodes = Node.getChildNodes(negativeNodeIDs[i]);
            if (childNodes != null && childNodes.size() > 0) {
                throw new Exception("Can't put a transaction into a node which has child nodes.");
            }
        }

        //don't allow to modify indate and inuser
        Transaction oldTran = TransactionDBH.get(tran.getID());
        tran.setInDate(oldTran.getInDate());
        tran.setInUserID(oldTran.getInUserID());

        tran.setEditDate(new Date());

        DatabaseOperator.getOperator().beginTransaction();

        try {
            TN_RelationDBH.deleteBYTranID(tran.getID());

//            for (int i = 0; nodeIDs != null && i < nodeIDs.length; i++) {
//                TN_Relation r = new TN_Relation();
//                r.setInDate(new Date());
//                r.setNodeID(nodeIDs[i]);
//                r.setTranID(tran.getID());
//                r.setFlag(1);
//
//                TN_RelationDBH.insert(r);
//            }
//
//            for (int i = 0; negativeNodeIDs != null && i < negativeNodeIDs.length; i++) {
//                TN_Relation r = new TN_Relation();
//                r.setInDate(new Date());
//                r.setNodeID(negativeNodeIDs[i]);
//                r.setTranID(tran.getID());
//                r.setFlag(-1);
//
//                TN_RelationDBH.insert(r);
//            }

            for (int i = 0; i < tran.getTN_Relation().size(); i++) {
                if (tran.getTN_Relation().get(i).getAmount() <= 0){
                    throw new Exception("Relation amount can't be empty.");
                }
                tran.getTN_Relation().get(i).setTranID(tran.getID());
                tran.getTN_Relation().get(i).setInDate(new Date());
                //tran.getTN_Relation().get(i).setAmount(tran.getAmount());
                tran.getTN_Relation().get(i).setID(TN_RelationDBH.insert(tran.getTN_Relation().get(i)));

            }

            TransactionDBH.update(tran);

            DatabaseOperator.getOperator().setTransactionSuccessful();
        } finally {
            DatabaseOperator.getOperator().endTransaction();
        }

    }

    public static List<Transaction> getByNodeID(int nodeID) throws Exception {
        return TransactionDBH.getByNodeID(nodeID);
    }

    public static void delete(int id) throws SQLException {
        DatabaseOperator.getOperator().beginTransaction();
        try {
            TransactionDBH.delete(id);
            TN_RelationDBH.deleteBYTranID(id);

            DatabaseOperator.getOperator().setTransactionSuccessful();
        } finally {
            DatabaseOperator.getOperator().endTransaction();
        }
    }

    public static Transaction get(int id) throws ParseException, Exception {
        return TransactionDBH.get(id);
    }

    public static List<Transaction> query(Date start, Date end, int[] nodeIDs) throws Exception {
        if (start != null) {
            start = Utility.removeTime(start);
        }
        if (end != null) {
            end = Utility.addDate(end, 1);
            end = Utility.removeTime(end);
        }

        return TransactionDBH.query(start, end, nodeIDs);
    }

    public static int getSummary(Date start, Date end, int[] nodeIDs) throws Exception {
        List<Transaction> list = query(start, end, nodeIDs);

        int s = 0;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                s = s + list.get(i).getAmount();
            }
        }

        return s;
    }



    //Getter and Setter
    protected int mID;
    protected int mAmount;
    protected String mComment;
    protected Date mTranDate;
    protected Date mInDate;
    protected String mInUserID;
    protected Date mEditDate;
    protected String mEditUserID;
    protected List<TN_Relation> mTN_Relation;

    public List<TN_Relation> getTN_Relation() {
        return mTN_Relation;
    }

    public void setTN_Relation(List<TN_Relation> tn_Relation) {
        this.mTN_Relation = tn_Relation;
    }

    public int getID() {
        return mID;
    }
    public void setID(int id) {
        this.mID = id;
    }
    public int getAmount() {
        return mAmount;
    }
    public void setAmount(int amount) {
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
