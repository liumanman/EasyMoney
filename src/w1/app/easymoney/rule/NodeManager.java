package w1.app.easymoney.rule;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import w1.app.easymoney.data.DatabaseOperator;
import w1.app.easymoney.data.NodeDBH;
import w1.app.easymoney.entity.Node;
import w1.app.easymoney.entity.Transaction;

public class NodeManager {
	private static int ROOT_PARENT_ID = -1;
	
	public static Node create(String name, String description, int parentID, String inUserID) throws SQLException, ParseException{
		DatabaseOperator.getOperator().beginTransaction();
		try
		{
			Node node = new Node();
			
			node.setName(name);
			node.setDescription(description);
			node.setParentID(parentID);
			node.setInUserID(inUserID);
			node.setInDate(new Date());
			node.setLevel("");
			
			int id = NodeDBH.insert(node);
			
			if (parentID == ROOT_PARENT_ID){
				node.setLevel(String.valueOf(id));
			}else{
				Node parent = NodeDBH.get(parentID);
				node.setLevel(parent.getLevel() + "-" + String.valueOf(id));
			}
			
			node.setID(id);
			
			NodeDBH.update(node);
			DatabaseOperator.getOperator().setTransactionSuccessful();
			
			return node;
		}
		
		finally{
			DatabaseOperator.getOperator().endTransaction();
		}
	}

	public static Node modify(int id, String name, String description, String userID) throws SQLException, ParseException{
		Node node = NodeDBH.get(id);
		
		node.setDescription(description);
		node.setName(name);
		node.setEditDate(new Date());
		node.setEditUserID(userID);
		
		NodeDBH.update(node);
		
		return node;
		
	}
	
	public static void delete(int id) throws Exception{
		List<Node> childNodes = NodeManager.getChildNodes(id);
		if(childNodes != null && childNodes.size() > 0){
			throw new Exception("Can't delete a node which has child nodes.");
		}
		
		List<Transaction> TransactionList = TransactionManager.getByNodeID(id);
		if (TransactionList != null && TransactionList.size() > 0){
			throw new Exception("Can't delete a node which has transaction.");
		}
		
		NodeDBH.delete(id);
	}
	
	public static List<Node> getChildNodes(int id) throws ParseException{
		List<Node> nodes = NodeDBH.getByParentID(id);
		
		return nodes;
	}

	public static Node getByCode(String code) throws ParseException{
		List<Node> nodes = NodeDBH.getByCode(code);
		if (nodes == null || nodes.size()<1){
			return null;
		}else{
			return nodes.get(0);
		}
	}

    public static Node get(int id) throws ParseException {
        return NodeDBH.get(id);
    }

	public static List<Node> getOCNodes() throws ParseException{
		Node root = getByCode(Node.CODE_OUTGOING_CATEGORY);
		return NodeManager.getChildNodes(root.getID());
	}

}
