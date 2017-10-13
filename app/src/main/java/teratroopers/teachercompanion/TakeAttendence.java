

package teratroopers.teachercompanion;

import android.app.ProgressDialog;
import java.net.*;
import java.io.*;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakeAttendence extends AppCompatActivity {

    mydbhelper mydb;
    int sroll,eroll;
    TextView disbutton;
    Button presbutton;
    Button absbutton;
    int total;
    int droll;
    int a;
    String k,pres;
    String date;
    String cname;
    public Context context;
    TextView tv;
    int count=0;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendence);
        context=this;
        mydb =new mydbhelper(this);
        Bundle b = getIntent().getExtras();
        cname = b.getString("name");
        //goToClass gtc=new goToClass(cname);
        getValues(cname);
        display();
        presentButton();
        absentButton();
        buttonclickfordisplayingvalues();


    }

    public void getValues(String name) {

        disbutton = (TextView) findViewById(R.id.textView2);
        Cursor res = mydb.getcname(name);
        res.moveToNext();
        sroll = Integer.parseInt(res.getString(0));
        droll=sroll;
        Log.i("sroll",String.valueOf(sroll));
        res.moveToLast();
        eroll = Integer.parseInt(res.getString(0));
        tv=(TextView)findViewById(R.id.count);
        k=String.valueOf(eroll);
        pres="0";
        tv.setText(pres+"/"+k+" present");
    }
    public void display(){
        String number=Integer.toString(droll);
        disbutton.setText(number);
    }

    public void presentButton(){
        total=(eroll-sroll)+1;
        presbutton=(Button)findViewById(R.id.present);
        vibrator=(Vibrator) getSystemService(VIBRATOR_SERVICE);
        presbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vibrator.vibrate(50);
                        count++;
                        pres=String.valueOf(count);
                        tv.setText(pres+"/"+k+" present");
                        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                        date = sdf.format(new Date());
                        date = "dt" + date;
                        if(droll<eroll) {
                            if (droll == sroll) {
                                Log.i("first:","droll=sroll");
                                mydb.alterTable(date,cname);
                            }
                            mydb.registerData(date,cname, droll, 1);
                            droll++;
                            display();
                        }
                        else if(droll==eroll){
                            mydb.registerData(date,cname, droll, 1);
                           // disbutton.setText("Attendance complete");
                            presbutton.setClickable(false);
                            Snackbar.make(view,"Attendance Complete",Snackbar.LENGTH_INDEFINITE)
                                    .setAction("Return",new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            finish();
                                        }
                                    }).show();

                        }
                    }
                }
        );
    }

    public void absentButton(){
        a=sroll;
        total=(eroll-sroll)+1;
        absbutton=(Button)findViewById(R.id.absent);
        absbutton = (Button) findViewById(R.id.absent);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        absbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vibrator.vibrate(50);
                        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                        date = sdf.format(new Date());
                        date = "dt" + date;
                        if (droll < eroll) {
                            if (droll == sroll) {
                                mydb.alterTable(date, cname);
                            }
                            mydb.registerData(date, cname, droll, 0);
                            droll++;
                            display();
                        } else if (droll == eroll) {
                            mydb.registerData(date, cname, droll, 0);
                            //disbutton.setText("Attendance complete");
                            absbutton.setClickable(false);
                            Snackbar.make(view, "Attendance Complete", Snackbar.LENGTH_INDEFINITE)
                                    .setAction("Return", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            finish();
                                        }
                                    }).show();
                        }
                    }
                }
        );
    }










    public void buttonclickfordisplayingvalues(){
        FloatingActionButton  butt = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        butt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                        date = sdf.format(new Date());
                        date="dt"+date;
                        StringBuffer buffer = new StringBuffer();
                        try {
                            Cursor res = mydb.retrievedatatodisplayattendance(date, cname);
                            while (res.moveToNext()) {

                                buffer.append(res.getString(0) + "=");
                                buffer.append(res.getString(1) + "\n");
                                //buffer.append("Ending Roll :" + res.getString(2) + "\n");
                            }
                            showmessage("Today's Attendance", buffer.toString());
                        }
                        catch(Exception x){
                            buffer.append("Attendance not taken today");
                            showmessage("Uh-Oh!",buffer.toString());
                        }
                    }
                }
        );


    }

    public void showmessage(String title,String Message) {
        AlertDialog.Builder builder = new  AlertDialog.Builder(this);
        builder.setCancelable(true);
       // builder.setTitle("");
        builder.setIcon(R.drawable.book);
        builder.setMessage(Message);
        builder.show();

    }

}


