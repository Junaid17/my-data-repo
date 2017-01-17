package com.example.jmush.eventbudgetmanager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jmush on 12/8/16.
 */
public class LoginActivity extends Activity {

        // Email, password edit text

    public EditText txtUsername, txtPassword;
    public SessionManager session;
    public TextView btnLogin, Register;
    public DBAdapter dbh;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signin);

            dbh = new DBAdapter(LoginActivity.this);
            txtUsername = (EditText) findViewById(R.id.email);
            txtPassword = (EditText) findViewById(R.id.password);
            btnLogin = (TextView) findViewById(R.id.btnLogin);
            Register = (TextView) findViewById(R.id.btnLinkToRegisterScreen);
            session = new SessionManager(LoginActivity.this);



            Register.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                    startActivity(i);
                }
            });
            // Login button click event
            btnLogin.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    String email = txtUsername.getText().toString();
                    String password = txtPassword.getText().toString();

                    if (email.trim().length() > 0 && password.trim().length() > 0) {
                        Cursor logcheck = dbh.Login(email, password);
                         if (logcheck.getCount() > 0) {
                            session.createLoginSession(email, password);
                            Toast.makeText(LoginActivity.this,
                                    "Login Successfull  ",
                                    Toast.LENGTH_LONG).show();
                             if (logcheck != null)
                                 logcheck.moveToFirst();
                             String name=logcheck.getString(logcheck.getColumnIndex("NAME"));

                             logcheck.close();

                            Intent intent = new Intent(LoginActivity.this,
                                    MainActivity.class);
                             intent.putExtra("name",name);
                            startActivity(intent);
                            finish();

                        }
                         else  {
                             Toast.makeText(LoginActivity.this, "Email/Password is incorrect)", Toast.LENGTH_LONG).show();
                         }
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Please enter Email and password)", Toast.LENGTH_LONG).show();
                    }

                }




            });

        }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            finish();
        }
    }


