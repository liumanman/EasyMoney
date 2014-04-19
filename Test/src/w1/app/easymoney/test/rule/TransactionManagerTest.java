package w1.app.easymoney.test.rule;

import junit.framework.TestCase;

import java.security.PrivateKey;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import w1.app.easymoney.common.Utility;
import w1.app.easymoney.data.DatabaseOperator;
import w1.app.easymoney.data.TN_RelationDBH;
import w1.app.easymoney.data.TransactionDBH;
import w1.app.easymoney.entity.Node;
import w1.app.easymoney.entity.TN_Relation;
import w1.app.easymoney.entity.Transaction;
import w1.app.easymoney.rule.NodeManager;
import w1.app.easymoney.rule.TransactionManager;

public class TransactionManagerTest extends TestCase {
    private Node ecRoot;
    private Node ecR_1;
    private Node ecR_1_1;
    private Node ecR_1_2;
    private Node ecR_1_3;
    private Node ecR_2;
    private Node ecR_2_1;
    private Node ecR_2_2;
    private Node ecR_3;
    private Node ecR_3_1;

    private Transaction mTran;

    protected void setUp() throws Exception {
        super.setUp();

        DatabaseOperator database = DatabaseOperator.getOperator();
        database.getWritableDatabase()
                .execSQL("delete from " + Node.TABLE_NAME);
        database.getWritableDatabase().execSQL("delete from " + Transaction.TABLE_NAME);
        database.getWritableDatabase().execSQL("delete from " + TN_Relation.TABLE_NAME);

        ecRoot = NodeManager.create("Expense Category", "Category",
                Node.ROOT_NODE_ID, "1w");

        ecR_1 = NodeManager.create("Food", "Food", ecRoot.getID(), "1w");
        ecR_1_1 = NodeManager.create("Material", "Material", ecR_1.getID(),
                "1w");
        ecR_1_2 = NodeManager.create("Drink", "Drink", ecR_1.getID(), "1w");
        ecR_1_3 = NodeManager.create("Snack", "Snack", ecR_1.getID(), "1w");

        ecR_2 = NodeManager.create("Clothes", "Clothes", ecRoot.getID(), "1w");
        ecR_2_1 = NodeManager.create("Dress", "Dress", ecR_2.getID(), "1w");
        ecR_2_2 = NodeManager.create("hat", "hat", ecR_2.getID(), "1w");

        ecR_3 = NodeManager.create("Health", "Health", ecRoot.getID(), "1w");
        ecR_3_1 = NodeManager.create("Medication", "Medication", ecR_3.getID(),
                "1w");

    }

    public void test() throws Exception {
        this.doTestCreate2();

//        this.doTestModify();
//        this.doTestQuery();
//        this.doTestDelete();
    }

    private void doTestCreate() throws Exception {
        Transaction tran = new Transaction();
        tran.setAmount(999);
        tran.setInUserID("1w");
        tran.setComment("xxxx");
        tran.setTranDate(new SimpleDateFormat("yyyy-MM-dd")
                .parse("2014-4-8"));

        Transaction newTran = TransactionManager.create(tran, new int[] {
                        ecR_1_2.getID(), ecR_2_1.getID() },
                new int[] { ecR_3_1.getID() });
        assertNotNull(newTran);
        assertNotNull(newTran.getInDate());

        mTran = TransactionDBH.get(newTran.getID());
        assertNotNull(mTran);

        //-------------------------
        Transaction tran2 = new Transaction();
        tran2.setAmount(999);
        tran2.setInUserID("1w");
        tran2.setComment("xxxx");
        tran2.setTranDate(new SimpleDateFormat("yyyy-MM-dd")
                .parse("2014-4-8"));

        tran2 = TransactionManager.create(tran2, new int[] {ecR_1_2.getID()}, null);
        tran2 = TransactionDBH.get(tran2.getID());
        assertNotNull(tran2);
        //--------------------
        Transaction tran3 = new Transaction();
        tran3.setAmount(1999);
        tran3.setInUserID("1w");
        tran3.setComment("xxxx");
        tran3.setTranDate(new SimpleDateFormat("yyyy-MM-dd")
                .parse("2014-4-8"));

        tran3 = TransactionManager.create(tran3, null, new int[] {ecR_1_2.getID()});
        tran3 = TransactionDBH.get(tran3.getID());
        assertNotNull(tran3);
    }

    private void doTestCreate2() throws Exception {
       Transaction tran = new Transaction();
        tran.setAmount(999);
        tran.setComment("comment");
        tran.setTranDate(Utility.StringToDate("2014-04-17 00:00:00"));

        List<TN_Relation> list = new ArrayList<TN_Relation>(5);
        TN_Relation tn = new TN_Relation();
        tn.setAmount(999);
        tn.setNodeID(ecR_1_3.getID());
        list.add(tn);

        tn = new TN_Relation();
        tn.setAmount(599);
        tn.setNodeID(ecR_2_1.getID());
        list.add(tn);

        tn = new TN_Relation();
        tn.setAmount(400);
        tn.setNodeID(ecR_2_2.getID());
        list.add(tn);

        tran.setTN_Relation(list);

        tran = TransactionManager.create(tran);
    }


    private void doTestModify() throws Exception {
        int amount = 1999;
        String comment = "updated comment";
        String tranDate = "2014-04-09";
        String editUserID = "2w";

        Transaction before = TransactionManager.get(mTran.getID());

        mTran.setAmount(amount);
        mTran.setComment(comment);
        mTran.setTranDate(new SimpleDateFormat("yyyy-MM-dd").parse(tranDate));
        mTran.setEditUserID(editUserID);

        Transaction tran = TransactionManager.modify(mTran, new int[]{ecR_3_1.getID()}, new int[]{ecR_1_2.getID(), ecR_2_1.getID()});
        assertEquals(amount, tran.getAmount());
        assertEquals(comment, tran.getComment());
        assertEquals(tranDate, new SimpleDateFormat("yyyy-MM-dd").format(tran.getTranDate()));
        assertEquals(editUserID, tran.getEditUserID());
        assertNotNull(tran.getEditDate());
        assertEquals(before.getInDate(), tran.getInDate());
        assertEquals(before.getInUserID(), tran.getInUserID());

        List<TN_Relation> list = TN_RelationDBH.getByTranID(tran.getID());
        for (int i = 0; i < list.size(); i++) {
            assertEquals(tran.getID(), list.get(i).getTranID());

            if (list.get(i).getNodeID() == ecR_3_1.getID()) {
                assertEquals(1, list.get(i).getFlag());

            } else {
                if (list.get(i).getNodeID() == ecR_1_2.getID()) {
                    assertEquals(-1, list.get(i).getFlag());
                } else {
                    if (list.get(i).getNodeID() == ecR_2_1.getID()) {
                        assertEquals(-1, list.get(i).getFlag());
                    } else {
                        fail("unexpected TN_Relation");
                    }
                }
            }
        }
    }

    private void doTestQuery() throws Exception {
        List<Transaction> list = TransactionManager.query(null, null, new int[] {ecR_1_2.getID()});
    }

    private void doTestDelete() throws SQLException, ParseException {
        TransactionManager.delete(mTran.getID());

        Transaction tran = TransactionDBH.get(mTran.getID());
        assertNull(tran);
        List<TN_Relation> list = TN_RelationDBH.getByTranID(mTran.getID());
        assertEquals(0, list.size());

    }
}
