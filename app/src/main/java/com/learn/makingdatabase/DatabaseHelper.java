package com.learn.makingdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Vineet on 15-03-2016.
 */
public class DatabaseHelper
{   SQLiteDatabase db;
    DataBaseCreater dataBaseCreater;
    DatabaseHelper(Context context)
    {

        dataBaseCreater=new DataBaseCreater(context);
    }

    public long insertData(String name,String password,String address)
    {
        db=dataBaseCreater.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DataBaseCreater.NAME,name);
        contentValues.put(DataBaseCreater.PASSWORD, password);
        contentValues.put(DataBaseCreater.ADDRESS,address);
        long id=db.insert(DataBaseCreater.TABLE_NAME,null,contentValues);
        return id;
    }

    public String getAllData()
    {
        SQLiteDatabase db=dataBaseCreater.getWritableDatabase();
        String columns[]={dataBaseCreater.UID,dataBaseCreater.NAME,dataBaseCreater.PASSWORD,dataBaseCreater.ADDRESS};
        Cursor cursor= db.query(dataBaseCreater.TABLE_NAME, columns, null, null, null, null, null);
        StringBuffer stringBuffer=new StringBuffer();
        while(cursor.moveToNext()==true)
        {
            int cid=cursor.getInt(0);
            String personName=cursor.getString(1);
            String personPassword=cursor.getString(2);
            String personAddress=cursor.getString(3);
            stringBuffer.append(cid+" "+personName+" "+personPassword+" "+personAddress+"\n");
        }
        return stringBuffer.toString();
    }

    public String getData(String name)
    {
        SQLiteDatabase db=dataBaseCreater.getWritableDatabase();
        String columns[]={dataBaseCreater.UID,dataBaseCreater.NAME,dataBaseCreater.ADDRESS};
        Cursor cursor= db.query(dataBaseCreater.TABLE_NAME, columns,DataBaseCreater.NAME+" ='"+name+"'",null,null,null,null);
        StringBuffer stringBuffer=new StringBuffer();
        while(cursor.moveToNext()==true)
        {
            int cid=cursor.getInt(cursor.getColumnIndex(DataBaseCreater.UID));
            String personName=cursor.getString(cursor.getColumnIndex(DataBaseCreater.NAME));
            String personAddress=cursor.getString(cursor.getColumnIndex(DataBaseCreater.ADDRESS));
            stringBuffer.append(cid+" "+personName+" "+personAddress+"\n");
        }
        return stringBuffer.toString();
    }

    public int updateInfo(String name)
    {
        SQLiteDatabase db=dataBaseCreater.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(dataBaseCreater.NAME,name);
       int count= db.update(dataBaseCreater.TABLE_NAME,contentValues,dataBaseCreater.NAME+" ='Vineet'",null);
        return count;
    }

    public int deleteInfo(String name)
    {
        SQLiteDatabase db=dataBaseCreater.getWritableDatabase();
        int count=db.delete(dataBaseCreater.TABLE_NAME,DataBaseCreater.NAME+"='"+name+"'",null);
        return count;
    }
    static class DataBaseCreater extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME="VinsDataBase";
        private static final String TABLE_NAME="VinsTable";
        private static final String NAME="Name";
        private static final String PASSWORD="Password";
        private static final String ADDRESS="Address";
        private static final String UID="_id";
        private static final int DATABASE_VERSION=5;
        private static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255),"+PASSWORD+" VARCHAR(255), "+ADDRESS+" VARCHAR(255));";
        private static final String DROP_TABLE="DROP TABLE IF EXISTS "+TABLE_NAME;
        Context c;
        public DataBaseCreater(Context context)
        {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            c=context;
            Toast.makeText(c, "Constructor Called", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            Log.d("Created","Database");
            Toast.makeText(c, "onCreate Called", Toast.LENGTH_SHORT).show();
            try{
                db.execSQL(CREATE_TABLE);
            }
            catch (SQLException e)
            {
                Log.e("Error in Creating", "Database did not get created");
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Toast.makeText(c, "onUpgrade Called", Toast.LENGTH_SHORT).show();
            try{
                db.execSQL(DROP_TABLE);
                db.execSQL(CREATE_TABLE);

            }
            catch (SQLException e)
            {
                Log.e("Error in Creating", "Database did not get created");
                e.printStackTrace();
            }


        }
    }
}
