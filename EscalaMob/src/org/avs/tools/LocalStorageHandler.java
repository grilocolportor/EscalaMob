package org.avs.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LocalStorageHandler extends SQLiteOpenHelper {

	private static final String TAG = LocalStorageHandler.class.getSimpleName();
	
	private static final String DATABASE_NAME = "EscalaMob.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String _ID = "_id";
	private static final String TABLE_NAME_MESSAGES = "escalamob_messages";
	public static final String MESSAGE_RECEIVER = "receiver";
	public static final String MESSAGE_SENDER = "sender";
	private static final String MESSAGE_MESSAGE = "message";
	
	private static final String TABLE_NAME_USUARIO = "escalamob_usuario";
	public static final String USUARIO_NUMERO = "usuario";
	public static final String USUARIO_EMAIL = "email";
	public static final String USUARIO_SENHA = "senha";
	
	private static final String TABLE_MESSAGE_CREATE
	= "CREATE TABLE " + TABLE_NAME_MESSAGES
	+ " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
	+ MESSAGE_RECEIVER + " VARCHAR(25), "
	+ MESSAGE_SENDER + " VARCHAR(25), "
	+ MESSAGE_MESSAGE + " VARCHAR(255));";
	
	private static final String TABLE_USUARIO_CREATE
	= "CREATE TABLE " + TABLE_NAME_USUARIO
	+ " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
	+ USUARIO_NUMERO + " VARCHAR(16), "
	+ USUARIO_EMAIL + " VARCHAR(25), "
	+ USUARIO_SENHA + " VARCHAR(15));";
	
	private static final String TABLE_MESSAGE_DROP = 
			"DROP TABLE IF EXISTS "
			+ TABLE_NAME_MESSAGES;
	
	private static final String TABLE_USUARIO_DROP = 
			"DROP TABLE IF EXISTS "
			+ TABLE_NAME_USUARIO;
	
	public LocalStorageHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_MESSAGE_CREATE);
		db.execSQL(TABLE_USUARIO_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrade database: "+ oldVersion + " V:" + newVersion + "; Nova versão");
		db.execSQL(TABLE_MESSAGE_DROP);
		db.execSQL(TABLE_USUARIO_CREATE);
		onCreate(db);
		
	}
	
	public void insert(String sender, String receiver, String message){
		long rowId = -1;
		try{
			
			SQLiteDatabase db = getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(MESSAGE_RECEIVER, receiver);
			values.put(MESSAGE_SENDER, sender);
			values.put(MESSAGE_MESSAGE, message);
			rowId = db.insert(TABLE_NAME_MESSAGES, null, values);
			
		} catch (SQLiteException e){
			Log.e(TAG, "insert()", e);
		} finally {
			Log.d(TAG, "insert(): rowId=" + rowId);
		}
		
	}
	
	public void insertUsuario(String numero, String email, String senha){
		long rowId = -1;
		try{
			
			SQLiteDatabase db = getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(USUARIO_NUMERO, numero);
			values.put(USUARIO_EMAIL, email);
			values.put(USUARIO_SENHA, senha);
			rowId = db.insert(TABLE_NAME_USUARIO, null, values);
			
		} catch (SQLiteException e){
			Log.e(TAG, "insert()", e);
		} finally {
			Log.d(TAG, "insert(): rowId=" + rowId);
		}
		
	}
	
	public Cursor get(String sender, String receiver) {
					
			SQLiteDatabase db = getWritableDatabase();
			String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME_MESSAGES + " WHERE " + MESSAGE_SENDER + " LIKE '" + sender + "' AND " + MESSAGE_RECEIVER + " LIKE '" + receiver + "' OR " + MESSAGE_SENDER + " LIKE '" + receiver + "' AND " + MESSAGE_RECEIVER + " LIKE '" + sender + "' ORDER BY " + _ID + " ASC";
			return db.rawQuery(SELECT_QUERY,null);
			
			//return db.query(TABLE_NAME_MESSAGES, null, MESSAGE_SENDER + " LIKE ? OR " + MESSAGE_SENDER + " LIKE ?", sender , null, null, _ID + " ASC");
		
	}
	
	public Cursor getUsuario() {
		
		SQLiteDatabase db = getWritableDatabase();
		String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME_USUARIO + ";";
		return db.rawQuery(SELECT_QUERY,null);
		
		//return db.query(TABLE_NAME_MESSAGES, null, MESSAGE_SENDER + " LIKE ? OR " + MESSAGE_SENDER + " LIKE ?", sender , null, null, _ID + " ASC");
	
}
	
	

}
