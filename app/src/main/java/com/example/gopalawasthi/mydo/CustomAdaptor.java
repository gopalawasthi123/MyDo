package com.example.gopalawasthi.mydo;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.test.InstrumentationTestCase;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gopal Awasthi on 20-02-2018.
 */

public class CustomAdaptor extends BaseAdapter {
    public  interface onbuttonclicklistener{
        public void longclickdeletion(int i , long j);
        public void oneditbuttonclick(int i);
        public void oncheckboxcheckdelete(int i);
    }
    onbuttonclicklistener listener;
    ArrayList<Item> arrayList=new ArrayList<>();
    Context context;
    public static final int WITH_HEADER=0;
    public static final int WITHOUT_HEADER=1;

    public CustomAdaptor(ArrayList<Item> arrayList, Context context, onbuttonclicklistener listener) {
        this.arrayList = arrayList;
        this.context = context;
        this.listener=listener;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Item getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;

    }

    @Override
    public int getItemViewType(int position) {
        Item item=arrayList.get(position);
        if(item.isHeader()){
            return WITH_HEADER;
        }
        else{
            return WITHOUT_HEADER;
        }

    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View view1=view;
        if(view1==null){

            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Holder holder=new Holder();

            if(getItemViewType(i)==WITH_HEADER){

             view1= inflater.inflate(R.layout.headersection,viewGroup,false);

                holder.header=view1.findViewById(R.id.header);
                view1.setTag(holder);
            }
            else if(getItemViewType(i)==WITHOUT_HEADER){
                view1=inflater.inflate(R.layout.customdesign,viewGroup,false);
                holder.checkBox = view1.findViewById(R.id.check);
                holder.name=view1.findViewById(R.id.task);
                holder.edit=view1.findViewById(R.id.edit);
                holder.date=view1.findViewById(R.id.timing);
                holder.time=view1.findViewById(R.id.timer);

                holder.fordelete=view1.findViewById(R.id.textdeletion);
                view1.setTag(holder);
            }


        }
         Holder  holder=(Holder) view1.getTag();
        if(getItemViewType(i)==WITH_HEADER){

            Header header1=(Header)getItem(i);
            String a = header1.getHeader();
            if(a == "Today" ){
                holder.header.setTextColor(Color.MAGENTA);
            }else if(a == "Later"){
                holder.header.setTextColor(Color.GREEN);
            } else if(a == "OverDue"){
                holder.header.setTextColor(Color.RED);
            } else if( a == "Tomorrow"){
                holder.header.setTextColor(Color.YELLOW);
            }

            holder.header.setVisibility(view1.VISIBLE);
            holder.header.setText(header1.getHeader());
        }
        else{

            holder=(Holder) view1.getTag();
            Content content=(Content)getItem(i);
            holder.name.setText(content.getName());
            holder.date.setText(content.getDate());
            holder.time.setText(content.getTime());

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.oncheckboxcheckdelete(i);
                }
            });

            holder.fordelete.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.longclickdeletion(i, getItemId(i));
                    return true;
                }
            });
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.oneditbuttonclick(i);
                }
            });

        }


        return view1;

    }
    class Holder{
        TextView name;
        TextView date;
        TextView time;
        TextView header;
        Button edit;
        LinearLayout fordelete;
        CheckBox checkBox;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


}
















