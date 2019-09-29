package com.timeline.rss2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

public class DbOpenHelper {
    private static final String DATABASE_NAME = "goodoc.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;
    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Database.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS "+Database.CreateDB._TABLENAME0);
            onCreate(db);
        }

    }
    public DbOpenHelper(Context context){
        this.mCtx = context;
    }
    public DbOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }
    public void create(){
        mDBHelper.onCreate(mDB);
    }

    public void close(){
        mDB.close();
    }
    public long insertColumn(String phonetoken, String Goodoc){
        ContentValues values = new ContentValues();
        values.put(Database.CreateDB.PHONE, phonetoken);
        values.put(Database.CreateDB.Goodoc, Goodoc);
        return mDB.insert(Database.CreateDB._TABLENAME0, null, values);
    }
    public void deleteColumn(String phonetoken, String Goodoc){
        mDB = mDBHelper.getWritableDatabase();
        String sql ="DELETE FROM "+Database.CreateDB._TABLENAME0+" WHERE 구독='" + Goodoc + "';" ;
        mDB.execSQL(sql);

    }
    public Cursor selectColumns(){
        return mDB.query(Database.CreateDB._TABLENAME0, null, null, null, null, null, null);
    }
}
