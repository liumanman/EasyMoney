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
        int id = this.doTestIssue();
        this.doTestModify(id);
        this.doTestDelete(id);
    }


    private int doTestIssue() throws Exception {
        Account a = new Account(Account.TYPE_DEBIT);
        a.setName("BOA");
        a.setDescription("Bank of America");
        a.save();

        if (a.getID() < 1){
            throw new Exception("fail.");
        }

        assertEquals(root_D.getID(), a.getParentID());

        Account a2 = Account.get(a.getID());
        assertNotNull(a2);
        assertEquals("BOA", a2.getName());
        assertEquals("Bank of America", a2.getDescription());
        assertEquals(root_D.getID(), a2.getParentID());

        return a.getID();
    }

    private void doTestModify(int id) throws Exception {
        Account a = Account.get(id);
        a.setName("BOA2");
        a.setDescription("Bank of America2");
        a.save();

        Account a2 = Account.get(id);
        assertEquals("BOA2", a2.getName());
        assertEquals("Bank of America2", a2.getDescription());
        assertEquals(root_D.getID(), a2.getParentID());
    }

    private void doTestDelete(int id) throws Exception {
        Account a = Account.get(id);
        a.delete();

        Account a2 = Account.get(id);
        assertNull(a2);
    }
}
