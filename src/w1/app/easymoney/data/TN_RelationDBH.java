package w1.app.easymoney.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import w1.app.easymoney.common.Utility;
import w1.app.easymoney.entity.TN_Relation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TN_RelationDBH {
	public static int insert(TN_Relation tn) throws SQLException, java.sql.SQLException{
		ContentValues cv = new ContentValues(6);
		
		cv.put(TN_Relation.COLUMN_FLAG, tn.getFlag());
		//cv.put(TN_Relation.COLUMN_ID, tn.getID());
		cv.put(TN_Relation.COLUMN_INDATE, Utility.DateToString(tn.getInDate()));
		cv.put(TN_Relation.COLUMN_INUSERID, tn.getInUserID());
		cv.put(TN_Relation.COLUMN_NODEID, tn.getNodeID());
		cv.put(TN_Relation.COLUMN_TRANID, tn.getTranID());
		
		return (int) DatabaseOperator.getOperator().getWritableDatabase().insertOrThrow(TN_Relation.TABLE_NAME, null, cv);
	}
	
	public static void update(TN_Relation tn){
		
	}
	
	public static void delete(int id) throws java.sql.SQLException {
        DatabaseOperator.getOperator().getWritableDatabase().delete(TN_Relation.TABLE_NAME, "id=?", new String[] {String.valueOf(id)});
	}
	
	public static void deleteBYTranID(int tranID) throws java.sql.SQLException {
        DatabaseOperator.getOperator().getWritableDatabase().delete(TN_Relation.TABLE_NAME, "TranID=?", new String[] {String.valueOf(tranID)});
	}

    public static List<TN_Relation> getByTranID(int tranID) throws ParseException {
        Cursor cursor = DatabaseOperator.getOperator().getReadableDatabase().query(TN_Relation.TABLE_NAME, null, "TranID=?", new String[]{String.valueOf(tranID)}, null, null, null);

        ArrayList<TN_Relation> list = new ArrayList<TN_Relation>(cursor.getCount());
        while(cursor.moveToNext()){
            list.add(createTNRelationFromCursor(cursor));
        }

        return list;
    }

    private static TN_Relation createTNRelationFromCursor(Cursor cursor) throws ParseException {
        TN_Relation r = new TN_Relation();

        r.setFlag(cursor.getInt(cursor.getColumnIndex(TN_Relation.COLUMN_FLAG)));
        r.setID(cursor.getInt(cursor.getColumnIndex(TN_Relation.COLUMN_ID)));
        r.setInDate(Utility.StringToDate(cursor.getString(cursor.getColumnIndex(TN_Relation.COLUMN_INDATE))));
        r.setInUserID(cursor.getString(cursor.getColumnIndex(TN_Relation.COLUMN_INUSERID)));
        r.setNodeID(cursor.getInt(cursor.getColumnIndex(TN_Relation.COLUMN_NODEID)));
        r.setTranID(cursor.getInt(cursor.getColumnIndex(TN_Relation.COLUMN_TRANID)));

        return r;
    }
}
