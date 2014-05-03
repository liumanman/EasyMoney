package w1.app.easymoney.data;

import java.text.ParseException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import w1.app.easymoney.common.Utility;
import w1.app.easymoney.model.Node;


public class NodeDBH {
	
	public static int insert(Node node) throws SQLException{
		ContentValues cv = new ContentValues(9);
		cv.put(Node.COLUMN_CODE, node.getCode());
		cv.put(Node.COLUMN_NAME, node.getName());
		cv.put(Node.COLUMN_DESCRIPTION, node.getDescription());
		cv.put(Node.COLUMN_PARENTID, node.getParentID());
		cv.put(Node.COLUMN_LEVEL, node.getLevel());
		cv.put(Node.COLUMN_INDATE, Utility.DateToString(node.getInDate()));
		cv.put(Node.COLUMN_INUSERID, node.getInUserID());
		cv.put(Node.COLUMN_EDITDATE, Utility.DateToString(node.getEditDate()));
		cv.put(Node.COLUMN_EDITUSERID, node.getEditUserID());
		
		return (int) DatabaseOperator.getOperator().getWritableDatabase().insertOrThrow(Node.TABLE_NAME, null, cv);
	}
	
	public static void update(Node node) throws SQLException{
		ContentValues cv = new ContentValues(9);
		cv.put(Node.COLUMN_CODE, node.getCode());
		cv.put(Node.COLUMN_NAME, node.getName());
		cv.put(Node.COLUMN_DESCRIPTION, node.getDescription());
		cv.put(Node.COLUMN_PARENTID, node.getParentID());
		cv.put(Node.COLUMN_LEVEL, node.getLevel());
		cv.put(Node.COLUMN_INDATE, Utility.DateToString(node.getInDate()));
		cv.put(Node.COLUMN_INUSERID, node.getInUserID());
		cv.put(Node.COLUMN_EDITDATE, Utility.DateToString(node.getEditDate()));
		cv.put(Node.COLUMN_EDITUSERID, node.getEditUserID());
		
		DatabaseOperator.getOperator().getWritableDatabase().update(Node.TABLE_NAME, cv, "id=?", new String[] {String.valueOf(node.getID()) });
		
	}
	
	public static void delete(int id) throws SQLException{
		DatabaseOperator.getOperator().getWritableDatabase().delete(Node.TABLE_NAME, "id=?", new String[] {String.valueOf(id)});
	}
	
	public static Node get(int id) throws ParseException{
		Cursor c = DatabaseOperator.getOperator().getReadableDatabase().query(Node.TABLE_NAME
				, null
				, "ID=?"
				,new String[] {String.valueOf(id)}
				, null
				, null
				, null);
		
		if (c == null || c.getCount() < 1){
			return null;
		}else{
			c.moveToFirst();
			
			Node node = createNodeFromCursor(c);
			
			return node;
		}
	}
	
	public static List<Node> getByParentID(int parentID) throws ParseException{
		Cursor c = DatabaseOperator.getOperator().getReadableDatabase().query(Node.TABLE_NAME
				, null
				, "ParentID=?"
				,new String[] {String.valueOf(parentID)}
				, null
				, null
				, null);
		
		if (c == null || c.getCount() < 1){
			return null;
		}else{
			List<Node> list = new ArrayList<Node>(c.getCount());
			
			while(c.moveToNext()){
				list.add(createNodeFromCursor(c));
			}
			
			return list;
		}
	}
	
	public static List<Node> getByCode(String code) throws ParseException{
		Cursor c = DatabaseOperator.getOperator().getReadableDatabase().query(Node.TABLE_NAME
				, null
				, "Code=?"
				,new String[] {code}
				, null
				, null
				, null);
		
		if (c == null || c.getCount() < 1){
			return null;
		}else{
			List<Node> list = new ArrayList<Node>(c.getCount());
			
			while(c.moveToNext()){
				list.add(createNodeFromCursor(c));
			}
			
			return list;
		}
	}
	
	
	private static Node createNodeFromCursor(Cursor c) throws ParseException{
		Node node = new Node();
		node.setID(c.getInt(c.getColumnIndex(Node.COLUMN_ID)));
		node.setCode(c.getString(c.getColumnIndex(Node.COLUMN_CODE)));
		node.setName(c.getString(c.getColumnIndex(Node.COLUMN_NAME)));
		node.setDescription(c.getString(c.getColumnIndex(Node.COLUMN_DESCRIPTION)));
		node.setParentID(c.getInt(c.getColumnIndex(Node.COLUMN_PARENTID)));
		node.setLevel(c.getString(c.getColumnIndex(Node.COLUMN_LEVEL)));
		node.setInDate(Utility.StringToDate(c.getString(c.getColumnIndex(Node.COLUMN_INDATE))));
		node.setInUserID(c.getString(c.getColumnIndex(Node.COLUMN_INUSERID)));
		node.setEditDate(Utility.StringToDate(c.getString(c.getColumnIndex(Node.COLUMN_EDITDATE))));
		node.setEditUserID(c.getString(c.getColumnIndex(Node.COLUMN_EDITUSERID)));
		
		return node;
	}
}
