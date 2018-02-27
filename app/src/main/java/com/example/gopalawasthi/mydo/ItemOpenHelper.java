package com.example.gopalawasthi.mydo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gopal Awasthi on 20-02-2018.
 */

public class ItemOpenHelper extends SQLiteOpenHelper {
    private static ItemOpenHelper itemOpenHelper;

    private ItemOpenHelper(Context context) {
        super(context, Contracts.DATABASE_NAME, null, Contracts.ITEM_VERSION);
    }

    public static ItemOpenHelper getInstance( Context context){
        if(itemOpenHelper==null){
            itemOpenHelper =new ItemOpenHelper(context.getApplicationContext());
        }
        return itemOpenHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

  String sqluse = "CREATE TABLE " + Contracts.ItemDataBase.TABLE_NAME + " ( " +
                    Contracts.ItemDataBase.ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Contracts.ItemDataBase.DATES + " TEXT, " +
                    Contracts.ItemDataBase.TIMES + " TEXT, " +
                    Contracts.ItemDataBase.TIME_STAMP + " TEXT, " +
                    Contracts.ItemDataBase.TASKS + " TEXT )";

            sqLiteDatabase.execSQL(sqluse);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
