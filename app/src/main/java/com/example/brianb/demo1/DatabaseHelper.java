package com.example.brianb.demo1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BrianB on 01-May-17.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "propertiesDB";
    public static final String TABLE_NAME = "propertiesTB";
    public static final String KEY_ID = "id";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PROPERTY = "propertyType";
    public static final String KEY_UNITS = "units";
    public static final String KEY_PATH = "imagePath";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"("
                +KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +KEY_PROPERTY+ " TEXT NOT NULL, "
                +KEY_ADDRESS+" TEXT NOT NULL, "
                +KEY_UNITS+" INTEGER NOT NULL, "
                +KEY_PATH+ " TEXT NOT NULL "+");";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

//CRUD PROPERTIES
    public void addProperty(Properties property){
    SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "select * from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(KEY_ID, count);
        values.put(KEY_PROPERTY, property.getProperty());
        values.put(KEY_ADDRESS, property.getAddress());
        values.put(KEY_UNITS, property.getUnits());
        values.put(KEY_PATH, property.getpPath());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public int updateProperty(Properties property){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "select * from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        values.put(KEY_ID,count);
        values.put(KEY_PROPERTY, property.getProperty());
        values.put(KEY_ADDRESS, property.getAddress());
        values.put(KEY_UNITS, property.getUnits());
        values.put(KEY_PATH, property.getpPath());

        return db.update(TABLE_NAME,values, KEY_ID+ " =?", new String[]{String.valueOf(property.getId())});
    }
    public void deleteProperty(Properties property){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "select * from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        values.put(KEY_ID, count);
        values.put(KEY_PROPERTY, property.getProperty());
        values.put(KEY_ADDRESS, property.getAddress());
        values.put(KEY_UNITS, property.getUnits());
        values.put(KEY_PATH, property.getpPath());

        db.delete(TABLE_NAME, KEY_ID+" = ?", new String[]{String.valueOf(property.getId())});
        db.close();
    }
    public Properties getProperty(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID,KEY_PROPERTY,KEY_ADDRESS,KEY_UNITS}, KEY_ID+ " =?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return new Properties(cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4));
    }
    public Properties justAddress(String address){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ADDRESS}, KEY_ADDRESS+ " =?",
                new String[]{String.valueOf(address)}, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return new Properties(cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4));
    }

    public List<Properties> getAllProperties(){
        List<Properties>lstProperties = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Properties property = new Properties();
                property.setId(cursor.getInt(0));
                property.setProperty(cursor.getString(1));
                property.setAddress(cursor.getString(2));
                property.setUnits(cursor.getString(3));
                property.setpPath(cursor.getString(4));

                lstProperties.add(property);
            }
            while(cursor.moveToNext());
        }
        return lstProperties;
    }
 /*   public List<Properties> getProperties() {
        List<MyImage> MyImages = new ArrayList<>();
        Cursor cursor = database.query(DBhelper.TABLE_NAME, null, null, null, null,
                null, DBhelper.COLUMN_ID + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MyImage MyImage = cursorToMyImage(cursor);
            MyImages.add(MyImage);
            cursor.moveToNext();
        }
        cursor.close();
        return MyImages;
    } */
    public String searchAddress(String address) {
      SQLiteDatabase  db = this.getReadableDatabase();
        String query = "SELECT " +KEY_ADDRESS +" FROM " +TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;
        b = "not found";
        if(cursor.moveToFirst()){
            do {
                a = cursor.getString(0);

                if(a.equals(address)){
                    b = cursor.getString(0);
                    break;
                }
            }while(cursor.moveToNext());
        }
        return b;
    }
}
