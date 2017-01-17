package com.example.jmush.eventbudgetmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by jmush on 1/5/17.
 */

public class EventsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        sessionManager=new SessionManager(this);
        toolbar= (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
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

                Intent intent = new Intent(EventsActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.logout:

                sessionManager.logoutUser();
                Toast.makeText(EventsActivity.this,
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
