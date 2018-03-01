package com.example.gopalawasthi.mydo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.LinkAddress;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class Setlist extends AppCompatActivity {
    EditText editText;
    boolean add= true;
    EditText showtime;
    EditText showdate;
    int position;
    TextView header;
    Button button;
    Spinner settags;
    ImageButton imageButton;
    String settime;
    String setdate;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
   ArrayAdapter<String> arrayAdapter;
    ArrayList<String> ar = new ArrayList<>();
    String home = "Personal";
    String Work = "Work";
    String shopping = "Shopping";
    int onedithour , oneditminute, onedityear, oneditmonth ,oneditday;


//   public static long epochdate;

  Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setlist);
     arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,ar);
        editText=findViewById(R.id.edittext);

        calendar = Calendar.getInstance();
        button=findViewById(R.id.savedata);
        imageButton = findViewById(R.id.imagebutton);
        header=findViewById(R.id.header);
        showdate=findViewById(R.id.showdate);
        showtime=findViewById(R.id.showtime);
        settags = findViewById(R.id.customtask);

        ar.add(home);
        ar.add(Work);
        ar.add(shopping);

        settags.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        Intent intent = getIntent();
        if(intent.hasExtra(ItemConstants.TASK)){
            position =intent.getIntExtra(ItemConstants.POSITION,-1);
         String name = intent.getStringExtra(ItemConstants.TASK);
         String date1 = intent.getStringExtra(ItemConstants.DATE);
         String time1 = intent.getStringExtra(ItemConstants.TIME);
         editText.setText(name);
         showdate.setText(date1);
         showtime.setText(time1);
         add = false;
        }


                showtime.setOnClickListener(new EditText.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        Date date = new Date(System.currentTimeMillis());
                        date.toString();
                        long epoch = date.getTime();

                        // final Calendar calendar=Calendar.getInstance();
                        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        final int minute = calendar.get(Calendar.MINUTE);

                          timePickerDialog = new TimePickerDialog(Setlist.this, new TimePickerDialog.OnTimeSetListener() {


                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                               String one = i + "";
                               String two = i1 + "";
                               if(one.length()==1){
                                   one = "0" + one ;
                               }
                                if(two.length()==1){
                                   two = "0" + two;
                                }
                                settime = (one + ":" + two + "");
                                showtime.setText(settime);
//                                onedithour = i;
//                                oneditminute = i1;

                                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), i, i1);
                            }


                        }, hour, minute, true);
                        timePickerDialog.setTitle("Set alarm time");
                        timePickerDialog.show();
                    }
                });




        showdate.setOnClickListener(new EditText.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // final Calendar calendar1=Calendar.getInstance();
                    int yea = calendar.get(Calendar.YEAR);
                    int mon = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                     datePickerDialog = new DatePickerDialog(Setlist.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            // i1=i1+1;
                            int monthpro = i1 + 1;
                            String one = i2 + "";
                            String two = monthpro + "";
                            String three = i + "";
                            if(one.length() == 1){
                                one = "0" + one;
                            }
                            if(two.length() == 1){
                                two = "0" +two;
                            }
                            if(three.length() == 1){
                                three = "0" + three;
                            }
                            setdate = (one + "-" + two + "-" + three);
//                            onedityear = i;
//                            oneditmonth = monthpro;
//                            oneditday = i2;
                            showdate.setText(setdate);
                            calendar.set(i, i1, i2, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));

                        }
                    }, yea, mon, day);
                    datePickerDialog.setTitle("set alarm date");
                    datePickerDialog.show();
                }
            });

//        String maindate=setdate+":"+settime;
//
//        try {
//           a = new SimpleDateFormat("dd-MM-yyyy:HH:mm").parse(maindate).getTime();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }


    }

     // this is image button by clicking at it dialog box appears
    public void onImagebutton (View view){
        final AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("Enter Task");
        alertdialog.setMessage("Wanna enter a custom Task?");
        alertdialog.setIcon(R.drawable.contract);
        final EditText editText1 = new EditText(Setlist.this);

//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        editText1.setLayoutParams(layoutParams);



        alertdialog.setPositiveButton("save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            String a = editText1.getText().toString();


                //    Toast.makeText(Setlist.this,"invalid ",Toast.LENGTH_SHORT).show();
            }
        });

        alertdialog.setView(editText1);
        final AlertDialog dialog = alertdialog.create();
        dialog.show();
       dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            Boolean a = editText1.getText().toString().trim().isEmpty();
            if(!a){
                ar.add(editText1.getText().toString());
                dialog.dismiss();
            }else
                Toast.makeText(Setlist.this,"invalid! input can't be empty",Toast.LENGTH_SHORT).show();

           }
       });


    }
//    String a=  showdate.getText() + "";
//    String b= showtime.getText() + "";
//    String c= a+b;  // 12-12-1332 12:12
//    SimpleDateFormat simpleDateFormat =new SimpleDateFormat("DD mm yyyy HH:mm zzz ");
//    Date date =simpleDateFormat.parse(c,null);
//    long epoch1=date.getTime();

    // save button take you back to the main activity
    public void savebutton(View view) {


        Calendar calendar1 = Calendar.getInstance();
        String a = showdate.getText().toString();
        String b= showtime.getText().toString();
        calendar1.set(Calendar.YEAR,Integer.parseInt(a.substring(6,10)));
        calendar1.set(Calendar.DAY_OF_MONTH,Integer.parseInt(a.substring(0,2)));
        calendar1.set(Calendar.MONTH,Integer.parseInt(a.substring(3,5))-1);
        calendar1.set(Calendar.HOUR_OF_DAY,Integer.parseInt(b.substring(0,2)));
        calendar1.set(Calendar.MINUTE,Integer.parseInt(b.substring(3,5)));
        calendar1.set(Calendar.SECOND,0);
        calendar1.set(Calendar.MILLISECOND,0);

        Log.d("EPOCHDAY",a.substring(0,2));
        Log.d("EPOCHMONTH",a.substring(3,5));
        Log.d("EPOCHYEAR",a.substring(6,10));
        Log.d("EPOCHHOUR",a.substring(0,2));
        Log.d("EPOCHMINUTE",a.substring(3,5));



        long epoch = calendar1.getTimeInMillis();

        Log.d("tagger",epoch+"");
//            else{
//                epoch =epoch1;
//            }

        Intent intent=new Intent();

        String taskedit=editText.getText().toString();
        String dateedit=showdate.getText().toString();
        String timeedit=showtime.getText().toString();

        if(isemptyornull(taskedit)){
            Toast.makeText(this,"task can't be empty",Toast.LENGTH_SHORT).show();
            return;
    }
        if(isemptyornull(dateedit)){
            Toast.makeText(this,"date can't be empty",Toast.LENGTH_SHORT).show();
            return;
        }
        if(isemptyornull(timeedit)){
            Toast.makeText(this,"time can't be empty",Toast.LENGTH_SHORT).show();
            return;
        }
        intent.putExtra(ItemConstants.TASK,editText.getText().toString());
        intent.putExtra(ItemConstants.TIME,showtime.getText().toString());
        intent.putExtra(ItemConstants.DATE,showdate.getText().toString());
        intent.putExtra(ItemConstants.TASK_COMPLETION,epoch);        //time in milliseconds



        if(add) {
            setResult(1, intent);
        }else{
            intent.putExtra(ItemConstants.POSITION,position);
            setResult(2,intent);
        }
        finish();
    }

    //for checking whether the task is empty or not
    public boolean isemptyornull(String s){

        String a=s.trim();
        return a == null || a.isEmpty();

    }
}
