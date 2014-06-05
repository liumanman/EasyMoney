package w1.app.easymoney.test.model;

import junit.framework.TestCase;
import w1.app.easymoney.data.DatabaseOperator;
import w1.app.easymoney.model.Merchant;
import w1.app.easymoney.model.Node;


/**
 * Created by el17 on 6/4/2014.
 */
public class MerchantTest extends TestCase {
    private Node root;
    protected void setUp() throws Exception {
        super.setUp();

        DatabaseOperator database = DatabaseOperator.getOperator();
        database.getWritableDatabase().execSQL("delete from " + Node.TABLE_NAME);

        root = new Node();
        root.setName("Merchant");
        root.setDescription("Root of Merchant");
        root.setCode(Merchant.CODE_MERCHANT);
        root.save();

        if (root.getID() < 1){
            throw new Exception("Fail to create root Merchant.");
        }
    }

    public void test() throws Exception {
        int id = this.doTestIssue();
        this.doTestModify(id);
        this.doTestDelete(id);

    }

    private int doTestIssue() throws Exception {
        Merchant m = new Merchant();
        m.setName("Costco");
        m.setDescription("WholeSale");
        m.save();

        if (m.getID() < 1){
            fail();
        }

        Merchant m2 = Merchant.get(m.getID());
        assertNotNull(m2);
        assertEquals(m.getID(), m2.getID());
        assertEquals(root.getID(), m2.getParentID());
        assertEquals("Costco", m2.getName());
        assertEquals("WholeSale", m2.getDescription());

        return m.getID();
    }

    private void doTestModify(int id) throws Exception {
        Merchant m = Merchant.get(id);
        m.setName("Walmart");
        m.setDescription("NO.1");
        m.save();

        Merchant m2 = Merchant.get(id);
        assertNotNull(m2);
        assertEquals(m2.getID(), m.getID());
        assertEquals(root.getID(), m2.getParentID());
        assertEquals("Walmart", m2.getName());
        assertEquals("NO.1", m2.getDescription());

    }

    private void doTestDelete(int id) throws Exception {
        Merchant m = Merchant.get(id);
        m.delete();

        Merchant m2 = Merchant.get(id);
        assertNull(m2);
    }
}
