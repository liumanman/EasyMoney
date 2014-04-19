package w1.app.easymoney.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import w1.app.easymoney.common.Utility;
import w1.app.easymoney.entity.Transaction;

public class TransactionDBH {
	public static int insert(Transaction tran) throws SQLException, java.sql.SQLException{
		ContentValues cv = new ContentValues(8);
		cv.put(Transaction.COLUMN_AMOUNT, tran.getAmount());
		cv.put(Transaction.COLUMN_COMMENT, tran.getComment());
		cv.put(Transaction.COLUMN_EDITDATE, Utility.DateToString(tran.getEditDate()));
		cv.put(Transaction.COLUMN_EDITUSERID, tran.getEditUserID());
		//cv.put(Transaction.COLUMN_ID, tran.getID());
		cv.put(Transaction.COLUMN_INDATE, Utility.DateToString(tran.getInDate()));
		cv.put(Transaction.COLUMN_INUSERID, tran.getInUserID());
		cv.put(Transaction.COLUMN_TRANDATE, Utility.DateToString(tran.getTranDate()));
		
		return (int) DatabaseOperator.getOperator().getWritableDatabase().insertOrThrow(Transaction.TABLE_NAME, null, cv);
	}
	
	public static void update(Transaction tran) throws Exception {
		throw new Exception("not finished.");
	}
	
	public static void delete(int id) throws java.sql.SQLException {
	    DatabaseOperator.getOperator().getWritableDatabase().delete(Transaction.TABLE_NAME,"id=?" ,new String[]{String.valueOf(id)});
	}
	
	public static Transaction get(int id) throws ParseException{
		Cursor c = DatabaseOperator.getOperator().getReadableDatabase().query(Transaction.TABLE_NAME
															//, new String[] {Transaction.COLUMN_AMOUNT,Transaction.COLUMN_COMMENT,Transaction.COLUMN_EDITDATE,Transaction.COLUMN_EDITUSERID,Transaction.COLUMN_ID,Transaction.COLUMN_INDATE,Transaction.COLUMN_INUSERID,Transaction.COLUMN_TRANDATE}
															,null
															, "ID=?", new String[] {String.valueOf(id)}
															, null, null, null);
		if (c.getCount() > 0 && c.moveToFirst()){
			return createTransactionFromCursor(c);
		}else{
			return null;
		}
	}
	
	public static List<Transaction> getByNodeID(int nodeID) throws Exception {
		throw new Exception("not finished.");
	}

    public static List<Transaction> query(Date start, Date end, int[] nodeIDs) throws java.sql.SQLException, ParseException {
//       String sql = "select * from [transaction] a inner join tn_Relation b on a.id=b.tranid" +
//               " where (a.TranDate>=?) and a.TranDate<? and b.nodeID in (?);";

            String sql = "select * from [transaction] a inner join tn_Relation b on a.id=b.tranid" +
              " where a.TranDate>=? and a.TranDate<? and  b.nodeID in (?);";

       String s;
        if (start == null){

          s = "0000";
        }else{
            s = Utility.DateToString(start);
        }


        String e;
        if (end == null){
            e = "2050-01-01";
        }else{
            e = Utility.DateToString(end);
        }

        StringBuilder sb = new StringBuilder(nodeIDs.length*6);
       sb.append(nodeIDs[0]);
        for(int i = 1; i < nodeIDs.length; i ++){
            sb.append(",");
            sb.append(nodeIDs[i]);


        }

        Cursor c = DatabaseOperator.getOperator().getWritableDatabase().rawQuery(sql, new String[]{s,e, sb.toString()});
        if(c == null || c.getCount() < 1){
            return null;
        }else{
            List<Transaction> list = new ArrayList<Transaction>(c.getCount());
            while (c.moveToNext()){
                list.add(createTransactionFromCursor(c));
            }

            return list;
        }

    }
	private static Transaction createTransactionFromCursor(Cursor c) throws ParseException{
		Transaction t = new Transaction();
		
		t.setAmount(c.getInt(c.getColumnIndex(Transaction.COLUMN_AMOUNT)));
		t.setComment(c.getString(c.getColumnIndex(Transaction.COLUMN_COMMENT)));
		t.setEditDate(Utility.StringToDate(c.getString(c.getColumnIndex(Transaction.COLUMN_EDITDATE))));
		t.setEditUserID(c.getString(c.getColumnIndex(Transaction.COLUMN_EDITUSERID)));
		t.setID(c.getInt(c.getColumnIndex(Transaction.COLUMN_ID)));
		t.setInDate(Utility.StringToDate(c.getString(c.getColumnIndex(Transaction.COLUMN_INDATE))));
		t.setInUserID(c.getString(c.getColumnIndex(Transaction.COLUMN_INUSERID)));
		t.setTranDate(Utility.StringToDate(c.getString(c.getColumnIndex(Transaction.COLUMN_TRANDATE))));
		
		return t;
	}



}
