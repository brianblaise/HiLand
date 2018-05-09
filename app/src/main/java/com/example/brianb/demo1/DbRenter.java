package com.example.brianb.demo1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import static com.example.brianb.demo1.DatabaseHelper.KEY_ADDRESS;

/**
 * Created by BrianB on 07-Jun-17.
 */
public class DbRenter extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION =2;
    public static final String DATABASE_NAME = "tenantDB";
    public static final String TABLE_NAME = "tenantTB";
    public static final String HOUSE_NUMBER = "houseNo";
    public static final String KEY_NAME = "tenantName";
    public static final String KEY_ID = "renterId";
    public static final String KEY_PHONE = "renterPhone";
    public static final String KEY_DATE = "joinDate";
    public static final String KEY_ADDRESS = "tenantAddress";
    public static final String KEY_EMAIL = "tenantEmail";
    public static final String KEY_IMAGE = "tenantImage";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    public static final SimpleDateFormat database_format = new SimpleDateFormat("yyyy/MM/dd");


    public DbRenter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String TABLE_CREATE = "CREATE TABLE "+TABLE_NAME+ "("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +HOUSE_NUMBER+ " INTEGER NOT NULL, "
                +KEY_NAME+" TEXT NOT NULL, "
                +KEY_PHONE+" TEXT NOT NULL, "
                +KEY_DATE+" LONG, "
                +KEY_ADDRESS+" TEXT NOT NULL, "
                +KEY_EMAIL+ " TEXT NOT NULL, "
                +KEY_IMAGE+" TEXT "+");";
        sqLiteDatabase.execSQL(TABLE_CREATE);
        ContentValues cv = new ContentValues();
        //cv.put(KEY_NAME, "New Entry");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy‐MM‐dd");  //HH:mm:ss
        String date = dateFormat.format(new Date());
        cv.put(KEY_DATE, new Date().getTime());
        cv.put(KEY_DATE, date);
        sqLiteDatabase.insert(TABLE_NAME, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public void addRenter(Renter renter){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "select * from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        values.put(KEY_ID, count);
        values.put(HOUSE_NUMBER, renter.getrHouseNumber());
        values.put(KEY_NAME, renter.getrName());
        values.put(KEY_PHONE, renter.getrPhone());
        values.put(KEY_DATE, renter.getrDate());
        values.put(KEY_ADDRESS, renter.getrAddress());
        values.put(KEY_EMAIL, renter.getrEmail());
        values.put(KEY_IMAGE, renter.getrPath());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public int updateRenter(Renter renter){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "select * from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        values.put(KEY_ID, count);
        values.put(HOUSE_NUMBER, renter.getrHouseNumber());
        values.put(KEY_NAME, renter.getrName());
        values.put(KEY_PHONE, renter.getrPhone());
        values.put(KEY_DATE, renter.getrDate());
        values.put(KEY_ADDRESS, renter.getrAddress());
        values.put(KEY_EMAIL, renter.getrEmail());
        values.put(KEY_IMAGE, renter.getrPath());
        return db.update(TABLE_NAME,values, KEY_ID+ " =?", new String[]{String.valueOf(renter.getrId())});
    }
    public void deleteRenter(Renter renter){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "select * from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        values.put(KEY_ID, count);
        values.put(HOUSE_NUMBER, renter.getrHouseNumber());
        values.put(KEY_NAME, renter.getrName());
        values.put(KEY_PHONE, renter.getrPhone());
        values.put(KEY_DATE, renter.getrDate());
        values.put(KEY_ADDRESS, renter.getrAddress());
        values.put(KEY_EMAIL, renter.getrEmail());
        values.put(KEY_IMAGE, renter.getrPath());

        db.delete(TABLE_NAME, KEY_ID+" = ?", new String[]{String.valueOf(renter.getrId())});
        db.close();
    }
    public Renter getRenter(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, HOUSE_NUMBER, KEY_NAME, KEY_PHONE, KEY_DATE,
                        KEY_ADDRESS, KEY_EMAIL, KEY_IMAGE},
                KEY_ID+ " =?",
                new String[]{String.valueOf(id)},null, null,KEY_ID, null);
        if(cursor != null){
            cursor.moveToFirst();
        }

        return new Renter( cursor.getInt(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));

    }
    //Performs same task as getRenter(int id)
    public Renter getRenterItem(int id) {

        Renter item = null;

        Cursor cursor = mDb.query(TABLE_NAME,
                new String[]{KEY_ID, HOUSE_NUMBER, KEY_NAME, KEY_PHONE, KEY_DATE,
                        KEY_ADDRESS, KEY_EMAIL, KEY_IMAGE}, KEY_ID + " = " + id, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            item = cursorToRenter(cursor);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return item;
    }
    private Renter cursorToRenter(Cursor cursor) {
        Renter renter = new Renter();

        renter.setrId(cursor.getInt(0));
        renter.setrHouseNumber(cursor.getInt(1));
        renter.setrName(cursor.getString(2));
        renter.setrPhone(cursor.getString(3));
        renter.setrDate(cursor.getString(4));
        renter.setrAddress(cursor.getString(5));
        renter.setrEmail(cursor.getString(6));
        renter.setrPath(cursor.getString(7));

        return renter;
    }

    public List<Renter> getAllRenters(){
        List<Renter>listRenters = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+TABLE_NAME+ " ORDER BY "+KEY_ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Renter renter = new Renter();

                renter.setrId(cursor.getInt(0));
                renter.setrHouseNumber(cursor.getInt(1));
                renter.setrName(cursor.getString(2));
                renter.setrPhone(cursor.getString(3));
                renter.setrDate(cursor.getString(4));
                renter.setrAddress(cursor.getString(5));
                renter.setrEmail(cursor.getString(6));
                renter.setrPath(cursor.getString(7));
                listRenters.add(renter);
            }
            while(cursor.moveToNext());
            cursor.close();
        }
        return listRenters;
    }
   /* public List<Renter> getRenters() {
        List<MyImage> MyImages = new ArrayList<>();
        Cursor cursor = mDb.query(DBhelper.TABLE_NAME, null, null, null, null,
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
   //Closing and opening the database
    public DbRenter open() throws SQLException {
        mDb = this.getWritableDatabase();
        return this;
    }
    public void close() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.close();
    }
    // Insert the image to the Sqlite DB
   /* public void insertImage(byte[] imageBytes) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_IMAGE, imageBytes);
        mDb.insert(TABLE_NAME, null, cv);
    } */
    // Get the image from SQLite DB
    // We will just get the last image we just saved for convenience...
  /*  public byte[] retreiveImageFromDB() {
        Cursor cur = mDb.query(true, TABLE_NAME, new String[]{KEY_IMAGE,},
                null, null, null, null, HOUSE_NUMBER + " ASC", "1");
        if (cur.moveToFirst()) {
            do{
                byte[] blob = cur.getBlob(cur.getColumnIndex(KEY_IMAGE));
                cur.close();
                return blob;
            }while(cur.moveToNext());
        }

        cur.close();
        return null;
    } */
}
