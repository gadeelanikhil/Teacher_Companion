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

public class settings extends AppCompatActivity {


    Button bt;
    ImageView sett;
    Switch s1,s2;
    mydbhelper mydb;
    EditText t;
    TextView tv;
    int a=0;
    //Switch s1=(Switch)findViewById(R.id.switch1);
    //Switch s2=(Switch)findViewById(R.id.switch2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mydb =new mydbhelper(this);
        bt=(Button)findViewById(R.id.button7);
        t=(EditText)findViewById(R.id.editText4);
        tv=(TextView)findViewById(R.id.textView9);
        animateImage();
        vibration();
        lock();
        button();
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
        int b=mydb.lock1();
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
                    s2.setChecked(true);
                    bt.setVisibility(View.VISIBLE);
                    t.setVisibility(View.VISIBLE);
                    tv.setVisibility(View.VISIBLE);
                }
                else{
                    bt.setVisibility(View.VISIBLE);
                    s2.setChecked(false);
                    t.setVisibility(View.VISIBLE);
                    tv.setVisibility(View.VISIBLE);
                }
            }

        });
    }
    public void button(){

        bt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
               boolean b= s2.isChecked();
                String s;
                try {
                   s = t.getText().toString();
                }
                catch (Exception e){
                    s="";
                    Toast.makeText(getApplicationContext(), "plss enter the pin",
                            Toast.LENGTH_SHORT).show();
                }
                t.setVisibility(View.INVISIBLE);
                tv.setVisibility(View.INVISIBLE);
                bt.setVisibility(View.INVISIBLE);
               int a = mydb.bt(Integer.parseInt(s),b);
                tv.setText("");
                if(a==0){
                    s2.setChecked(true);
                    t.setVisibility(View.INVISIBLE);
                    tv.setVisibility(View.INVISIBLE);
                    bt.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "plss enter the valid pin",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
