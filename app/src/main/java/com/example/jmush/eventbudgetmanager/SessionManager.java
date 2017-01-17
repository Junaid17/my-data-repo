package com.example.jmush.eventbudgetmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import java.util.HashMap;

public class SessionManager {

	SharedPreferences pref;
	Editor editor;
	Context context;
	int private_mode = 0;
	private static final String PREF_NAME = "Event";
	private static final String Is_Login = "IsLoggedIn";
	public static final String Key_Name = "name";
	public static final String Key_Email = "email";

	public SessionManager(Context context) {
		this.context = context;
		pref = context.getSharedPreferences(PREF_NAME, private_mode);
		editor = pref.edit();
	}

	public void createLoginSession(String email, String pass) {
		editor.putBoolean(Is_Login, true);
		editor.putString(Key_Name, email);
		editor.putString(Key_Email, pass);
		editor.commit();
	}

    public boolean isLoggedIn() {
        return pref.getBoolean(Is_Login, false);
    }


    public void logoutUser() {
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }













	/*public void checkLogin() {
		if (!this.isLoggedIn()) {
			Intent intent = new Intent(context, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	}*/





	/*public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		user.put(Key_Name, pref.getString(Key_Name, null));
		user.put(Key_Email, pref.getString(Key_Email, null));
		return user;

	}*/

	/*public static void setUserObject(Context c, String userObject, String key) {
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, userObject);
        editor.commit();
    }


	 public static String getUserObject(Context ctx, String key) {
	        SharedPreferences pref = PreferenceManager
	                .getDefaultSharedPreferences(ctx);
	        String userObject = pref.getString(key, null);
	        return userObject;
	    }*/
}
