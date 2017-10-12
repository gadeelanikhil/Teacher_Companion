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


public class addclass extends AppCompatActivity {
    mydbhelper mydb;
    EditText a1,a2,a3;
    Button viw;
    TextView tv;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addclass);
        mydb =new mydbhelper(this);
        a1 = (EditText) findViewById(R.id.editText);
        a2 = (EditText) findViewById(R.id.editText2);
        a3 = (EditText) findViewById(R.id.editText3);
        viw=(Button)findViewById(R.id.button6);
        fab=(FloatingActionButton)findViewById(R.id.floatingActionButton);
        animateImg();
        confirmfab();

    }
    public void animateImg(){
        ImageView addclassimg=(ImageView)findViewById(R.id.addimg);
        addclassimg.animate().rotation(360).setDuration(900);
    }
    public void confirmfab()
    {

        fab.setOnClickListener(
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
                                   Toast.makeText(addclass.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                                   /* Snackbar mySnackbar = Snackbar.make(findViewById(R.id.addclass),
                                            "Class Added", Snackbar.LENGTH_SHORT);
                                   // mySnackbar.setAction("UNDO", new MyUndoListener());//
                                    mySnackbar.show();
                                  /*  public class MyUndoListener implements View.OnClickListener{

                                        @Override
                                        public void onClick(View v) {

                                            // Code to undo the user's last action
                                        }
                                    }*/

                                    a1.setText("");
                                    a2.setText("");
                                    a3.setText("");
                                    tv=(TextView)findViewById(R.id.classaddtext);
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Do something after 5s = 5000ms
                                            tv.animate().alpha(1).setDuration(2000);
                                        }
                                    }, 3000);

                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Do something after 5s = 5000ms
                                            tv.animate().alpha(0).setDuration(2000);
                                        }
                                    }, 7500);


                                } else {
                                    Toast.makeText(addclass.this, "internal error occurred! please create class with another name",
                                            Toast.LENGTH_SHORT).show();
                                  /*  Snackbar mineSnackBar = Snackbar.make(findViewById(R.id.addclass),
                                            "Something went wrong! Please Create class with another name", Snackbar.LENGTH_SHORT);
                                    mineSnackBar.show();*/

                                }
                            }
                            else {
                               Toast.makeText(addclass.this, "Start Roll_no should be less than End Roll_no", Toast.LENGTH_SHORT).show();

                               /* Snackbar minSnackBar = Snackbar.make(findViewById(R.id.addclass),
                                        "Start Roll_no should be less than End Roll_no", Snackbar.LENGTH_SHORT);
                                minSnackBar.show();*/
                            }
                        }
                        catch (Exception e) {
                           /* Snackbar miSnackBar = Snackbar.make(findViewById(R.id.addclass),
                                    "Something is wrong! Please try again", Snackbar.LENGTH_SHORT);
                            miSnackBar.show();*/
                            Toast.makeText(addclass.this, "Enter data in all fields", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

}
