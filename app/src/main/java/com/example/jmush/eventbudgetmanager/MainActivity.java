package com.example.jmush.eventbudgetmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public SessionManager sessionManager;
    public TextView tvname;
    public Button btnEvents,btnMembers,btnFunds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        sessionManager=new SessionManager(this);
        tvname= (TextView) findViewById(R.id.tvName);
        Intent intent=getIntent();
        String Name=intent.getStringExtra("name");
        tvname.setText(Name);

        btnEvents= (Button) findViewById(R.id.btnEvents);
        btnMembers= (Button) findViewById(R.id.btnMembers);
        btnFunds= (Button) findViewById(R.id.btnFunds);
        //  Log.d("Name",Name);

        btnMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MemberActivity.class);
                startActivity(intent);
            }
        });

        btnFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,FundsActivity.class);
                startActivity(intent);

            }
        });

        btnEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,EventsActivity.class);
                startActivity(intent);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        if (sessionManager.isLoggedIn()) {

            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(true);

        } else {
            menu.getItem(1).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();

        if (id == R.id.login) {

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;

        }
       else if(id==R.id.logout){
            sessionManager.logoutUser();
            Toast.makeText(MainActivity.this,
                    "you logged out from your A/c",
                    Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);*/
        switch (item.getItemId()) {

            case R.id.login:

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.logout:

                sessionManager.logoutUser();
                Toast.makeText(MainActivity.this,
                        "you logged out from your A/c",
                        Toast.LENGTH_LONG).show();
                break;
            default:
                return onOptionsItemSelected(item);


        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        finish();

    }
}