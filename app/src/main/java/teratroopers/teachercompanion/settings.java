package teratroopers.teachercompanion;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

public class settings extends AppCompatActivity {



    ImageView sett;
    Switch s1,s2;
    mydbhelper mydb;
    int a=0;
    //Switch s1=(Switch)findViewById(R.id.switch1);
    //Switch s2=(Switch)findViewById(R.id.switch2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mydb =new mydbhelper(this);
        animateImage();
        vibration();
        lock();
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
                    Log.i("message",String.valueOf(a));
                     mydb.vibration(a);
                }
                else{
                    s1.setChecked(false);
                    //s1.setChecked();
                    a=0;
                    mydb.vibration(a);
                }
            }

        });
    }
    public void lock(){
        s2=(Switch)findViewById(R.id.switch2);
    }
}
