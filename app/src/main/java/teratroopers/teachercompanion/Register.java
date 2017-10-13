package teratroopers.teachercompanion;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Register extends AppCompatActivity implements View.OnClickListener {

    mydbhelper mydb;
    Button b1;
    LinearLayout linearLayout;
    int i;
    String cname;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        linearLayout=(LinearLayout)findViewById(R.id.linearLayout2);
        this.mydb = new mydbhelper((Context)this);
        back();
        createcards();
    }
    public void back(){
        Button back =(Button)findViewById(R.id.backpress);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void createcards(){
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,150);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
        lp1.setMargins(20, 20, 20, 10); //ltrd
        final Cursor res=mydb.getalldata();
        final Handler handler = new Handler();

        while (res.moveToNext()) {
            i = 0;
            cname = res.getString(0);
            b1 = new Button(this);
            b1.setText(res.getString(0));
            b1.setTag(res.getString(0));
            //b1.setElevation(3.8f);
            b1.setBackgroundResource(R.drawable.backbutt);
            b1.setLayoutParams(lp1);
            b1.setGravity(Gravity.CENTER);
            linearLayout.addView(b1);
            b1.setOnClickListener(this);
        }
    }
    public void onClick(View v) {
        String str=v.getTag().toString();
        Intent intent=new Intent("teratroopers.teachercompanion.RegisterForm");
        intent.putExtra("name",str);
        startActivity(intent);
    }
}

