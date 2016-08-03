package com.jazz.musuemguide;

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
        query = "CREATE TABLE IF NOT EXISTS tblAssets ( Id INTEGER PRIMARY KEY, Barcode TEXT, Description TEXT, Artist TEXT, ResourceName TEXT)";
        database.execSQL(query);
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
                map.put("Description", cursor.getString(2));
                map.put("Artist", cursor.getString(3));
                map.put("ResourceName", cursor.getString(4));
                AssetList.add(map);
            } while (cursor.moveToNext());
        }
        return AssetList;
    }
    public String getAsset(String Barcode) {
        String selectQuery = "SELECT Description, Artist, ResourceName FROM tblAssets WHERE Barcode = '" + Barcode + "'";
        String result = "";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                result = (cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return result;
    }
}