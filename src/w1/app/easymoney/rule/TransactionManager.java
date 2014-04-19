package w1.app.easymoney.rule;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import w1.app.easymoney.data.DatabaseOperator;
import w1.app.easymoney.data.TN_RelationDBH;
import w1.app.easymoney.data.TransactionDBH;
import w1.app.easymoney.entity.Node;
import w1.app.easymoney.entity.TN_Relation;
import w1.app.easymoney.entity.Transaction;

public class TransactionManager {
    public static Transaction create(Transaction tran, int[] nodeIDs, int[] negativeNodeIDs) throws Exception {
        if ((nodeIDs == null || nodeIDs.length < 1) && (negativeNodeIDs == null || negativeNodeIDs.length < 1)) {
            throw new Exception("Can't create a transaction without any node.");
        }

        for (int i = 0; nodeIDs != null && i < nodeIDs.length; i++) {
            List<Node> childNodes = NodeManager.getChildNodes(nodeIDs[i]);
            if (childNodes != null && childNodes.size() > 0) {
                throw new Exception("Can't put a transction into a node which has child nodes.");
            }
        }

        for (int i = 0; negativeNodeIDs != null && i < negativeNodeIDs.length; i++) {
            List<Node> childNodes = NodeManager.getChildNodes(negativeNodeIDs[i]);
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

    public static Transaction create(Transaction tran) throws Exception {
      tran.setInDate(new Date());

        if (tran.getTN_Relation() == null || tran.getTN_Relation().size() < 1){
            throw new Exception("Can't create a transaction without any node.");
        }


        int c = 0;
        for(int i = 0; i < tran.getTN_Relation().size(); i ++){
            Node node = NodeManager.get(tran.getTN_Relation().get(i).getNodeID());
            if(node == null){
                throw new Exception("Can't find node " + tran.getTN_Relation().get(i).getNodeID());
            }

            List<Node> list = NodeManager.getChildNodes(tran.getTN_Relation().get(i).getNodeID());
            if(list != null && list.size() > 0){
                throw new Exception("Can't put a transaction into a node which has child nodes.");
            }

            c = c + tran.getTN_Relation().get(i).getAmount();
        }

        if(c % tran.getAmount() != 0){
            throw new Exception("TN amount is not correct.");
        }

        tran.setID(TransactionDBH.insert(tran));
        for(int i = 0; i < tran.getTN_Relation().size(); i ++){
            tran.getTN_Relation().get(i).setTranID(tran.getID());
            tran.getTN_Relation().get(i).setInDate(new Date());
            tran.getTN_Relation().get(i).setID(TN_RelationDBH.insert(tran.getTN_Relation().get(i)));

        }

        return tran;
    }

    public static Transaction modify(Transaction tran, int[] nodeIDs, int[] negativeNodeIDs) throws Exception {

        if ((nodeIDs == null || nodeIDs.length < 1) && (negativeNodeIDs == null || negativeNodeIDs.length < 1)) {
            throw new Exception("Can't create a transaction without any node.");
        }

        for (int i = 0; nodeIDs != null && i < nodeIDs.length; i++) {
            List<Node> childNodes = NodeManager.getChildNodes(nodeIDs[i]);
            if (childNodes != null && childNodes.size() > 0) {
                throw new Exception("Can't put a transaction into a node which has child nodes.");
            }
        }

        for (int i = 0; negativeNodeIDs != null && i < negativeNodeIDs.length; i++) {
            List<Node> childNodes = NodeManager.getChildNodes(negativeNodeIDs[i]);
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

            TransactionDBH.update(tran);

            DatabaseOperator.getOperator().setTransactionSuccessful();
        } finally {
            DatabaseOperator.getOperator().endTransaction();
        }


        return tran;

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
        }
        finally{
            DatabaseOperator.getOperator().endTransaction();
        }
    }

    public static Transaction get(int id) throws  ParseException {
        return TransactionDBH.get(id);
    }

    public static List<Transaction> query(Date start, Date end, int[] nodeIDs) throws Exception {
        Date e = null;
        if (end != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(end);
            e = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).getGregorianChange();
        }
        return TransactionDBH.query(start, e, nodeIDs);
    }

}
