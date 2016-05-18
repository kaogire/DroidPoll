package com.ogresolutions.kaogire.droidpoll.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ogresolutions.kaogire.droidpoll.R;
import com.ogresolutions.kaogire.droidpoll.model.ChatMessage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyReader extends AppCompatActivity {
    public final CharSequence VOTE_POS = "poll_yes";
    public final CharSequence VOTE_NEG = "poll_no";
    public final CharSequence VOTE_NEU = "poll_neither";
    public static ArrayList<ChatMessage> smsPositive = new ArrayList<ChatMessage>();
    public static ArrayList<ChatMessage> smsNeutral = new ArrayList<ChatMessage>();
    public static ArrayList<ChatMessage> smsNegative = new ArrayList<ChatMessage>();
    ListView lvSMS = null;
    String address = null;
    DateFormat dft = DateFormat.getDateTimeInstance();
    EditText edtFrom;
    Calendar dAndT1 = Calendar.getInstance();
    Calendar dAndT2 = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener d1 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            dAndT1.set(Calendar.YEAR, year);
            dAndT1.set(Calendar.MONTH, monthOfYear);
            dAndT1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(edtFrom, dAndT1);
        }
    };

    TimePickerDialog.OnTimeSetListener t1 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dAndT1.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dAndT1.set(Calendar.MINUTE, minute);
            dAndT1.set(Calendar.SECOND, 00);
            updateLabel(edtFrom, dAndT1);
        }
    };

    public void updateLabel(EditText label, Calendar calendar) {
        label.setText(dft.format(calendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtFrom = (EditText) findViewById(R.id.time1);
        edtFrom.setInputType(InputType.TYPE_NULL);
        edtFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new TimePickerDialog(MyReader.this, t1, dAndT1.get(Calendar.HOUR_OF_DAY), dAndT1.get(Calendar.MINUTE), true).show();

                new DatePickerDialog(MyReader.this, d1, dAndT1.get(Calendar.YEAR), dAndT1.get(Calendar.MONTH), dAndT1.get(Calendar.DAY_OF_MONTH)).show();
                updateLabel(edtFrom, dAndT1);
            }
        });
        Button btnResults = (Button) findViewById(R.id.btnResults);
        btnResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyReader.this, MyResults.class);
                startActivity(intent);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(edtFrom.getText())) {
                    fetchInbox();
                } else {
                    edtFrom.setError("Please pick time");
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return true;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void fetchInbox() {
        ArrayList smsPosi = new ArrayList();
        ArrayList smsNega = new ArrayList();
        ArrayList smsNeut = new ArrayList();
        int counter1 = 0, counter2 = 0, counter3 = 0, total = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String myDate = dAndT1.get(Calendar.YEAR) + "-" + (dAndT1.get(Calendar.MONTH) + 1) + "-" +
                dAndT1.get(Calendar.DAY_OF_MONTH) + "T" + dAndT1.get(Calendar.HOUR_OF_DAY) + ":" +
                dAndT1.get(Calendar.MINUTE) + ":" + dAndT1.get(Calendar.SECOND);
        Log.i("joe", myDate);

        Date fromDate = null;
        try {
            fromDate = sdf.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String filter = "date>=" + fromDate.getTime();

        Uri uriSMS = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(uriSMS, null, filter, null, null);

        cursor.moveToFirst();

        while (cursor.moveToNext()) {
            ChatMessage newSMS = new ChatMessage();
            address = cursor.getString(cursor.getColumnIndex("address"));
            String body = cursor.getString(cursor.getColumnIndex("body"));
            String person = cursor.getString(cursor.getColumnIndex("person"));
            String x = cursor.getString(cursor.getColumnIndex("date"));
            Date date = new Date(Long.parseLong(x));
            if (address != null) {
                body.toLowerCase();
                Log.i("joe", body);
                Log.i("joe", "lower");
                if (body.contains("Poll_yes") || body.contains("poll_yes")) {
                    newSMS.smsTime = date.toString();
                    newSMS.smsAddress = address;
                    newSMS.smsBody = body;
                    smsPositive.add(newSMS);
                }
                if (body.contains("Poll_neither") || body.contains("poll_neither")) {
                    newSMS.smsTime = date.toString();
                    newSMS.smsAddress = address;
                    newSMS.smsBody = body;
                    smsNeutral.add(newSMS);
                }
                if (body.contains("Poll_no") || body.contains("poll_no")) {
                    newSMS.smsTime = date.toString();
                    newSMS.smsAddress = address;
                    newSMS.smsBody = body;
                    smsNegative.add(newSMS);
                }
            }
        }
        counter1 = smsPositive.size();
        counter2 = smsNegative.size();
        counter3 = smsNeutral.size();
        total = counter1 + counter2 + counter3;
        TextView dispPositive = (TextView) findViewById(R.id.tvPositive);
        TextView dispNegative = (TextView) findViewById(R.id.tvNegative);
        TextView dispNeutral = (TextView) findViewById(R.id.tvNeutral);
        if(counter1==0|| counter2==0 ||counter3 == 0){
            if(counter1 == 0){
                dispPositive.setText("Positive: 0%");
            }
            if(counter2 == 0){
                dispNegative.setText("Negative: 0%");
            }
            if(counter3 == 0){
                dispNeutral.setText("Neutral: 0%");
            }
        }else{
            Float cent1 = ((float) counter1 / (float) total) * 100;
            Float cent3 = ((float) counter3 / (float) total) * 100;
            Float cent2 = ((float) counter2 / (float) total) * 100;

            dispNegative.setText("Negative: "+cent2+"%");
            dispPositive.setText("Positive: "+cent1+"%");
            dispNeutral.setText("Neutral: "+cent3+"%");
        }
        cursor.close();
    }
}
