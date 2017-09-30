package com.example.pranav.myapplication;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import com.example.pranav.myapplication.IMyAidlInterface;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public ServiceConnection AddServiceObject;
    public IMyAidlInterface myAidlObject;
    public EditText ed1;
    public EditText ed2;
    public TextView sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.fab: {
                        String x = "apple";
                        Intent i = new Intent();
                        i.setComponent(new ComponentName("com.example.kranthi.myapplication","com.example.kranthi.myapplication.MainActivity"));
                        i.putExtra("variable", "apple");
                        startActivity(i);

                    }
                    break;
                }}});
        initView();
        initServiceConnection();
    }
    public void initView(){
        ed1 = (EditText) findViewById(R.id.input1);
        ed2 = (EditText) findViewById(R.id.input2);
        sum = (TextView) findViewById(R.id.output1);
        if((getIntent().getStringExtra("variable") != null) && (getIntent().getStringExtra("variable")).equals("apple"))
        {
            Log.d("flow","init view intent");
            sum.setHint("apple");
        }
    }
    public void initServiceConnection(){
       AddServiceObject = new ServiceConnection() {
           @Override
           public void onServiceConnected(ComponentName name, IBinder service) {
               Log.d("flow","onservice connected");
               myAidlObject = IMyAidlInterface.Stub.asInterface((IBinder)service);
               if(myAidlObject !=null){
                   Log.d("flow", "object initialized");
               }


           }

           @Override
           public void onServiceDisconnected(ComponentName name) {
               Log.d("flow","onservice disconnected");
               myAidlObject = null;

           }
       };

        if(myAidlObject == null) {
            Log.d("flow", "interface not initialized");
            Intent it = new Intent();
            it.setPackage("com.example.pranav.serviceapplication");
            it.setAction("service.Calculator");
            // binding to remote service
            bindService(it, AddServiceObject, Service.BIND_AUTO_CREATE);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(AddServiceObject);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
