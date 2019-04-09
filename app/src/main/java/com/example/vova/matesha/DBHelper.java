package com.example.vova.matesha;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "Math.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Cursor getCursor(String query,String[] selctionArgs){
        Cursor cursor;

        SQLiteDatabase db = getReadableDatabase();

        cursor = db.rawQuery(query,selctionArgs);

        return cursor;
    }
}
