package w1.app.easymoney.data;

import java.sql.SQLException;
import java.util.Hashtable;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOperator {
	private SQLiteOpenHelper mHelper;
	private Hashtable<Thread, SQLiteDatabase> mTable;
	
	private static DatabaseOperator OPERATOR;
	public static DatabaseOperator getOperator(){
		if (OPERATOR == null){
			OPERATOR = new DatabaseOperator();
		}
		
		return OPERATOR;
	}
	
	public DatabaseOperator(){
		this.mHelper = EasyMoneyOpenHelper.getHelper();
		this.mTable = new Hashtable<Thread, SQLiteDatabase>(8);
	}
	
	public SQLiteDatabase getWritableDatabase() throws SQLException{
		if (this.mTable.size() < 1){
			return this.mHelper.getWritableDatabase();
		}
		
		Thread t = Thread.currentThread();
		SQLiteDatabase database = this.mTable.get(t);
		if (database == null){
			return this.mHelper.getWritableDatabase();
		}else{
			if (database.inTransaction()){
				return database;
			}else{
				throw new SQLException("Transaction is timeout.");
			}
		}
		
	}
	
	public SQLiteDatabase getReadableDatabase(){
		return this.mHelper.getReadableDatabase();
	}
	
	public void beginTransaction() throws SQLException{
		Thread t = Thread.currentThread();
		SQLiteDatabase database = this.mTable.get(t);
		if (database == null){
			database = this.mHelper.getWritableDatabase();
			database.beginTransaction();
			this.mTable.put(t, database);
		}else{
			if (!database.inTransaction()){
				throw new SQLException("Transaction is timeout.");
			}
		}
	}
	
	public void setTransactionSuccessful() throws SQLException{
		Thread t = Thread.currentThread();
		SQLiteDatabase database = this.mTable.get(t);
		if (database == null){
			throw new SQLException("No transaction begin.");
		}else{
			database.setTransactionSuccessful();
		}
	}
	
	public void endTransaction() throws SQLException{
		Thread t = Thread.currentThread();
		SQLiteDatabase database = this.mTable.get(t);
		if (database == null){
			throw new SQLException("No transaction begin.");
		}else{
			database.endTransaction();
			this.mTable.remove(t);
		}
	}
}
