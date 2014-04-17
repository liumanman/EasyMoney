package w1.app.easymoney.test.rule;

import junit.framework.TestCase;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import w1.app.easymoney.common.Utility;
import w1.app.easymoney.data.DatabaseOperator;
import w1.app.easymoney.data.NodeDBH;
import w1.app.easymoney.entity.Node;
import w1.app.easymoney.rule.NodeManager;

public class NodeManagerTest extends TestCase {
    private Node mRoot;
    private Node mChild1;
    private Node mChild2;


    protected void setUp() throws Exception {
        super.setUp();

        DatabaseOperator database = DatabaseOperator.getOperator();
        database.getWritableDatabase().execSQL("delete from " + Node.TABLE_NAME);
    }

    public void test() throws Exception{
        this.testCreate();
        this.testModify();
        this.testGetChildNodes(3);
        this.testDelete();
        this.testGetChildNodes(2);
    }

    private void testCreate() throws SQLException, ParseException {
        //-------insert root node------
        String name = "name1";
        String description = "description1";
        int parentID = -1;
        String inUserID = "user1";

        mRoot = NodeManager.create(name, description, parentID, inUserID);
        assertNotNull(mRoot);
        assertEquals(name, mRoot.getName());
        assertEquals(description, mRoot.getDescription());
        assertEquals(parentID, mRoot.getParentID());
        assertEquals(inUserID, mRoot.getInUserID());
        assertNotNull(mRoot.getInDate());
        assertEquals(mRoot.getLevel(), String.valueOf(mRoot.getID()));
        assertNull(mRoot.getEditDate());
        assertNull(mRoot.getEditUserID());
        assertNull(mRoot.getCode());


        Node node2 = NodeDBH.get(mRoot.getID());
        assertNotNull(node2);
        assertEquals(name, node2.getName());
        assertEquals(description, node2.getDescription());
        assertEquals(parentID, node2.getParentID());
        assertEquals(inUserID, node2.getInUserID());
        assertNotNull(node2.getInDate());
        assertEquals(node2.getLevel(), String.valueOf(node2.getID()));
        assertNull(node2.getEditDate());
        assertNull(node2.getEditUserID());
        assertNull(node2.getCode());

        //--------insert level 1 child-------------
        mChild1 = NodeManager.create("child1", "desc-child1", mRoot.getID(), "user2");
        assertEquals(mChild1.getLevel(), mRoot.getLevel()+"-"+String.valueOf(mChild1.getID()));

        node2 = NodeDBH.get(mChild1.getID());
        assertNotNull(node2);
        assertEquals(node2.getLevel(), mRoot.getLevel()+"-"+String.valueOf(mChild1.getID()));


        //---------insert level 2 child--------------
        mChild2 = NodeManager.create("child2", "desc-child2", mChild1.getID(), "user3");
        assertEquals(mChild2.getLevel(), mChild1.getLevel()+"-"+String.valueOf(mChild2.getID()));

        node2 = NodeDBH.get(mChild2.getID());
        assertNotNull(node2);
        assertEquals(node2.getLevel(), mChild1.getLevel()+"-"+String.valueOf(node2.getID()));

        //-------------more------
        NodeManager.create("child3", "desc-child3", mChild1.getID(), "user4");
        NodeManager.create("child4", "desc-child4", mChild1.getID(), "user5");


    }

    private void testModify() throws SQLException, ParseException {
        String name = "updated root name";
        String description="updated root description";
        String userID="user_u";

        Node oldRoot = mRoot;
        mRoot = NodeManager.modify(mRoot.getID(), name, description, userID);
        assertNotNull(mRoot);
        assertEquals(oldRoot.getCode(), mRoot.getCode());
        assertEquals(description, mRoot.getDescription());
        assertEquals(userID, mRoot.getEditUserID());
        assertEquals(oldRoot.getID(), mRoot.getID());
        assertEquals(oldRoot.getLevel(), mRoot.getLevel());
        assertEquals(name, mRoot.getName());
        assertEquals(oldRoot.getParentID(), mRoot.getParentID());
        assertNotNull(mRoot.getEditDate());
        assertEquals(Utility.DateToString(oldRoot.getInDate()), Utility.DateToString(mRoot.getInDate()));

        Node newRoot = NodeDBH.get(mRoot.getID());
        assertNotNull(newRoot);
        assertEquals(mRoot.getCode(), newRoot.getCode());
        assertEquals(mRoot.getDescription(), newRoot.getDescription());
        assertEquals(mRoot.getEditUserID(),newRoot.getEditUserID());
        assertEquals(mRoot.getID(), newRoot.getID());
        assertEquals(mRoot.getInUserID(), newRoot.getInUserID());
        assertEquals(mRoot.getLevel(), newRoot.getLevel());
        assertEquals(mRoot.getName(), newRoot.getName());
        assertEquals(mRoot.getParentID(), newRoot.getParentID());
        assertEquals(Utility.DateToString(mRoot.getEditDate()) , Utility.DateToString(newRoot.getEditDate()));
        assertEquals(Utility.DateToString(mRoot.getInDate()),Utility.DateToString(newRoot.getInDate()));


    }

    private void testDelete() throws Exception {
        try{
            NodeManager.delete(mChild1.getID());
        }
        catch (Exception ex){

        }

        Node node =NodeDBH.get(mChild1.getID());
        assertNotNull(node);

        NodeManager.delete(mChild2.getID());
        node = NodeDBH.get(mChild2.getID());
        assertNull(node);
    }

    private void testGetChildNodes(int excepted) throws ParseException {
        List<Node> list = NodeManager.getChildNodes(mChild1.getID());
        assertNotNull(list);
        assertEquals(excepted, list.size());

        for(int i = 0; i < list.size(); i ++){
            assertEquals(mChild1.getID(), list.get(i).getParentID());
        }
    }

	/*
	private void testGetByCode() {
		fail("Not yet implemented");
	}

	private void testGetOCNodes() {
		fail("Not yet implemented");
	}
	*/

}
