package com.jazz.musuemguide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mark Steger on 8/2/2016.
 */
public class DBController extends SQLiteOpenHelper {
    private static final String LOGCAT = null;

    public DBController(Context applicationcontext) {
        super(applicationcontext, "assets.db", null, 1);  // creating DATABASE
        Log.d(LOGCAT, "Created");
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "CREATE TABLE IF NOT EXISTS tblAssets ( Id INTEGER PRIMARY KEY, Barcode TEXT, Title TEXT, Description TEXT, Artist TEXT, ResourceName TEXT)";
        database.execSQL(query);
        ContentValues contentValues = new ContentValues();

        contentValues.put("Barcode", "1002");
        contentValues.put("Title", "Starry Night");
        contentValues.put("Description", "The Mona Lisa is a half-length portrait of a woman by the Italian artist Leonardo da Vinci, which has been acclaimed as \"the best known, the most visited, the most written about, the most sung about, the most parodied work of art in the world\". Wikipedia");
        contentValues.put("Author", "Leonardo da Vinci");
        contentValues.put("ResourceName", "1002.jpg");
        database.insert("tblAssets", null, contentValues);
        database.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old,
                          int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS tblAssets";
        database.execSQL(query);
        onCreate(database);
    }

    public ArrayList<HashMap<String, String>> getAllAssets() {
        ArrayList<HashMap<String, String>> AssetList;
        AssetList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM tblAssets";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("Id", cursor.getString(0));
                map.put("Barcode", cursor.getString(1));
                map.put("Title", cursor.getString(2));
                map.put("Description", cursor.getString(3));
                map.put("Artist", cursor.getString(4));
                map.put("ResourceName", cursor.getString(5));
                AssetList.add(map);
            } while (cursor.moveToNext());
            cursor.close();
        }
        database.close();
        return AssetList;
    }
    public Painting getAsset(String Barcode) {
        String selectQuery = "SELECT Description, Artist, Title, ResourceName FROM tblAssets WHERE Barcode = '" + Barcode + "'";
        //String result = "";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        Painting result = null;
        if (cursor.moveToFirst()) {
            do {
                result = new Painting(cursor.getString(cursor.getColumnIndex("Title")),
                                cursor.getString(cursor.getColumnIndex("Description")),
                                cursor.getString(cursor.getColumnIndex("ResourceName")),
                                cursor.getString(cursor.getColumnIndex("Author")));
                //result = (cursor.getString(0));
            } while (cursor.moveToNext());
            cursor.close();
        }
        database.close();
        return result;
    }
}