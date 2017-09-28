package teratroopers.teachercompanion;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private  Button button_more;
    private  Button button_list;
    private  Button button_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        OnClickButtonListener();
        OnClickListListener();
        OnClicSettingListener();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

    }
        public void OnClickListListener(){
            Button button_list = (Button)findViewById(R.id.button3);
            button_list.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    Intent intent_list = new Intent("teratroopers.teachercompanion.TakeAttendence");
                    startActivity(intent_list);
                }
            });

        }

    public  void OnClickButtonListener()
    {
        Button button_addclass = (Button) findViewById(R.id.button4);
        button_addclass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent_addclass = new Intent("teratroopers.teachercompanion.addclass");
                startActivity(intent_addclass);

            }
        });
    }
    public void OnClicSettingListener(){
        Button button_setting = (Button)findViewById(R.id.button5);
        button_setting.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent_list = new Intent("teratroopers.teachercompanion.rateus");
                startActivity(intent_list);
            }
        });

    }
}
