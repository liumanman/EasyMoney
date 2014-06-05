package w1.app.easymoney.test.model;

import junit.framework.TestCase;
import w1.app.easymoney.data.DatabaseOperator;
import w1.app.easymoney.model.Member;
import w1.app.easymoney.model.Node;

import java.sql.SQLException;

/**
 * Created by el17 on 6/4/2014.
 */
public class MemberTest extends TestCase {
    private Node root;
    protected  void setUp() throws Exception {
        super.setUp();

        DatabaseOperator database = DatabaseOperator.getOperator();
        database.getWritableDatabase().execSQL("delete from " + Node.TABLE_NAME);

        root = new Node();
        root.setName("Member");
        root.setDescription("Member");
        root.setCode(Member.CODE_MEMBER);

        root.save();
        if (root.getID() < 1){
            throw new Exception("fail to create member root.");
        }

    }

    public void test() throws Exception {
        int id = doTestIssue();
        this.doTestModify(id);
        this.doTestDelete(id);
    }

    private int doTestIssue() throws Exception {
        Member m = new Member();

        m.setName("Kiko");
        m.setDescription("Yuan Sun");
        m.save();

        Member m2 = Member.get(m.getID());
        assertNotNull(m2);
        assertEquals(m.getID(), m2.getID());
        assertEquals("Kiko", m2.getName());
        assertEquals("Yuan Sun", m2.getDescription());
        assertEquals(root.getID(), m2.getParentID());

        return m.getID();
    }

    private void doTestModify(int id) throws Exception {
        Member m = Member.get(id);
        m.setName("Evan");
        m.setDescription("Manman Liu");

        m.save();

        Member m2 = Member.get(m.getID());
        assertNotNull(m2);
        assertEquals("Evan", m2.getName());
        assertEquals("Manman Liu", m2.getDescription());
        assertEquals(root.getID(), m2.getParentID());
    }

    private void doTestDelete(int id) throws Exception {
        Member m = Member.get(id);
        m.delete();

        Member m2 = Member.get(id);
        assertNull(m2);
    }
}
