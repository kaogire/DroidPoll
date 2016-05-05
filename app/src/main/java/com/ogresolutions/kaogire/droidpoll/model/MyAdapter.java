package com.ogresolutions.kaogire.droidpoll.model;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ogresolutions.kaogire.droidpoll.R;

import java.util.ArrayList;

/**
 * Created by Njuguna on 4/22/2016.
 */
public class MyAdapter extends BaseAdapter {
    private TextView tvmySMS;
    private ArrayList<ChatMessage> listSMS = new ArrayList<ChatMessage>();
    private LinearLayout containerSMS;
    private ArrayList<ChatMessage> theSMS;
    private static LayoutInflater inflater = null;

    public MyAdapter (Context context, ArrayList<ChatMessage> theSMS){
        this.theSMS =  theSMS;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount(){
        return this.theSMS.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

     @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null){
            row = inflater.inflate(R.layout.single_message, parent, false);
        }
        containerSMS = (LinearLayout) row.findViewById(R.id.container);
        ChatMessage myMessage;
        myMessage = theSMS.get(position);
        tvmySMS = (TextView) row.findViewById(R.id.tvSms);
        tvmySMS.setText(myMessage.smsBody);
//         Log.i("joe","adapter initialization");
        tvmySMS.setBackgroundResource(R.drawable.my_message);
        containerSMS.setGravity(Gravity.LEFT);
        return row;
    }
}

