package com.example.brianb.demo1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BrianB on 12-Apr-17.
 */
public class DbHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 9;
    public static final String DATABASE_NAME = "account_details.db";
    public static final String TABLE_NAME = "account_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASS = "pass";
    /* public static final String COLUMN_ACCNTYPE = "accntype"; */  /* +COLUMN_ACCNTYPE+ "not null,"*/

    SQLiteDatabase db;

   /* public static final String TABLE_CREATE = "create table account(id integer primary key not null auto_increment , "
            + "username varchar(32) not null, email varchar(20) not null, accountType varchar(20) not null, password varchar(20) not null);";*/

    public DbHandler(Context context)
    {
        super(context, DATABASE_NAME ,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLE_CREATE = "CREATE TABLE " +TABLE_NAME+ "(" +COLUMN_ID+ " integer primary key not null, "
                +COLUMN_NAME+ " text not null, " +COLUMN_EMAIL+ " text, " +COLUMN_PASS+ " text"+");";
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    public void insertInfo(Person p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "select * from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COLUMN_ID, count);
        values.put(COLUMN_NAME, p.getName());
        values.put(COLUMN_EMAIL, p.getEmail());
        values.put(COLUMN_PASS, p.getPass());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        this.onCreate(db);
    }

    public String searchPass(String uname) {
        db = this.getReadableDatabase();
        String query = "SELECT " +COLUMN_EMAIL +", " +COLUMN_PASS +" FROM " +TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;
        b = "not found";
        if(cursor.moveToFirst()){
            do {
                a = cursor.getString(0);

                if(a.equals(uname)){
                    b = cursor.getString(1);
                    break;
                }
            }while(cursor.moveToNext());
        }
            return b;
    }

    /*public void Register(String username, String email, String accountType, String password){
        String query = "INSERT INTO register values(?, ?, ?, ?)";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query, new String[]{username, email, accountType, password});
        db.close();
    }*/

}
