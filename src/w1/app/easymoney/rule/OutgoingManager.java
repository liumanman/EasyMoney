//package w1.app.easymoney.rule;
//
//import w1.app.easymoney.common.Utility;
//import w1.app.easymoney.entity.Node;
//import w1.app.easymoney.entity.TN_Relation;
//import w1.app.easymoney.entity.Transaction;
//
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by el17 on 4/24/2014.
// */
//public class OutgoingManager {
//    public static int getOutgoingSummaryByCurrentDate() throws Exception {
//        Node ocRoot = NodeManager.getByCode(Node.CODE_OUTGOING_CATEGORY);
//
//        Date currentDay = new Date();
//
//        return TransactionManager.getSummary(currentDay, currentDay, new int[]{ocRoot.getID()});
//    }
//
//    public static int getOutgoingSummaryByCurrentWeek() throws Exception {
//
//        Node ocRoot = NodeManager.getByCode(Node.CODE_OUTGOING_CATEGORY);
//
//        Date currentDay = new Date();
//        Date sun = Utility.getTheSunday(currentDay);
//        Date sat = Utility.getTheSaturday(currentDay);
//
//        return TransactionManager.getSummary(sun, sat, new int[]{ocRoot.getID()});
//    }
//
//    public static int getOutgoingSumaryByCurrentMonth() throws Exception {
//
//        Node ocRoot = NodeManager.getByCode(Node.CODE_OUTGOING_CATEGORY);
//
//        Date date = new Date();
//        Date start = Utility.getFirstDayInMonth(date);
//        Date end = Utility.getLastDayInMonth(date);
//
//        return TransactionManager.getSummary(start, end, new int[]{ocRoot.getID()});
//    }
//
//    public static List<Node> getCategory() throws ParseException {
//        return NodeManager.getAllByCode(Node.CODE_OUTGOING_CATEGORY);
//    }
//
//    public static Transaction addOutgoingTransaction(int amount, Date date, int account, int oc, int member
//            , int merchant, int project, String memo) throws Exception {
//        if (amount <= 0){ throw new Exception("Amount is not valid.");}
//
//        if (date == null){ throw new Exception("Date is not valid.");}
//
//        if (oc <= 0){ throw new Exception("OC is not valid.");}
//        Node nodeOC = NodeManager.get(oc);
//        if (nodeOC == null){ throw new Exception("Can't locate OC.");}
//        if (!nodeOC.getCode().equals(Node.CODE_OUTGOING_CATEGORY)){
//            throw new Exception("It's not OC node.");
//        }
//
//        if (member <= 0){ throw new Exception("Member is not valid.");}
//        Node nodeMember = NodeManager.get(member);
//        if (nodeMember == null){ throw new Exception("Can;t locate Member.");}
//        if (!nodeMember.getCode().equals(Node.CODE_MEMBER)){
//            throw new Exception("It's not Member node.");
//        }
//
//        if (merchant > 0){
//            Node nodeMerchant = NodeManager.get(merchant);
//            if (nodeMerchant == null){ throw new Exception("Can't locate Merchant.");}
//            if (!nodeMerchant.getCode().equals(Node.CODE_MERCHANT)){
//                throw new Exception("It's not Merchant node.");
//            }
//        }
//
//        if (project > 0){
//            Node nodeProject = NodeManager.get(project);
//            if (nodeProject == null){ throw new Exception("Can't locate Project");}
//            if (!nodeProject.getCode().equals(Node.CODE_PROJECT)){
//                throw new Exception("It's not Project node.");
//            }
//        }
//
//        Transaction tran = new Transaction();
//        tran.setAmount(amount);
//        tran.setTranDate(date);
//        tran.setComment(memo);
//        tran.setTN_Relation(new ArrayList<TN_Relation>(4));
//        TN_Relation tn = new TN_Relation();
//        tn.setNodeID(oc);
//        tn.setAmount(amount);
//
//        throw new Exception("not finished yet.");
//    }
//}
