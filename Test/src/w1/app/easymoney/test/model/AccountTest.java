package w1.app.easymoney.test.model;

import junit.framework.TestCase;
import w1.app.easymoney.data.DatabaseOperator;
import w1.app.easymoney.model.Account;
import w1.app.easymoney.model.Node;

/**
 * Created by el17 on 6/3/2014.
 */
public class AccountTest extends TestCase {
    private Node root;
    private Node root_C;
    private Node root_D;

    protected void setUp() throws Exception {
        super.setUp();

        DatabaseOperator database = DatabaseOperator.getOperator();
        database.getWritableDatabase().execSQL("delete from " + Node.TABLE_NAME);

        root = new Node();
        root.setName("Account");
        root.setDescription("Account");
        root.setCode(Account.CODE_ACCOUNT);
        root.save();

        if (root.getID() < 1){
            throw new Exception("xxx");
        }

        root_C = new Node();
        root_C.setName("Credit");
        root_C.setDescription("Credit");
        root_C.setCode(Account.CODE_ACCOUNT_CREDIT);
        root_C.setParentID(root.getID());

        root_D = new Node();
        root_D.setDescription("Debit");
        root_D.setName("Debit");
        root_D.setCode(Account.CODE_ACCOUNT_DEBIT);
        root_D.setParentID(root.getID());

        root_C.save();
        root_D.save();
    }

    public void test() throws Exception {
        this.doTestIssue();
    }


    private void doTestIssue() throws Exception {
        Account a = new Account(Account.TYPE_DEBIT);
        a.setName("BOA");
        a.setDescription("Bank of America");
        a.save();

        if (a.getID() < 1){
            throw new Exception("fail.");
        }

        assertEquals(root_D.getID(), a.getParentID());
    }
}
