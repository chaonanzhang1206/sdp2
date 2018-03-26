package team4.sdp.uconn.sdp2018_team4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DatabaseHelper";

    public static final String DATABASE_NAME = "UserManager.db";
    public static final String TABLE_NAME = "usersinfo_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "TIME";
    public static final String COL_4 = "FAVORITEID";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TALBE = "CREATE TALBE" + TABLE_NAME + "(" + COL_1 + "INTEGER PRIMARY KEY NOT NULL," + COL_2 + " TEXT," + COL_3 + "TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL," + COL_4 + " TEXT " + ")";
        db.execSQL(CREATE_USER_TALBE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String name, String time, String favoriteid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, time);
        contentValues.put(COL_4, favoriteid);

        Log.d(TAG, "addData: Adding " + name + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    /***
     * Returns all the data from database
     * @return
     */

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }

    /***
     * Retruns only the ID that matches the name passed in
     * @param name
     * @param time
     * @param favoriteid
     * @return
     */
    public Cursor getData(String name, String time, String favoriteid){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT " + COL_1 + " FROM " + TABLE_NAME +
                " WHERE " + COL_2 + " = ' " + name + " AND " + COL_3 + " = ' " + time + " AND "
                + COL_4 + " = '" + favoriteid + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /***
     * Updates the name field
     * @param newName
     * @param id
     * @param oldName
     */
    public void updataName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " + TABLE_NAME + " SET " + COL_2 + " = '"
                + newName + "' WHERE " + COL_1 + " = '" + id + "'" + " AND "
                + COL_2 + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query:" + query);
        Log.d(TAG, "updateName: Setting name to" + newName);
        db.execSQL(query);
    }

    /***
    public boolean updataData(String id, String name, String time, String favoriteid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, time);
        contentValues.put(COL_4, favoriteid);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] { id });
        return true;
    }
     */

    /***
     * Delete from database
     * @param id
     * @param name
     * @param time
     * @param favoriteid
     */
    public void deleteData(int id, String name, String time, String favoriteid){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM" + TABLE_NAME + "WHERE" + COL_1 + "='" + id + "AND" +
                COL_2 + " = '" + name + "AND" + COL_3 + "='" + time + "AND" + COL_4 + "='" +
                favoriteid + "'";
        Log.d(TAG, "deleteData: query:" + query);
        Log.d(TAG, "deleteData: Deleting" + name + "from database.");
        db.execSQL(query);
    }

    /***
    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] { id });
    }
    */
}
