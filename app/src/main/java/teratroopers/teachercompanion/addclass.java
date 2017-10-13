package teratroopers.teachercompanion;

import android.database.Cursor;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.spark.submitbutton.SubmitButton;


public class addclass extends AppCompatActivity {
    mydbhelper mydb;
    EditText a1,a2,a3;
    Button viw;
    FloatingActionButton fab;
    SubmitButton sbn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addclass);
        mydb =new mydbhelper(this);
        a1 = (EditText) findViewById(R.id.name);
        a2 = (EditText) findViewById(R.id.sr);
        a3 = (EditText) findViewById(R.id.er);
        viw=(Button)findViewById(R.id.button6);
        //fab=(FloatingActionButton)findViewById(R.id.floatingActionButton);
        sbn=(SubmitButton)findViewById(R.id.sub);
        sbn.bringToFront();

        animateImg();
        confirmfab();
        back();

    }
    public void animateImg(){
        ImageView addclassimg=(ImageView)findViewById(R.id.addimg);
        addclassimg.animate().rotation(360).setDuration(900);
    }
    public void confirmfab()
    {

        sbn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            int sr, er;
                            String cname;
                            sr = Integer.parseInt(a2.getText().toString());
                            er = Integer.parseInt(a3.getText().toString());
                            cname=a1.getText().toString();

                            if (sr < er) {
                                boolean isInserted=mydb.dbname(cname,sr,er);
                                // boolean isInserted = mydb.insertData(cname, sr, er);
                                if (isInserted == true) {
                                    a1.setText("");
                                    a2.setText("");
                                    a3.setText("");

                                } else {
                                    sbn.setAlpha(0);
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            sbn.setAlpha(1);
                                        }
                                    }, 2000);
                                    Toast.makeText(addclass.this, "Class with same name exists!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                               Toast.makeText(addclass.this, "Start Roll_no should be less than End Roll_no", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e) {
                            Toast.makeText(addclass.this, "Enter data in all fields", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
    public void back(){
        Button back =(Button)findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
