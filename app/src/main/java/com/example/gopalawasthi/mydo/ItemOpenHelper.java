package com.example.gopalawasthi.mydo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.zip.CheckedOutputStream;

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
// for the simple list of the todos
  String sqluse = "CREATE TABLE " + Contracts.ItemDataBase.TABLE_NAME + " ( " +
                    Contracts.ItemDataBase.ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Contracts.ItemDataBase.DATES + " TEXT, " +
                    Contracts.ItemDataBase.TIMES + " TEXT, " +
                    Contracts.ItemDataBase.TIME_STAMP + " TEXT, " +
                    Contracts.ItemDataBase.TASKS + " TEXT, "+ Contracts.ItemDataBase.TAG + "TEXT )";

            sqLiteDatabase.execSQL(sqluse);

// for the tags to that would be given different as per requirement of the user
            String addtagstolist = "CREATE TABLE " + Contracts.TagsdataBase.TABLE_NAME + " ( " +
                                    Contracts.TagsdataBase.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                    Contracts.TagsdataBase.TAGS + " TEXT )";

            sqLiteDatabase.execSQL(addtagstolist);

//     for sorting the items using the tags
//      String tagswithitem = "CREATE TABLE " + Contracts.MergeItemTags.TABLE_NAME + " (" +
//                            Contracts.MergeItemTags.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                            Contracts.MergeItemTags.FINALITEM_ID + " TEXT " +
//                            Contracts.MergeItemTags.TAGS_ID + " TEXT )";
//
//      sqLiteDatabase.execSQL(tagswithitem);
     }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
