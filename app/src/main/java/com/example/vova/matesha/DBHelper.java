package com.example.vova.matesha;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE1_NAME = "chapters";
    public static final String TABLE2_NAME = "tssks";
    public static final String CHAPTER = "chapter";
    public static final String THEORY = "theory";
    public static final String TASK = "task";
    public static final String ANSWER = "answer";
    public static final String IMG = "img";
    public static final String SUBJECT = "subject";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "test.db";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "
                + TABLE1_NAME
                    + "("
                        + "_id integer primary key autoincrement ,"
                        + CHAPTER
                        + THEORY
                        + IMG
                        + SUBJECT
                    + ")"
        );

        db.execSQL("create table "
                    + TABLE2_NAME     + "("
                         + "_id integer primary key autoincrement ,"
                         + TASK
                         + ANSWER
                         + CHAPTER
                         + SUBJECT
                + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
