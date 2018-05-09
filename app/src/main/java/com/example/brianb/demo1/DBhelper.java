package com.example.brianb.demo1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BrianB on 24-Jun-17.
 */

public class DBhelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "sqliteimage.db";
    public static final int DB_VERSION = 4;

    public static final String COMMA_SEP = ",";
    public static final String TEXT_TYPE = " TEXT";
    public static final String NUMERIC_TYPE = " NUMERIC";
    public static final String TABLE_NAME = "image";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PATH = "path";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DESCRIPTION = "description";


    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            +COLUMN_ID+ " INTEGER  PRIMARY KEY  AUTOINCREMENT, "+
            COLUMN_PATH + TEXT_TYPE + COMMA_SEP +
            COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
            COLUMN_PRICE + NUMERIC_TYPE  + COMMA_SEP +
            COLUMN_DESCRIPTION + TEXT_TYPE +
            ")";

    public DBhelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
        onCreate(db);
    }

}
