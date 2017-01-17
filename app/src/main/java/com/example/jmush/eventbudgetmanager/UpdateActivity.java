package com.example.jmush.eventbudgetmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jmush on 12/28/16.
 */

public class UpdateActivity extends Activity {

    private EditText etNtid, etName, etPhone;
    private Button update;
    private DBAdapter dbh;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // // get Instance of Database Adapter
        dbh = new DBAdapter(this);
        bundle = getIntent().getExtras();
        final String NTID = bundle.getString("NTID");
        final String NAME = bundle.getString("NAME");
        final String PHONE = bundle.getString("PHONE");


        // Get References of Views
        etNtid = (EditText) findViewById(R.id.etntid1);
        etName = (EditText) findViewById(R.id.etname1);
        etPhone = (EditText) findViewById(R.id.etphone1);
        update = (Button) findViewById(R.id.btnUpdate);
        etNtid.setText(NTID);
        etName.setText(NAME);
        etPhone.setText(PHONE);

        update.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                String Name = etName.getText().toString();
                String Phone = etPhone.getText().toString();


                // check if any of the fields are vaccant
                if (Name.equals("") || Phone.equals("")) {
                    Toast.makeText(UpdateActivity.this, "Field Vaccant",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                final RetreiveName retreiveName=new RetreiveName();
                retreiveName.setNtid(NTID);
                retreiveName.setMemberName(etName.getText().toString());
                retreiveName.setPhone(etPhone.getText().toString());
                long yes = dbh.updateMember(retreiveName);
                if (yes != 0) {
                    Toast.makeText(UpdateActivity.this,
                            "Successfully Updated ",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(UpdateActivity.this,
                            MemberActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(
                            UpdateActivity.this,
                            "Problem with Updating please Try again ",
                            Toast.LENGTH_LONG).show();
                }
            }


        });

    }

    }

