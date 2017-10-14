package teratroopers.teachercompanion;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import de.codecrafters.tableview.TableView;
import android.graphics.Color;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.model.TableColumnDpWidthModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class RegisterForm extends AppCompatActivity {

    mydbhelper mydb;
    String cname;

    static String[] classColNames;
    static String[][] classData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);
        mydb = new mydbhelper(this);
        Bundle b = getIntent().getExtras();
        cname = b.getString("name");
        Cursor result = mydb.retrievedata(cname);
        classColNames = result.getColumnNames();
        int p=0;
        for (String k:classColNames) {
            StringBuilder str = new StringBuilder(k);
            k=k.replace("dt","");
            classColNames[p]=k;
            if(k.startsWith("1") || k.startsWith("2") || k.startsWith("3") || k.startsWith("4")|| k.startsWith("5")
                    || k.startsWith("6")|| k.startsWith("7")|| k.startsWith("8")|| k.startsWith("9")|| k.startsWith("0")){

                str.insert(2,':');
                str.insert(5,'/');
                str.insert(8,'/');
                k=str.toString();
                classColNames[p]=k;
            }
            p++;
        }

       classData=new String[result.getCount()][result.getColumnNames().length];
       result.moveToFirst();
        for(int i=0;i<result.getCount();i++){
            for(int j=0;j<result.getColumnNames().length;j++){
                try{
                    classData[i][j]=result.getString(j);
                }
                catch(NullPointerException npe){
                    classData[i][j]="";
                }
            }
            result.moveToNext();
        }


        final TableView<String[]> tableView = (TableView<String[]>) findViewById(R.id.tableView);

        tableView.setHeaderBackgroundColor(Color.parseColor("#2ecc71"));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, classColNames));
        tableView.setColumnCount(result.getColumnCount());
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, classData));

        //comment the following code if there's any error
        TableColumnDpWidthModel columnModel = new TableColumnDpWidthModel(this,result.getColumnCount(),200);
        columnModel.setColumnWidth(1, 100);
        columnModel.setColumnWidth(2, 200);
        tableView.setColumnModel(columnModel);

                tableView.addDataClickListener(new TableDataClickListener() {
                    @Override
                    public void onDataClicked(int rowIndex, Object clickedData) {
                        Toast.makeText(getApplicationContext(), ((String[]) clickedData)[1], Toast.LENGTH_SHORT).show();
                    }
                });
            }
    }


