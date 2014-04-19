package w1.app.easymoney.data;

import w1.app.easymoney.common.AppContext;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

	public class EasyMoneyOpenHelper extends SQLiteOpenHelper {
		private static EasyMoneyOpenHelper HELPER;
		public static EasyMoneyOpenHelper getHelper(){
			if (HELPER == null){
				HELPER = new EasyMoneyOpenHelper();
			}
			
			return HELPER;
		}
		
		private static final String DATABASE_NAME="easymoney" +
				"" +
				"" +
				"" +
				"";
		private static final int DATABASE_VERSION = 1;
		private static final String TABLE_CREATE_NODE = "CREATE TABLE [Node] (" +
												         "[ID] INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,"+
														"[Code] VARCHAR(16)  NULL,"+
														"[Name] nvarCHAR(128)  NOT NULL,"+
														"[Description] NVARCHAR(512)  NULL,"+
														"[ParentID] iNTEGER  NOT NULL,"+
														"[Level] VARCHAR(128)  NOT NULL,"+
														"[InDate] TIMESTAMP  NOT NULL,"+
														"[InUserID] VARCHAR(16)  NULL,"+
														"[EditDate] TIMESTAMP  NULL,"+
														"[EditUserID] VARCHAR(16)  NULL"+
														")";
		private static final String TABLE_CREATE_TRANSACTION="CREATE TABLE [Transaction] ("+
															"[ID] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,"+
															"[Amount] NUMERIC(8,2)  NOT NULL,"+
															"[Comment] NVARCHAR(512)  NULL,"+
															"[TranDate] DATE  NOT NULL,"+
															"[InDate] TIMESTAMP  NOT NULL,"+
															"[InUserID] VARCHAR(16)  NULL,"+
															"[EditDate] TIMESTAMP  NULL,"+
															"[EditUserID] VARCHAR(16)  NULL"+
															")";
		private static final String TABLE_CREATE_TN_RELATION = "CREATE TABLE [TN_Relation] ("+
																"[ID] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,"+
																"[TranID] INTEGER  NOT NULL,"+
																"[NodeID] INTEGER  NOT NULL,"+
																"[InDate] TIMESTAMP  NOT NULL,"+
																"[InUserID] VARCHAR(16)  NULL,"+
																"[Flag] INTEGER  NOT NULL,"+
                                                                "[Amount] NUMERIC(8,2) NOT NULL"+
																")";
		
		
		private EasyMoneyOpenHelper() {
			super(AppContext.getInstance(), DATABASE_NAME, null, DATABASE_VERSION);
		}
		
	

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(TABLE_CREATE_NODE);
			db.execSQL(TABLE_CREATE_TRANSACTION);
			db.execSQL(TABLE_CREATE_TN_RELATION);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}

	}

