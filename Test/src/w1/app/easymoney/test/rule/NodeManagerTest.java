//package w1.app.easymoney.test.rule;
//
//import junit.framework.TestCase;
//
//import java.sql.SQLException;
//import java.text.ParseException;
//import java.util.Date;
//import java.util.List;
//
//import w1.app.easymoney.common.Utility;
//import w1.app.easymoney.data.DatabaseOperator;
//import w1.app.easymoney.data.NodeDBH;
//import w1.app.easymoney.entity.Node;
//import w1.app.easymoney.rule.NodeManager;
//
//public class NodeManagerTest extends TestCase {
//    private Node mRoot;
//    private Node mChild1;
//    private Node mChild2;
//
//
//
//
//
//
//    public void test() throws Exception{
//        this.testCreate();
//        this.testModify();
//        this.testGetChildNodes(3);
//        this.testDelete();
//        this.testGetChildNodes(2);
//
//        this.testGetAllByCode();
//    }
//
//    private void testCreate() throws SQLException, ParseException {
//        //-------insert root node------
//        String name = "name1";
//        String description = "description1";
//        int parentID = -1;
//        String inUserID = "user1";
//
//        mRoot = NodeManager.create(name, description, parentID, inUserID);
//        assertNotNull(mRoot);
//        assertEquals(name, mRoot.getName());
//        assertEquals(description, mRoot.getDescription());
//        assertEquals(parentID, mRoot.getParentID());
//        assertEquals(inUserID, mRoot.getInUserID());
//        assertNotNull(mRoot.getInDate());
//        assertEquals(mRoot.getLevel(), String.valueOf(mRoot.getID()));
//        assertNull(mRoot.getEditDate());
//        assertNull(mRoot.getEditUserID());
//        assertNull(mRoot.getCode());
//
//
//        Node node2 = NodeDBH.get(mRoot.getID());
//        assertNotNull(node2);
//        assertEquals(name, node2.getName());
//        assertEquals(description, node2.getDescription());
//        assertEquals(parentID, node2.getParentID());
//        assertEquals(inUserID, node2.getInUserID());
//        assertNotNull(node2.getInDate());
//        assertEquals(node2.getLevel(), String.valueOf(node2.getID()));
//        assertNull(node2.getEditDate());
//        assertNull(node2.getEditUserID());
//        assertNull(node2.getCode());
//
//        //--------insert level 1 child-------------
//        mChild1 = NodeManager.create("child1", "desc-child1", mRoot.getID(), "user2");
//        assertEquals(mChild1.getLevel(), mRoot.getLevel()+"-"+String.valueOf(mChild1.getID()));
//
//        node2 = NodeDBH.get(mChild1.getID());
//        assertNotNull(node2);
//        assertEquals(node2.getLevel(), mRoot.getLevel()+"-"+String.valueOf(mChild1.getID()));
//
//
//        //---------insert level 2 child--------------
//        mChild2 = NodeManager.create("child2", "desc-child2", mChild1.getID(), "user3");
//        assertEquals(mChild2.getLevel(), mChild1.getLevel()+"-"+String.valueOf(mChild2.getID()));
//
//        node2 = NodeDBH.get(mChild2.getID());
//        assertNotNull(node2);
//        assertEquals(node2.getLevel(), mChild1.getLevel()+"-"+String.valueOf(node2.getID()));
//
//        //-------------more------
//        NodeManager.create("child3", "desc-child3", mChild1.getID(), "user4");
//        NodeManager.create("child4", "desc-child4", mChild1.getID(), "user5");
//
//
//    }
//
//    private void testModify() throws SQLException, ParseException {
//        String name = "updated root name";
//        String description="updated root description";
//        String userID="user_u";
//
//        Node oldRoot = mRoot;
//        mRoot = NodeManager.modify(mRoot.getID(), name, description, userID);
//        assertNotNull(mRoot);
//        assertEquals(oldRoot.getCode(), mRoot.getCode());
//        assertEquals(description, mRoot.getDescription());
//        assertEquals(userID, mRoot.getEditUserID());
//        assertEquals(oldRoot.getID(), mRoot.getID());
//        assertEquals(oldRoot.getLevel(), mRoot.getLevel());
//        assertEquals(name, mRoot.getName());
//        assertEquals(oldRoot.getParentID(), mRoot.getParentID());
//        assertNotNull(mRoot.getEditDate());
//        assertEquals(Utility.DateToString(oldRoot.getInDate()), Utility.DateToString(mRoot.getInDate()));
//
//        Node newRoot = NodeDBH.get(mRoot.getID());
//        assertNotNull(newRoot);
//        assertEquals(mRoot.getCode(), newRoot.getCode());
//        assertEquals(mRoot.getDescription(), newRoot.getDescription());
//        assertEquals(mRoot.getEditUserID(),newRoot.getEditUserID());
//        assertEquals(mRoot.getID(), newRoot.getID());
//        assertEquals(mRoot.getInUserID(), newRoot.getInUserID());
//        assertEquals(mRoot.getLevel(), newRoot.getLevel());
//        assertEquals(mRoot.getName(), newRoot.getName());
//        assertEquals(mRoot.getParentID(), newRoot.getParentID());
//        assertEquals(Utility.DateToString(mRoot.getEditDate()) , Utility.DateToString(newRoot.getEditDate()));
//        assertEquals(Utility.DateToString(mRoot.getInDate()),Utility.DateToString(newRoot.getInDate()));
//
//
//    }
//
//    private void testDelete() throws Exception {
//        try{
//            NodeManager.delete(mChild1.getID());
//        }
//        catch (Exception ex){
//
//        }
//
//        Node node =NodeDBH.get(mChild1.getID());
//        assertNotNull(node);
//
//        NodeManager.delete(mChild2.getID());
//        node = NodeDBH.get(mChild2.getID());
//        assertNull(node);
//    }
//
//    private void testGetChildNodes(int excepted) throws ParseException {
//        List<Node> list = NodeManager.getChildNodes(mChild1.getID());
//        assertNotNull(list);
//        assertEquals(excepted, list.size());
//
//        for(int i = 0; i < list.size(); i ++){
//            assertEquals(mChild1.getID(), list.get(i).getParentID());
//        }
//    }
//
//    private void testGetAllByCode() throws SQLException, ParseException {
//       Node root = new Node();
//        root.setCode(Node.CODE_OUTGOING_CATEGORY);
//        root.setDescription("Outgoing category root");
//        root.setInUserID("1w");
//        root.setName("Outgoing");
//        root.setParentID(-1);
//        root.setLevel("");
//        root.setInDate(new Date());
//
//        root.setID(NodeDBH.insert(root));
//        NodeDBH.update(root);
//
//        Node food = NodeManager.create("Food","Food", root.getID(), "1w");
//        Node house = NodeManager.create("house","house", root.getID(), "1w");
//        Node other = NodeManager.create("Other", "Other", root.getID(), "iw");
//
//        Node f_drink = NodeManager.create("Drink", "Drink", food.getID(), "1w");
//        Node f_restrent = NodeManager.create("restent", "restent", food.getID(),"1w");
//
//        Node f_d_tea = NodeManager.create("tea", "tea", f_drink.getID(), "1w");
//
//        Node h_hoa = NodeManager.create("Hoa", "hoa", house.getID(),"1w");
//
//
//        List<Node> list = NodeManager.getAllByCode(Node.CODE_OUTGOING_CATEGORY);
//
//        assertNotNull(list);
//        assertEquals(3, list.size());
//
//     for( int i = 0; i < list.size(); i ++){
//         String name = list.get(i).getName();
//         if (name.equals("Food")){
//             assertNotNull(list.get(i).getChildren());
//             assertEquals(2, list.get(i).getChildren().size());
//
//             continue;
//         }
//
//         if (name.equals("house")){
//             assertNotNull(list.get(i).getChildren());
//             assertEquals(1, list.get(i).getChildren().size());
//
//             continue;
//         }
//
//         if(name.equals("Other")){
//             assertNull(list.get(i).getChildren());
//
//             continue;
//         }
//
//         fail("wrong item.");
//     }
//
//    }
//	/*
//	private void testGetByCode() {
//		fail("Not yet implemented");
//	}
//
//	private void testGetOCNodes() {
//		fail("Not yet implemented");
//	}
//	*/
//
//}
