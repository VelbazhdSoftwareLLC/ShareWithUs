package eu.veldsoft.share.with.us.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * SQLite database helper class.
 * 
 * @author Ventsislav Medarov
 */
public class MessageHistoryDatabaseHelper extends SQLiteOpenHelper {
	/**
	 * 
	 * @author Ventsislav Medarov
	 */
	public static abstract class MessageHistoryColumns implements BaseColumns {
		public static final String TABLE_NAME = "history";
		public static final String COLUMN_NAME_MESSAGE_HASH = "message_hash";
		public static final String COLUMN_NAME_MESSAGE_REGISTERED = "registered";
	}

	/**
	 * Database integer version.
	 */
	public static final int DATABASE_VERSION = 1;

	/**
	 * Databse file name.
	 */
	public static final String DATABASE_NAME = "MessageHistory.db";

	/**
	 * Create table SQL patter.
	 */
	static final String SQL_CREATE_HISTORY = "CREATE TABLE "
			+ MessageHistoryColumns.TABLE_NAME + " ("
			+ MessageHistoryColumns._ID + " INTEGER PRIMARY KEY,"
			+ MessageHistoryColumns.COLUMN_NAME_MESSAGE_HASH + " TEXT, "
			+ MessageHistoryColumns.COLUMN_NAME_MESSAGE_REGISTERED + " TEXT)";

	/**
	 * Drop database SQL pattern.
	 */
	static final String SQL_DELETE_HISTORY = "DROP TABLE IF EXISTS "
			+ MessageHistoryColumns.TABLE_NAME;

	/**
	 * Helper class constructor.
	 * 
	 * @param context
	 *            Context of the database usage.
	 */
	public MessageHistoryDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_HISTORY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_HISTORY);
		onCreate(db);
	}

	/**
	 * Store last known message information inside the local database.
	 * 
	 * @param hash
	 *            Hash code of the message.
	 * @param registered
	 *            Data and time of the message registraion in the global
	 *            database.
	 */
	public void setLastMessage(String hash, String registered) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MessageHistoryColumns.COLUMN_NAME_MESSAGE_HASH, hash);
		values.put(MessageHistoryColumns.COLUMN_NAME_MESSAGE_REGISTERED,
				registered);
		db.insert(MessageHistoryColumns.TABLE_NAME, null, values);
	}

	/**
	 * Get last known message hash.
	 * 
	 * @return Hash of the last known message.
	 */
	public String getLastMessageHash() {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db
				.query(MessageHistoryColumns.TABLE_NAME,
						new String[] { MessageHistoryColumns.COLUMN_NAME_MESSAGE_HASH },
						null, null, null, null,
						MessageHistoryColumns.COLUMN_NAME_MESSAGE_REGISTERED
								+ " DESC", "1");

		if (cursor.getCount() == 0) {
			return "";
		}

		cursor.moveToFirst();
		String result = cursor.getString(0);
		cursor.close();

		return result;
	}
}
