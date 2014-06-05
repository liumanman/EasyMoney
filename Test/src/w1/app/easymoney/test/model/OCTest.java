package w1.app.easymoney.test.model;

import junit.framework.TestCase;
import w1.app.easymoney.data.DatabaseOperator;
import w1.app.easymoney.model.Node;
import w1.app.easymoney.model.OutgoingCategory;


/**
 * Created by el17 on 6/4/2014.
 */
public class OCTest extends TestCase {
    private Node root;
    protected void setUp() throws Exception {
        super.setUp();

        DatabaseOperator database = DatabaseOperator.getOperator();
        database.getWritableDatabase().execSQL("delete from " + Node.TABLE_NAME);

        root = new Node();
        root.setName("OC");
        root.setDescription("root of OC");
        root.setCode(OutgoingCategory.CODE_OUTGOING_CATEGORY);
        root.save();

        if (root.getID() < 1){
            throw new Exception("Fail to create root of OC");
        }
    }

    public void test() throws Exception {
        int id = this.doTestIssue();
        this.doTestModify(id);
        this.doTestDelete(id);
    }

    private int doTestIssue() throws Exception {
        OutgoingCategory oc1 = OutgoingCategory.create();
        oc1.setName("1");
        oc1.setDescription("Level1");
        oc1.save();

        if (oc1.getID() < 1){
            fail();
        }
        oc1 = OutgoingCategory.get(oc1.getID());
        assertEquals(root.getID(),oc1.getParentID());
        assertEquals("1", oc1.getName());
        assertEquals("Level1", oc1.getDescription());

        OutgoingCategory oc11 = oc1.createSub();
        oc11.setName("1-1");
        oc11.setDescription("Level 1-1");
        oc11.save();

        if (oc11.getID() < 1){
            fail();
        }
        oc11 = OutgoingCategory.get(oc11.getID());
        assertEquals(oc1.getID(), oc11.getParentID());
        assertEquals("1-1", oc11.getName());
        assertEquals("Level 1-1", oc11.getDescription());

        return oc11.getID();
    }

    private void doTestModify(int id) throws Exception {
        OutgoingCategory oc = OutgoingCategory.get(id);
        oc.setName("1-2");
        oc.setDescription("Level 1-2");
        oc.save();

        OutgoingCategory oc2 = OutgoingCategory.get(id);
        assertNotNull(oc2);
        assertEquals(oc.getParentID(), oc2.getParentID());
        assertEquals("1-2", oc2.getName());
        assertEquals("Level 1-2", oc2.getDescription());
    }

    private void doTestDelete(int id) throws Exception {
        OutgoingCategory oc = OutgoingCategory.get(id);
        oc.delete();

        oc = OutgoingCategory.get(id);
        assertNull(oc);
    }

}
