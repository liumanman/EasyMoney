package w1.app.easymoney.test.model;

import junit.framework.TestCase;
import w1.app.easymoney.data.DatabaseOperator;
import w1.app.easymoney.model.Node;
import w1.app.easymoney.model.Project;


/**
 * Created by el17 on 6/4/2014.
 */
public class ProjectTest extends TestCase {
    private Node root;
    protected void setUp() throws Exception {
        super.setUp();

        DatabaseOperator database = DatabaseOperator.getOperator();
        database.getWritableDatabase().execSQL("delete from " + Node.TABLE_NAME);

        root = new Node();
        root.setName("Project");
        root.setDescription("Root of projects");
        root.setCode(Project.CODE_PROJECT);
        root.save();

        if (root.getID() < 1){
            throw new Exception("Fail to create root of projects.");
        }
    }

    public void test() throws Exception {
        int id = this.doTestIssue();
        this.doTestModify(id);
        this.doTestDelete(id);
    }
    private int doTestIssue() throws Exception {
        Project p = new Project();
        p.setName("Project1");
        p.setDescription("Project description1");
        p.save();

        if (p.getID() < 1){
            fail();
        }

        Project p2 = Project.get(p.getID());
        assertNotNull(p2);
        assertEquals(p.getID(), p2.getID());
        assertEquals(root.getID(), p2.getParentID());
        assertEquals("Project1", p2.getName());
        assertEquals("Project description1", p2.getDescription());

        return p.getID();
    }

    private void doTestModify(int id) throws Exception {
        Project p = Project.get(id);
        p.setName("Project2");
        p.setDescription("Project description2");
        p.save();

        Project p2 = Project.get(id);
        assertNotNull(p2);
        assertEquals(p.getID(), p2.getID());
        assertEquals(root.getID(), p.getParentID());
        assertEquals("Project2", p2.getName());
        assertEquals("Project description2", p2.getDescription());
    }

    private void doTestDelete(int id) throws Exception {
        Project p = Project.get(id);
        p.delete();

        Project p2 = Project.get(id);
        assertNull(p2);
    }
}
