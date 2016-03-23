package com.learn.makingdatabase;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    DatabaseHelper databaseHelper;
    EditText name;
    EditText password;
    EditText address;
    Button viewAllDetails;
    Button getInfo;
    EditText getUsernameInfo;
    Button updater;
    Button deleter;

    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name= (EditText) findViewById(R.id.editText);
        password= (EditText) findViewById(R.id.editText2);
        address= (EditText) findViewById(R.id.editText3);
        viewAllDetails= (Button) findViewById(R.id.viewAllDetails);
        databaseHelper=new DatabaseHelper(this);
        save= (Button) findViewById(R.id.button);
        getInfo= (Button) findViewById(R.id.getData);
        getUsernameInfo= (EditText) findViewById(R.id.userNameEdit);
        updater= (Button) findViewById(R.id.Update);
        deleter= (Button) findViewById(R.id.Delete);
        //Using save button by onClickListener
        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                long id=databaseHelper.insertData(name.getText().toString(),password.getText().toString(),address.getText().toString());
                if(id<0)
                {
                    Toast.makeText(MainActivity.this, "Insertion Failed", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Inserted to Database successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewAllDetails.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String data=databaseHelper.getAllData();
                Toast.makeText(MainActivity.this, ""+data, Toast.LENGTH_SHORT).show();
            }
        });

        getInfo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String data=databaseHelper.getData(getUsernameInfo.getText().toString());
                Toast.makeText(MainActivity.this, ""+data, Toast.LENGTH_SHORT).show();
            }
        });

        updater.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int count=databaseHelper.updateInfo(getUsernameInfo.getText().toString());
                Toast.makeText(MainActivity.this, "No of Row Changed ="+count, Toast.LENGTH_SHORT).show();
            }
        });
        deleter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int count=databaseHelper.deleteInfo(getUsernameInfo.getText().toString());
                Toast.makeText(MainActivity.this, "No of Row Changed ="+count, Toast.LENGTH_SHORT).show();
            }
        });

    }




}
