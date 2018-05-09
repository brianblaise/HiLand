package com.example.brianb.demo1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.brianb.demo1.DBhelper.COLUMN_ID;
import static com.example.brianb.demo1.DBhelper.COLUMN_TITLE;

/**
 * Created by BrianB on 24-Jun-17.
 */

public class DAOdb {
    private SQLiteDatabase database;
    private DBhelper dbHelper;

    public DAOdb(Context context) {
        dbHelper = new DBhelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addImage(MyImage image) {
        ContentValues cv = new ContentValues();
        cv.put(DBhelper.COLUMN_PATH, image.getPath());
        cv.put(COLUMN_TITLE, image.getTitle());
        cv.put(DBhelper.COLUMN_PRICE, image.getPrice());
        cv.put(DBhelper.COLUMN_DESCRIPTION, image.getDescription());
        return database.insert(DBhelper.TABLE_NAME, null, cv);
    }

    public void deleteImage(MyImage image) {
        String whereClause = COLUMN_TITLE + "=? AND " + DBhelper.COLUMN_ID + "=?";
        String[] whereArgs = new String[]{image.getTitle(), String.valueOf(image.getpId())};
        database.delete(DBhelper.TABLE_NAME, whereClause, whereArgs);
    }

    public void deleteSelected(String id) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete("image", "id = " + id, null);
        database.close();
    }
    /**
     * @return all image as a List
     */
    public List<MyImage> getImages() {
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
    }
    public List<MyImage> getAllListings(){
        List<MyImage>lstListings = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+DBhelper.TABLE_NAME;
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                MyImage dispList = new MyImage();
                dispList.setpId(cursor.getLong(0));
                dispList.setPath(cursor.getString(1));
                dispList.setTitle(cursor.getString(2));
                dispList.setPrice(cursor.getLong(3));
                dispList.setDescription(cursor.getString(4));

                lstListings.add(dispList);
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        return lstListings;
    }

    /**
     * read the cursor row and convert the row to a MyImage object
     *
     * @param cursor
     * @return MyImage object
     */
    private MyImage cursorToMyImage(Cursor cursor) {
        MyImage image = new MyImage();
        image.setpId(cursor.getInt(cursor.getColumnIndex(DBhelper.COLUMN_ID)));
        image.setPath(cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_PATH)));
        image.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
        image.setPrice(cursor.getInt(cursor.getColumnIndex(DBhelper.COLUMN_PRICE)));
        image.setDescription(cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_PRICE)));
        return image;
    }
}
