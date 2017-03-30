package com.spp.keispp.sqllibv2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.PorterDuff;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 3/27/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    //database name and table name
    private static final String DATABASE_NAME = "Username";
    private static final String TABLE_USER = "User";

    // input column name
    private static final String COL_ID = "id";
    private static final String COL_USER = "Username";
    private static final String COL_PASS = "Password";
    private static final String COL_PIN = "PIN";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COL_ID + " INTEGER PRIMARY KEY, "
                + COL_USER + " TEXT, "
                + COL_PASS + " TEXT, "
                + COL_PIN + " TEXT)";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        onCreate(db);
    }

    /**
     * CODE NAME: OPERATION C.R.U.D.
     **/

    //adding user
    public void addUser(ModelUser modelUser) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_USER, modelUser.get_User());
        values.put(COL_PASS, modelUser.get_Pass());
        values.put(COL_PIN, modelUser.get_PIN());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    //get one user
    public ModelUser getModelUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER,
                new String[]{COL_ID, COL_USER, COL_PASS, COL_PIN}, COL_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        ModelUser modelUser = new ModelUser(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));

        cursor.close();
        return modelUser;

    }

    //get all user
    public List<ModelUser> getAllModelUser() {
        List<ModelUser> modelUserList = new ArrayList<ModelUser>();

        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ModelUser modelUser = new ModelUser();
                modelUser.set_id(Integer.parseInt(cursor.getString(0)));
                modelUser.set_User(cursor.getString(1));
                modelUser.set_Pass(cursor.getString(2));
                modelUser.set_PIN(cursor.getString(3));

                modelUserList.add(modelUser);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return modelUserList;
    }

    //get user count
    public int getUsersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    //update single user and pass
    public int updateUser(ModelUser modelUser) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_USER, modelUser.get_User());
        values.put(COL_PASS, modelUser.get_Pass());
        values.put(COL_PIN, modelUser.get_PIN());

        return db.update(TABLE_USER, values, COL_ID + "=?",
                new String[]{String.valueOf(modelUser.get_id())});
    }

    //delete single user
    public void deleteUser(ModelUser modelUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, COL_ID + "=?",
                new String[]{String.valueOf(modelUser.get_id())});

        db.close();
    }

}
