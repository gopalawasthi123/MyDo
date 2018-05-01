package com.example.gopalawasthi.mydo;

/**
 * Created by Gopal Awasthi on 20-02-2018.
 */

public class Contracts {

    public static final String DATABASE_NAME = "data";
    public static final int ITEM_VERSION = 1;

    static class ItemDataBase {
        public static final String TABLE_NAME = "mytable";
        public static final String TASKS = "datatasks";
        public static final String TIME_STAMP = "timeinepoch";
        public static final  String TIMES = "timetasks";
        public static final String ITEM_ID= "id_of_item";
        public static final  String DATES = "datetasks";
        public static final String TAG = "tag";
    }
    static class TagsdataBase {

        public static final String TABLE_NAME = "tags";
        public static final String ID = "tagsid";
        public static final String TAGS = "listoftags";
        public static  final String ITEM_ID = "item";
    }
//    static class MergeItemTags {
//        public  static  final String TABLE_NAME = "newtask";
//        public  static  final  String ID = "mergeid";
//        public static  final String FINALITEM_ID ="itemid";
//        public static  final String TAGS_ID = "tagsid";
//
//    }

    }


