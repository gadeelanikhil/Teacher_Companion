package teratroopers.teachercompanion;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class settings extends AppCompatActivity {


    ImageView sett;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        animateImage();
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
}
