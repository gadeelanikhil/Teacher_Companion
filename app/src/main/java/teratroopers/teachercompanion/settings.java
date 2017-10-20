package teratroopers.teachercompanion;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.*;
import android.telephony.*;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import java.util.Random;

public class settings extends AppCompatActivity {


    Button bt,bt1;
    ImageView sett;
    Switch s1,s2;
    mydbhelper mydb;
    EditText t;
    Context context;
    public SmsManager smsManager;
    TextView tv;
    int a=0,count=0;
    //Switch s1=(Switch)findViewById(R.id.switch1);
    //Switch s2=(Switch)findViewById(R.id.switch2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mydb =new mydbhelper(this);
        context=this;
        bt=(Button)findViewById(R.id.button7);
        bt1=(Button)findViewById(R.id.fbtn);
        bt1.setVisibility(View.INVISIBLE);
        t=(EditText)findViewById(R.id.editText4);
        tv=(TextView)findViewById(R.id.textView9);
        smsManager = SmsManager.getDefault();
        animateImage();
        vibration();
        lock();
        button();
        back();
    }
    public void animateImage(){
        sett=(ImageView)findViewById(R.id.imageView4);
        sett.animate().rotation(-45).setDuration(500);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sett.animate().rotation(45).setDuration(500);
            }
        }, 500);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sett.animate().rotation(0).setDuration(500);
            }
        }, 1000);
    }
    public void vibration(){
        s1=(Switch)findViewById(R.id.switch1);
        int b=mydb.vibration1();
        if(b==1){
            s1.setChecked(true);
        }
        else{
            s1.setChecked(false);
        }
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", ""+isChecked);
                if(isChecked){
                    s1.setChecked(true);
                    a=1;
                     mydb.vibration(a);
                }
                else{
                    s1.setChecked(false);
                    a=0;
                    mydb.vibration(a);
                }
            }

        });
    }
    public void lock(){
        s2=(Switch)findViewById(R.id.switch2);
        bt.setVisibility(View.INVISIBLE);
        t.setVisibility(View.INVISIBLE);
        tv.setVisibility(View.INVISIBLE);
        int b=mydb.check1();
        if(b==5){
            s2.setChecked(false);
        }
        else{
            s2.setChecked(true);
        }

        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", ""+isChecked);
                if(isChecked){
                  int a =  mydb.check1();
                    if(a==5) {
                        s2.setChecked(true);
                    }else{
                        s2.setChecked(false);
                        //Toast.makeText(getApplicationContext(), "already it is in locked mode pls unlock",
                                //Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    s2.setChecked(false);
                }
                bt.setVisibility(View.VISIBLE);
                t.setVisibility(View.VISIBLE);
                tv.setVisibility(View.VISIBLE);
            }

        });
    }
    public void button(){

        bt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                // Read more: http://mrbool.com/android-message-how-to-send-receive-sms-using-the-built-in-messaging-application-in-android/31138#ixzz4w3s7wBAl
               boolean b= s2.isChecked();
                int z=0;
                String s;
                try {
                    s = t.getText().toString();
                    t.setText("");

                    t.setVisibility(View.INVISIBLE);
                    tv.setVisibility(View.INVISIBLE);
                    bt.setVisibility(View.INVISIBLE);
                    int a = mydb.bt(Integer.parseInt(s), b);
                    if (a == 0) {
                        s2.setChecked(true);
                        t.setVisibility(View.VISIBLE);
                        tv.setVisibility(View.VISIBLE);
                        bt.setVisibility(View.VISIBLE);
                        count++;
                        Toast.makeText(getApplicationContext(), "plss enter the valid pin",
                                Toast.LENGTH_SHORT).show();
                        if(count>3){
                            bt1.setVisibility(view.VISIBLE);
                            bt1.setOnClickListener(new View.OnClickListener(){
                                public void onClick(View view){

                                    String sendTo = "8309927066";
                                    PendingIntent sentPI;
                                    Random r = new Random();
                                    int a=r.nextInt(999999);
                                    mydb.password(a);
                                    String SENT = "SMS SENT";
                                    sentPI = PendingIntent.getBroadcast(context, 0,new Intent(SENT), 0);
                                    String myMessage =String.valueOf(a);
                                    try {
                                        smsManager.sendTextMessage(sendTo, null, myMessage, null, sentPI);
                                        Toast.makeText(getApplicationContext(), "SMS sent.",
                                                Toast.LENGTH_LONG).show();
                                        bt1.setVisibility(View.INVISIBLE);
                                        password();
                                    }catch(Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "plss enter the pin",
                            Toast.LENGTH_SHORT).show();
                    z=1;
                    if(b) {
                        s2.setChecked(false);
                    }else{
                        s2.setChecked(true);
                    }
                }
                if(z==1){
                    t.setVisibility(View.INVISIBLE);
                    tv.setVisibility(View.INVISIBLE);
                    bt.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    public void back(){
        Button back =(Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void password(){
        tv.setText("OTP");
        bt.setText("confirm");
        bt.setOnClickListener(new View.OnClickListener() {
                                  public void onClick(View view) {
                                      String s=t.getText().toString();
                                     int a= mydb.password1(s);
                                      if(a==1){
                                          int b=mydb.check1();
                                          String q=String.valueOf(b);
                                          tv.setVisibility(View.INVISIBLE);
                                          bt.setVisibility(View.INVISIBLE);
                                          t.setText("");
                                          t.setVisibility(View.INVISIBLE);
                                          Toast.makeText(getApplicationContext(),"your password is:"+q,
                                                  Toast.LENGTH_LONG).show();
                                      }
                                      else{
                                          Toast.makeText(getApplicationContext(), "entered wrong OTP",
                                                  Toast.LENGTH_LONG).show();
                                      }
                                  }
                              });

    }
}
