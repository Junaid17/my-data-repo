package com.example.jmush.eventbudgetmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by jmush on 12/23/16.
 */

public class AddMemberActivity extends Activity {
    private EditText etNtid, etName, etPhone;
    private Button save;

    private DBAdapter dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmember);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

        // // get Instance of Database Adapter
        dbh = new DBAdapter(this);

        // Get References of Views
        etNtid = (EditText) findViewById(R.id.etntid);
        etName = (EditText) findViewById(R.id.etname);
        etPhone = (EditText) findViewById(R.id.etphone);


        save = (Button) findViewById(R.id.btnsave);
        save.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                String Ntid = etNtid.getText().toString();
                String Name = etName.getText().toString();
                String Phone = etPhone.getText().toString();


                // check if any of the fields are vaccant
                if (Ntid.equals("") || Name.equals("") || Phone.equals("")) {
                    Toast.makeText(AddMemberActivity.this, "Field Vaccant",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                 else {
                    int exst = dbh.ExistsNtid(Ntid);
                    // Save the Data in Database
                    if (exst == 0) {
                        long yes = dbh.insertMemebers(Ntid,Name,Phone,0);
                        if (yes != 0) {
                            Toast.makeText(AddMemberActivity.this,
                                    "Successfully Added ",
                                    Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(AddMemberActivity.this,
                                    MemberActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(
                                    AddMemberActivity.this,
                                    "Problem with Adding please Try again ",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(
                                AddMemberActivity.this,
                                "Ntid  Already Exists Please try another Ntid ",
                                Toast.LENGTH_LONG).show();

                    }
                }
            }
        });
    }
}
