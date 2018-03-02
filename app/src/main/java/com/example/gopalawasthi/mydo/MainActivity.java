package com.example.gopalawasthi.mydo;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static com.example.gopalawasthi.mydo.ItemConstants.DATE;
import static com.example.gopalawasthi.mydo.ItemConstants.POSITION;
import static com.example.gopalawasthi.mydo.ItemConstants.TASK;
import static com.example.gopalawasthi.mydo.ItemConstants.TASK_COMPLETION;
import static com.example.gopalawasthi.mydo.ItemConstants.TIME;

public class MainActivity extends AppCompatActivity implements CustomAdaptor.onbuttonclicklistener {
    ListView listView;
    ArrayList<Item> arrayList;
    CustomAdaptor customAdaptor;
    ItemOpenHelper itemOpenHelper ;
    Button editutton;
    TextView headertextview;
    CheckBox checkBox ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       itemOpenHelper= ItemOpenHelper.getInstance(this);
        listView = findViewById(R.id.listview);
        arrayList=new ArrayList<>();
        arrayList= fetchdatafromDataBase();
        editutton = findViewById(R.id.edit);
        headertextview = findViewById(R.id.header);
        customAdaptor= new CustomAdaptor(arrayList,this,this);

        listView.setAdapter(customAdaptor);

        customAdaptor.notifyDataSetChanged();
    }


    private ArrayList<Item> fetchdatafromDataBase() {
        ArrayList <Item> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = itemOpenHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(Contracts.ItemDataBase.TABLE_NAME , null , null , null, null , null ,Contracts.ItemDataBase.DATES + " ASC, " + Contracts.ItemDataBase.TIMES + " ASC");
      while (cursor.moveToNext()){
          String task= cursor.getString(cursor.getColumnIndex(Contracts.ItemDataBase.TASKS));
          String date = cursor.getString(cursor.getColumnIndex(Contracts.ItemDataBase.DATES));
          String time = cursor.getString(cursor.getColumnIndex(Contracts.ItemDataBase.TIMES));
          String istaskcompletion = cursor.getString(cursor.getColumnIndex(Contracts.ItemDataBase.TIME_STAMP));
          int id= cursor.getInt(cursor.getColumnIndex(Contracts.ItemDataBase.ITEM_ID));
          long a = Long.parseLong(istaskcompletion);

          Content item =new Content(task,date,time,a,id);
          String oncomplete = getHeaderString(item);
          Header header = new Header(oncomplete);

          arrayList.add(header);
          arrayList.add(item);

      }
      return arrayList;
    }


    public String getHeaderString(Content item){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        long midnight=calendar.getTimeInMillis();
        long itemTimestamp = item.getTimestamp();
        long currentTimestamp = System.currentTimeMillis();
        Date todayDate = new Date(currentTimestamp);
        long todayTimestamp = todayDate.getTime();
        long tomorrowTimestamp =  midnight + 24*60*60*1000;
        long dayaftertomorrowTimestamp = midnight + 2*(24*60*60*1000);

        if(itemTimestamp > todayTimestamp && itemTimestamp < midnight ){
            String today = "Today";
           return today;
        }
        else if(itemTimestamp < todayTimestamp){
            return "OverDue";
        }
        else if(itemTimestamp > midnight && itemTimestamp < tomorrowTimestamp){
            return "Tomorrow";
        }
        else
            return "Later";
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if(id==R.id.addtask){
            Intent intent = new Intent(this,Setlist.class);
            startActivityForResult(intent,1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
        if(resultCode==1){

            String one = data.getStringExtra(TASK);
            String two=data.getStringExtra(DATE);
            String three=data.getStringExtra(TIME);
            long a=data.getLongExtra(TASK_COMPLETION,-1);


            SQLiteDatabase database=itemOpenHelper.getWritableDatabase();
               ContentValues contentValues=new ContentValues();
                contentValues.put(Contracts.ItemDataBase.TASKS,one);
                contentValues.put(Contracts.ItemDataBase.DATES,two);
                contentValues.put(Contracts.ItemDataBase.TIMES,three);
                contentValues.put(Contracts.ItemDataBase.TIME_STAMP,a);
                long id=database.insert(Contracts.ItemDataBase.TABLE_NAME,null,contentValues);
                int ee=(int)id;
            Content content =new Content(one, two,three,a,ee);
            String TobeSet = getHeaderString(content);
            Header header = new Header(TobeSet);
            arrayList.add(header);
            arrayList.add(content);
            customAdaptor.notifyDataSetChanged();
        }
        } else if(requestCode == 2){

            if(resultCode == 2){

             String name = data.getStringExtra(TASK);
             String date = data.getStringExtra(DATE);
             String time = data.getStringExtra(TIME);
            int pos = data.getIntExtra(POSITION,-1);
             long a = data.getLongExtra(TASK_COMPLETION,-1);

                ContentValues contentValues=new ContentValues();
                contentValues.put(Contracts.ItemDataBase.TASKS,name);
                contentValues.put(Contracts.ItemDataBase.DATES,date);
                contentValues.put(Contracts.ItemDataBase.TIMES,time);
                contentValues.put(Contracts.ItemDataBase.TIME_STAMP,a);
               // int ee = (int)a;
               Content content =  new Content(name,date,time,a);
                Content c =(Content) arrayList.get(pos);
                String [] id = {c.getId()+""};
                String tobeset = getHeaderString(content);
              //  Header header = new Header(tobeset);

//                arrayList.remove(pos);
//                arrayList.remove(pos - 1);
            //   customAdaptor.notifyDataSetChanged();

//                arrayList.add(new Header(tobeset));
//                arrayList.add(pos,content);
//                customAdaptor.notifyDataSetChanged();

              //  itemOpenHelper = ItemOpenHelper.getInstance(this) ;
                SQLiteDatabase database=itemOpenHelper.getWritableDatabase();
                 database.update(Contracts.ItemDataBase.TABLE_NAME,contentValues,Contracts.ItemDataBase.ITEM_ID + " = ? ",id);
                arrayList =  fetchdatafromDataBase();
                customAdaptor= new CustomAdaptor(arrayList,this,this);
                listView.setAdapter(customAdaptor);
                customAdaptor.notifyDataSetChanged();
             }
        }



    }

    @Override
    public void longclickdeletion( final int i ,final long j) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("are you sure to want to delete ?")
                .setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase database1 = itemOpenHelper.getWritableDatabase();
                       Content c=(Content)arrayList.get(i);
                       String [] a= {c.getId()+""};
                        database1.delete(Contracts.ItemDataBase.TABLE_NAME,Contracts.ItemDataBase.ITEM_ID + " = ? ", a );
                        arrayList.remove(i);
                        arrayList.remove(i-1);
                        customAdaptor.notifyDataSetChanged();
                    }

                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                customAdaptor.notifyDataSetChanged();
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.setTitle("alert for delete");
        dialog.show();

    }

    @Override
    public void oneditbuttonclick(int i) {
        Intent intent =new Intent(this,Setlist.class);
        Content c = (Content) arrayList.get(i);
        intent.putExtra(ItemConstants.TASK,c.getName());
        intent.putExtra(ItemConstants.DATE,c.getDate());
        intent.putExtra(ItemConstants.TIME,c.getTime());
        intent.putExtra(ItemConstants.POSITION,i);
        startActivityForResult(intent,2);
        overridePendingTransition(R.anim.animator1,R.anim.animator2);

    }

    @Override
    public void oncheckboxcheckdelete(final int i) {
       // Content c= (Content) arrayList.get(i);
         checkBox = findViewById(R.id.check);
        if(checkBox.isChecked()){
            arrayList.remove(i);
            arrayList.remove(i-1);
            customAdaptor.notifyDataSetChanged();
            checkBox.setChecked(false);
        }
    }


}
