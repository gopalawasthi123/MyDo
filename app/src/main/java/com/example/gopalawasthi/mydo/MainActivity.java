package com.example.gopalawasthi.mydo;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
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
    ImageButton editutton;
    ArrayList <Item> arrayList1;
    TextView headertextview;
    boolean todaycheck,tomorrowcheck,overduecheck,latercheck;

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
        arrayList1 = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = itemOpenHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(Contracts.ItemDataBase.TABLE_NAME , null , null , null, null , null ,Contracts.ItemDataBase.DATES + " ASC, " + Contracts.ItemDataBase.TIMES + " ASC");
      while (cursor.moveToNext()){
          String task= cursor.getString(cursor.getColumnIndex(Contracts.ItemDataBase.TASKS));
          String date = cursor.getString(cursor.getColumnIndex(Contracts.ItemDataBase.DATES));
          String time = cursor.getString(cursor.getColumnIndex(Contracts.ItemDataBase.TIMES));
          String istaskcompletion = cursor.getString(cursor.getColumnIndex(Contracts.ItemDataBase.TIME_STAMP));
          String tag = cursor.getString(cursor.getColumnIndex(Contracts.ItemDataBase.TAG));
          int id= cursor.getInt(cursor.getColumnIndex(Contracts.ItemDataBase.ITEM_ID));
          long a = Long.parseLong(istaskcompletion);

          Content item =new Content(task,date,time,a,id,tag);
          String get = getHeaderString(item);
        //  Header header = new Header(oncomplete);
         // arrayList.add(header);
         // arrayList.add(item);

      }
      return arrayList1;
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
            String Today = "Today";
           if(!todaycheck ) {
               arrayList1.add(new Header(Today));
               todaycheck = true;
           }
            arrayList1.add(item);
            return Today;
        }
        else if(itemTimestamp < todayTimestamp){
            String Overdue = "OverDue";
            if(!overduecheck)
            {
              arrayList1.add(new Header(Overdue));
              overduecheck = true;
            }
            arrayList1.add(item);
            return Overdue;
        }
        else if(itemTimestamp > midnight && itemTimestamp < tomorrowTimestamp){
             String Tomorrow = "Tomorrow";
            if(!tomorrowcheck){
                arrayList1.add(new Header(Tomorrow));
                tomorrowcheck = true;
            }
            arrayList1.add(item);
            return Tomorrow;
        }
        else {
                String Later ="Later";
                if(!latercheck){
                   arrayList1.add(new Header(Later));
                    latercheck = true;
                }
                arrayList1.add(item);
            return "Later";

        }
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

          //  Content content =new Content(one, two,three,a,ee);
                 broadcastAlarmNotification(a,ee);
          //  String TobeSet = getHeaderString(content);
            //Header header = new Header(TobeSet);
          //  arrayList.add(header);
           // arrayList.add(content);
            todaycheck =false;
            tomorrowcheck =false;
            overduecheck =false;
            latercheck =false;

            arrayList =fetchdatafromDataBase();
            customAdaptor = new CustomAdaptor(arrayList,this,this);
            listView.setAdapter(customAdaptor);

            customAdaptor.notifyDataSetChanged();
        }
        } else if(requestCode == 2){

            if(resultCode == 2){

             String name = data.getStringExtra(TASK);
             String date = data.getStringExtra(DATE);
             String time = data.getStringExtra(TIME);
             String tag =  data.getStringExtra(Setlist.TAGS_STRING);
             int pos = data.getIntExtra(POSITION,-1);
             long a = data.getLongExtra(TASK_COMPLETION,-1);

                ContentValues contentValues=new ContentValues();
                contentValues.put(Contracts.ItemDataBase.TASKS,name);
                contentValues.put(Contracts.ItemDataBase.DATES,date);
                contentValues.put(Contracts.ItemDataBase.TIMES,time);
                contentValues.put(Contracts.ItemDataBase.TIME_STAMP,a);
                contentValues.put(Contracts.ItemDataBase.TAG,tag);
               // int ee = (int)a;
               Content content =  new Content(name,date,time,a);
                Content c =(Content) arrayList.get(pos);
                String [] id = {c.getId()+""};
             //   String tobeset = getHeaderString(content);
              //  Header header = new Header(tobeset);
                  int ee = c.getId();
//                arrayList.remove(pos);
//                arrayList.remove(pos - 1);
            //   customAdaptor.notifyDataSetChanged();

//                arrayList.add(new Header(tobeset));
//                arrayList.add(pos,content);
//                customAdaptor.notifyDataSetChanged();

              //  itemOpenHelper = ItemOpenHelper.getInstance(this) ;
                SQLiteDatabase database=itemOpenHelper.getWritableDatabase();
                 database.update(Contracts.ItemDataBase.TABLE_NAME,contentValues,Contracts.ItemDataBase.ITEM_ID + " = ? ",id);
                todaycheck =false;
                tomorrowcheck =false;
                overduecheck =false;
                latercheck =false;
                broadcastAlarmNotification(a,ee);
                arrayList =  fetchdatafromDataBase();
                customAdaptor= new CustomAdaptor(arrayList,this,this);
                listView.setAdapter(customAdaptor);
                customAdaptor.notifyDataSetChanged();
             }
        }


    }
    // set the alarm using broadcasting
    private void broadcastAlarmNotification(long epoch,int id) {

        AlarmManager alarmManager =(AlarmManager) getSystemService(ALARM_SERVICE);
        Intent shareintent = new Intent(this,MyReceiver.class);
        SQLiteDatabase database = itemOpenHelper.getReadableDatabase();
        long currenttime = System.currentTimeMillis();
        long check = epoch - currenttime;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,id,shareintent,PendingIntent.FLAG_UPDATE_CURRENT);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            if(check >= 0 )
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,epoch+1,pendingIntent);
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
                        todaycheck =false;
                        tomorrowcheck =false;
                        overduecheck =false;
                        latercheck =false;
                        cancelAlarmOnDelete(c.getId());
                        arrayList = fetchdatafromDataBase();

                        customAdaptor = new CustomAdaptor(arrayList,MainActivity.this,MainActivity.this);
                        listView.setAdapter(customAdaptor);
                        customAdaptor.notifyDataSetChanged();
                    }

                }).setNegativeButton("no", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                customAdaptor.today= false;
//                customAdaptor.overdue= false;
//                customAdaptor.tomorrow =false;
//                customAdaptor.later =false;

                customAdaptor.notifyDataSetChanged();
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.setTitle("alert for delete");
        dialog.show();

    }

    private void cancelAlarmOnDelete(int id) {
    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    Intent  intent = new Intent(this,MyReceiver.class);
    PendingIntent pendingIntent =  PendingIntent.getBroadcast(MainActivity.this,id,intent,PendingIntent.FLAG_UPDATE_CURRENT);
   // pendingIntent.cancel();
    alarmManager.cancel(pendingIntent);

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
//
//    @Override
//    public void oncheckboxcheckdelete( int i) {
//
//        Content c= (Content) arrayList.get(i);
//
//             arrayList.remove(i);
//             arrayList.remove(i-1);
//             customAdaptor.notifyDataSetChanged();
//   }

}



