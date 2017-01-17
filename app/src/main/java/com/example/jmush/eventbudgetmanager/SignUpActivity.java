package com.example.jmush.eventbudgetmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by jmush on 12/8/16.
 */
public class SignUpActivity extends Activity {
    EditText edittextEmail, edittextName, editTextPassword,
            editTextConfirmPassword;
    Button register;

    DBAdapter dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

        // // get Instance of Database Adapter
        dbh = new DBAdapter(this);

        // Get References of Views
        edittextName = (EditText) findViewById(R.id.edittextname);
        edittextEmail = (EditText) findViewById(R.id.edittextemail);
        editTextPassword = (EditText) findViewById(R.id.edittextpword);
        editTextConfirmPassword = (EditText) findViewById(R.id.edittextcpword);

        register = (Button) findViewById(R.id.btnregister);
        register.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                String Name = edittextName.getText().toString();
                String Email = edittextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();

                // check if any of the fields are vaccant
                if (Name.equals("") || Email.equals("") || password.equals("") || confirmPassword.equals("")) {
                    Toast.makeText(SignUpActivity.this, "Field Vaccant",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                // check if both password matches
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(),
                            "Password does not match", Toast.LENGTH_LONG)
                            .show();
                    return;
                } else {
                    int exst = dbh.ExistsEmail(Email);
                    // Save the Data in Database
                    if (exst == 0) {
                        long yes = dbh.insertDetails(Name, Email, password);
                        if (yes != 0) {
                            Toast.makeText(SignUpActivity.this,
                                    "Account Successfully Created ",
                                    Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignUpActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Problem with Registration please Try again ",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(
                                getApplicationContext(),
                                "Email ID Already Exists Please try another Email ID or Login with Existing ",
                                Toast.LENGTH_LONG).show();

                    }
                }
            }
        });
    }
}
