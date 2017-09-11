package teratroopers.teachercompanion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private  Button button_more;
    private  Button button_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OnClickButtonListener();
        OnClickListListener();

    }
        public void OnClickListListener(){
            Button button_list = (Button)findViewById(R.id.button2);
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
        Button button_addclass = (Button) findViewById(R.id.button);
        button_addclass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent_addclass = new Intent("teratroopers.teachercompanion.addclass");
                startActivity(intent_addclass);

            }
        });
    }
}
