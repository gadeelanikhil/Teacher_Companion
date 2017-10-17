package teratroopers.teachercompanion;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class mydbhelper extends SQLiteOpenHelper {
    public static final String DATABSE_NAME="student.sqLiteDatabase";
    public static  String TABLE_NAME;
    public static final String cTABLE_NAME="cTABLE";
    public static final String settings="Settings";
    public static final String COL1="rollnos";
    public static final String COL2="studnames";
    public static final String COL3="count";
    public static final String CTCOL1="classname";
    public static final String CL1="key";
    public static final String CL2="values";
    public static final String CTCOL2="total";
    public List<Integer> list = new ArrayList<>();
    public List<Integer> li = new ArrayList<>();

    boolean k=false;
    int g,count;
    Cursor req;


    public mydbhelper(Context context) {
        super(context,DATABSE_NAME, null, 1);
    }
    public SQLiteDatabase sqLiteDatabase;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
        ContentValues contentvalues = new ContentValues();
        Log.i("info", sqLiteDatabase.toString());
        sqLiteDatabase.execSQL("create table " + cTABLE_NAME + "(" + CTCOL1 + " TEXT, " + CTCOL2 + " INTEGER);");
        sqLiteDatabase.execSQL("create table " + settings + "(" + CL1 + " INTEGER );");
        contentvalues.put(CL1, 0);
        sqLiteDatabase.insert(settings, null, contentvalues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData( int sr, int er) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        for(int i=sr;i<=er;i++) {
            contentvalues.put(COL1, i);
            contentvalues.put(COL2,"");
            contentvalues.put(COL3,0);
            long result = sqLiteDatabase.insert(TABLE_NAME, null, contentvalues);
            if (result == -1) {
                k= false;
            }
            else {
                k= true;
            }
        }
        return k;
    }
    public Cursor getalldata() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from " + cTABLE_NAME, null);
        return res;

    }
    public Cursor getcname(String cname){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor result=sqLiteDatabase.rawQuery("Select "+COL1+" from "+cname,null);
        return result;
    }

    public boolean dbname(String cname, int sr, int er){   //test.java activity (class add)
        ContentValues contentvalues = new ContentValues();
        sqLiteDatabase=this.getWritableDatabase();
        TABLE_NAME=cname;
        Log.i("tname",TABLE_NAME);

        sqLiteDatabase.execSQL("create table if not exists "+TABLE_NAME+"("+COL1+" INTEGER,"+COL2+ " TEXT, "+COL3+" INTEGER);");
        boolean c=checkclassname();
        if(c==true) {
            contentvalues.put(CTCOL1,TABLE_NAME);
            contentvalues.put(CTCOL2,0);
            sqLiteDatabase.insert(cTABLE_NAME, null, contentvalues);

            Log.i("class table insertion:", "success");
            k = insertData(sr, er);
            Log.i("our nikhil:", "success");
            return k;
        }
        else return false;
    }

    public boolean checkclassname(){ //checks class name for existence

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Log.i("came to checkclassname","success");
        Cursor result = sqLiteDatabase.rawQuery("Select count(*) from " + cTABLE_NAME + " where " + CTCOL1 + "=" + "'"+TABLE_NAME+"'", null);
        result.moveToNext();
        int k=Integer.parseInt(result.getString(0));
        Log.i("value of k:",String.valueOf(k));
        if(k==0)
            return true;
        else
            return false;
    }

    public void deleteclass(String classname){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        //sqLiteDatabase.execSQL("delete TABLE IF EXISTS " + classname);
        sqLiteDatabase.delete(classname,null,null);
        sqLiteDatabase.delete(cTABLE_NAME,CTCOL1+"="+"'"+classname+"'",null);
        try {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + classname);
        }
        catch (Exception e){
            Log.i("deleteclass","exception");
        }
    }


    public void alterTable(String date,String cname,int sroll,int eroll){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        //ContentValues contentValues = new ContentValues();
        boolean k=isFieldExist(cname,date);
        if(k) {
            try{
                sqLiteDatabase.execSQL("alter table " + cname + " add "+date+" INTEGER");
                Log.i("table altered:", "success");
                for(int i=sroll;i<=eroll;i++) {
                    sqLiteDatabase.execSQL("UPDATE " + cname + " SET " + date + " = " + "0" + " WHERE " + COL1 + " = " + i);
                }
            }
            catch (Exception e){
                Log.i("Attendance taken:","finish");
            }
        }
        else {
            Log.i("column alreadyis taken:","finish");
        }
    }

    public Cursor retrievedatatodisplayattendance(String date,String classname){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor result = sqLiteDatabase.rawQuery("Select "+COL1+"," + COL3+","+ date +" from " + classname,null);
        return result;
    }

    public void registerData(String date,String cname,int droll,int i,int sroll,int eroll){
        li.add(droll);
        list.add(i);
        g=droll;
        if(g==eroll) {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            for(i=0;i<(eroll-sroll)+1;i++) {
                if(sroll==li.get(i)){
                    count=0;
                }
                if(count==0){
                    sqLiteDatabase.execSQL("UPDATE " + cTABLE_NAME + " SET " + CTCOL2 + " = " + CTCOL2 + " + " + 1 + " WHERE " + CTCOL1 + " = " + "'" +cname + "'");
                    count++;
                }
                sqLiteDatabase.execSQL("UPDATE " + cname + " SET " + date + " = " + date + " + " + list.get(i) + " WHERE " + COL1 + " = " + li.get(i));
                sqLiteDatabase.execSQL("UPDATE " + cname + " SET " + COL3 + " = " + COL3 + " + " + list.get(i) + " WHERE " + COL1 + " = " + li.get(i));
            }
        }
    }

    public boolean isFieldExist(String tableName, String fieldName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("PRAGMA table_info("+tableName+")",null);
        int i= res.getColumnIndex(fieldName);

        if(i == -1)
        {
            Log.i("Row not exist","yes");
            return true;
        }
        else return false;
    }
    public Cursor retrievedata(String cname){
        SQLiteDatabase db = this.getWritableDatabase();
         req = db.rawQuery("Select * from "+cname,null);
        return req;
    }
    public Cursor statistics(String cname){
        SQLiteDatabase db = this.getWritableDatabase();
       // req = db.rawQuery("Select * from "+cname,null);
        req =db.rawQuery("Select "+COL1+"," + COL3 + " from " + cname,null);
        return req;
    }
    public Cursor statistics1(String cname){
        SQLiteDatabase db = this.getWritableDatabase();
        // req = db.rawQuery("Select * from "+cname,null);
        //req =db.rawQuery("Select "+COL1+"," + COL3 + ", from " + cname,null);
        Cursor result = db.rawQuery("Select " +CTCOL2 + " from " + cTABLE_NAME + " where " + CTCOL1 + "=" + "'"+cname+"'", null);
        return result;
    }
    public void vibration(int a){
        if(a==1){
            try {
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL("UPDATE " + settings + " SET " + CL1 + "= 1 where " + CL1 + "= 0");
            }
            catch (Exception e){

            }
        }
        else{
            try {
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL("UPDATE " + settings + " SET " + CL1 + "= 0 where " + CL1 + "= 1");
            }
            catch (Exception e){

            }
        }
    }
    public int vibration1(){
        SQLiteDatabase db = this.getWritableDatabase();
        int a;
        Cursor c = db.rawQuery("select * from "+ settings ,null);
        c.moveToNext();
        a = c.getInt(0);
        return  a;
    }
}



