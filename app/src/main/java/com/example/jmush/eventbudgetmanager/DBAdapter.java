package com.example.jmush.eventbudgetmanager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.jar.Attributes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.jmush.eventbudgetmanager.DBAdapter.DBHelper.KEY_NTID;
import static com.example.jmush.eventbudgetmanager.DBAdapter.DBHelper.TABLE_MEMBERS;

public class DBAdapter {


	private DBHelper helper;

	private Context  context;
	public DBAdapter(Context context) {
		this.context=context;
		helper = new DBHelper(context);

	}

	SQLiteDatabase db;

	static class DBHelper extends SQLiteOpenHelper {

		// private Context context;
		private static final String DATABASE_NAME = "EventManager";
		public static final String TABLE_USER = "user";
		public static final String KEY_NTID = "NTID";
		public static final String TABLE_MEMBERS = "member";
		private static final int DATABASE_VERSION = 1;


		private static final String CREATE_TABLE_USER = "CREATE TABLE "
				+ TABLE_USER + "( "
				+ "ID integer primary key autoincrement, "
				+ "NAME text, EMAIL text, PASSWORD text); ";

		private static final String CREATE_TABLE_MEMEBERS = "CREATE TABLE "
				+ TABLE_MEMBERS + "( "
				+ "NTID text primary key  , "
				+ "MEMBERNAME text, PHONENUMBER text, AMOUNT INTEGER DEFAULT 0); ";


		private static final String DROP_TABLE = "DROP TABLE IF EXISTS "
				+ TABLE_USER;

		private static final String DROP_TABLE1 = "DROP TABLE IF EXISTS "
				+ TABLE_MEMBERS;



		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_TABLE_USER);
			db.execSQL(CREATE_TABLE_MEMEBERS);


			Log.d("DB", "Creating Table");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d("DB", "Deleting old and Creating New Table");
			db.execSQL(DROP_TABLE);
			db.execSQL(CREATE_TABLE_USER);

			db.execSQL(DROP_TABLE1);
			db.execSQL(CREATE_TABLE_MEMEBERS);

		}

	}



	public long insertDetails(String Name, String Email,
			String password) {
		db = helper.getWritableDatabase();
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("NAME", Name);
		newValues.put("EMAIL", Email);
		newValues.put("PASSWORD", password);
		Log.d("Inserting", "Inserted");
		// Insert the row into your table
		return db.insert(DBHelper.TABLE_USER, null, newValues);

	}



	public  long insertMemebers(String Ntid, String Name, String Phone, int Amount){

		db=helper.getWritableDatabase();
		ContentValues newValues=new ContentValues();
		newValues.put("NTID",Ntid);
		newValues.put("MEMBERNAME",Name);
		newValues.put("PHONENUMBER",Phone);
        newValues.put("AMOUNT", Amount);
		Log.d("Inserting Members", "Inserted");
		return db.insert(TABLE_MEMBERS,null,newValues);
	}


	// Deleting single contact
	public void deleteMember(RetreiveName contact) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete(TABLE_MEMBERS, KEY_NTID + " = ?",
				new String[] { String.valueOf(contact.getNtid()) });
		db.close();
	}

	public int updateMember(RetreiveName retreiveName) {

		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("MEMBERNAME",retreiveName.getMemberName() );
		values.put("PHONENUMBER",retreiveName.getPhone());

		// updating row
		return db.update(TABLE_MEMBERS, values, KEY_NTID + " = ?",
				new String[] { String.valueOf(retreiveName.getNtid()) });
	}

    public int updateFunds(RetreiveName retreiveName) {

        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("AMOUNT",retreiveName.getAmount());
        // updating row
        return db.update(TABLE_MEMBERS, values, KEY_NTID + " = ?",
                new String[] { String.valueOf(retreiveName.getNtid()) });
    }


    public int getTotalOfAmount(){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT SUM(AMOUNT) FROM " +TABLE_MEMBERS,null);
        c.moveToFirst();
        int i=c.getInt(0);
        c.close();
        return i;
    }





    public ArrayList<RetreiveName> getAllNames() {
		ArrayList<RetreiveName> nameList = new ArrayList<RetreiveName>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_MEMBERS;

		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				RetreiveName names = new RetreiveName();
				names.setNtid(cursor.getString(0));
				names.setMemberName(cursor.getString(1));
				names.setPhone(cursor.getString(2));
                names.setAmount(cursor.getInt(3));

				// Adding contact to list
				nameList.add(names);
			} while (cursor.moveToNext());
		}

		// return contact list
		return nameList;
	}







    @SuppressLint("SimpleDateFormat")
	private String DATETIME(String string) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}




	public int ExistsEmail(String id) {// "SELECT id from users WHERE email = ?"

		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "SELECT * from " + DBHelper.TABLE_USER
				+ " WHERE EMAIL = '" + id + "';";
		Cursor cursor = db.rawQuery(sql, null);
		int t = cursor.getCount();
		cursor.close();
		db.close();
		return t;
	}


	public int ExistsNtid(String ntid) {// "SELECT id from users WHERE email = ?"

		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "SELECT * from " + TABLE_MEMBERS
				+ " WHERE NTID = '" + ntid + "';";
		Cursor cursor = db.rawQuery(sql, null);
		int t = cursor.getCount();
		cursor.close();
		db.close();
		return t;
	}

	public Cursor Login(String email, String password) {// "SELECT id from users WHERE email = ?"

		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "SELECT * from " + DBHelper.TABLE_USER
				+ " WHERE EMAIL = '" + email + "' and PASSWORD ='"
				+ password + "' ;";
		Cursor cursor = db.rawQuery(sql, null);
		int t = cursor.getCount();
			if(t>0)
			{
				if (cursor != null)
					cursor.moveToFirst();
					String name=cursor.getString(cursor.getColumnIndex("NAME"));

			}

		return cursor;
	}



}
