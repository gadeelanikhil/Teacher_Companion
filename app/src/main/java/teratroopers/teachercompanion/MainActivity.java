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


    Button btnaddclass,btnclasslist,btnregister,btnstatistics,btnsettings,btnmore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        btnaddclass=(Button)findViewById(R.id.addclass);
        btnregister=(Button)findViewById(R.id.register);
        btnclasslist=(Button)findViewById(R.id.classlist);
        btnstatistics=(Button)findViewById(R.id.statistics);
        btnsettings=(Button)findViewById(R.id.settings);
        btnmore=(Button)findViewById(R.id.more);
        activityListener();
    }
    public void activityListener(){
        btnaddclass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent addclassAct=new Intent("teratroopers.teachercompanion.addclass");
                        startActivity(addclassAct);
                    }
                }
        );
        btnclasslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent classListAct=new Intent("teratroopers.teachercompanion.ClassList");
                startActivity(classListAct);
            }
        });
        btnsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsAct=new Intent("teratroopers.teachercompanion.settings");
                startActivity(settingsAct);
            }
        });
        btnmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsAct=new Intent("teratroopers.teachercompanion.activity_more");
                startActivity(settingsAct);
            }
        });
    }


}
