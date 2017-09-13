package cam.surveil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseAdapter {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_ip = "ip";
	public static String KEY_port = "port";
	public static final String KEY_description = "description";
	public static final String KEY_remark = "remark";
	
	
	private static final String TAG = "DatabaseAdapter";
	private  static final String DATABASE_NAME = "config_strore";
	private  static final String DATABASE_TABLE = "ip_table";
	private  static final int DATABASE_VERSION = 1;
	
	private  static final String DATABASE_CREATE = "create table ip_table "
		+ "(_id integer primary key autoincrement, "
		+ "ip text,port text,description numeric, remark text);";
	
	private Context context = null;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	public DatabaseAdapter(Context ctx){
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	
	public DatabaseAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		DBHelper.close();
	}
	
	public long insertIPport(String ip, String port, String description, String remark)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ip, ip);
		initialValues.put(KEY_port, port);
		initialValues.put(KEY_description, description);
		initialValues.put(KEY_remark, remark);
		return db.insert(DATABASE_TABLE, null, initialValues);
		
	}
	
	public boolean deleteIPport(long rowId)
	{
		return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	public boolean updateport(long rowId, String ip, String port, double description, String remark)
	{
		
		ContentValues args = new ContentValues();
		args.put(KEY_ip, ip);
		args.put(KEY_port, port);
		args.put(KEY_description, description);
		args.put(KEY_remark, remark);
		return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	public Cursor getAllIPport()
	{
		return db.query(DATABASE_TABLE, new String[] {
		KEY_ROWID,
		KEY_ip,
		KEY_port,
		KEY_description,
		KEY_remark}, 
		null, 
		null, 
		null, 
		null, 
		null);
	}
	
	//--- Retrieves a particular port ---
	public Cursor getIPport(long rowId) throws SQLException
	{
		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[]{
		KEY_ROWID,
		KEY_ip,
		KEY_port,
		KEY_description,
		KEY_remark
		}, 
		KEY_ROWID + "=" + rowId,
		null,
		null, 
		null, 
		null, 
		null);
		
		if(mCursor != null){
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper{
		DatabaseHelper(Context context){
			super(context, DATABASE_NAME, null,
			DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db){
			db.execSQL(DATABASE_CREATE);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db,
		int oldVersion,int newVersion){
			
		Log.w(TAG, "Upgrading database from version "
				+ oldVersion + "to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS port");
		onCreate(db);
		
		}
	}
}
