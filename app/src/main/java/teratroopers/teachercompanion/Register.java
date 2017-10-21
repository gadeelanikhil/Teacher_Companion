package teratroopers.teachercompanion;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.Locale;
import android.util.*;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


import java.io.File;

public class Register extends AppCompatActivity implements View.OnClickListener {

    mydbhelper mydb;
    Button b1;
    LinearLayout linearLayout;
    int i;
    String cname,str;

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
            b1.setOnLongClickListener(
                    new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            str=view.getTag().toString();
                            new AlertDialog.Builder(Register.this)
                                    .setTitle("Do you want to delete the class "+"  ?")
                                    .setMessage("You can not undo the action")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            Toast.makeText(getApplicationContext()," is deleted from the records",Toast.LENGTH_SHORT).show();
                                           // mydb.deleteclass(str);
                                              Cursor result = mydb.retrievetoxml(str);
                                             convert(str);
                                            // Bundle configBundle = new Bundle();
                                            //onCreate(savedInstanceState);
                                        }})
                                    .setNegativeButton(android.R.string.no, null).show();
                            return true;
                        }

                    }

            );
        }
    }
    public void onClick(View v) {
        String str=v.getTag().toString();
        Intent intent=new Intent("teratroopers.teachercompanion.RegisterForm");
        intent.putExtra("name",str);
        intent.putExtra("value","register");
        startActivity(intent);
    }
    public void convert(String str){
        Cursor cursor = mydb.retrievetoxml(str);

        File sd = Environment.getExternalStorageDirectory();
        String csvFile = "myData.xls";

        File directory = new File(sd.getAbsolutePath());
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        try {

            //file path

            File file = new File(directory, csvFile);

            WorkbookSettings wbSettings = new WorkbookSettings();

            wbSettings.setLocale(new Locale("en", "EN"));

            WritableWorkbook workbook;

            workbook = Workbook.createWorkbook(file, wbSettings);

            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("userList", 0);

            // column and row
            sheet.addCell(new Label(0, 0, "ROllNo"));

            sheet.addCell(new Label(1, 0, "StudentNames"));

            sheet.addCell(new Label(1, 0, "count"));

            //sheet.addCell(new Label(1, 0, "date"));


            if (cursor.moveToFirst()) {
                do {
                    String rollno = cursor.getString(cursor.getColumnIndex("rollnos"));
                    String name = cursor.getString(cursor.getColumnIndex("studnames"));
                    String count = cursor.getString(cursor.getColumnIndex("count"));

                    int i = cursor.getPosition() + 1;
                    sheet.addCell(new Label(0, i, rollno));
                    sheet.addCell(new Label(1, i, name));
                    sheet.addCell(new Label(2, i, count));
                } while (cursor.moveToNext());
            }

            //closing cursor
            cursor.close();
            workbook.write();
            workbook.close();
            Toast.makeText(getApplication(),
                    "Data Exported in a Excel Sheet", Toast.LENGTH_SHORT).show();
        } catch(Exception e){
            e.printStackTrace();
        }




    }
}

