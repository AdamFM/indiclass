package com.apps.indiclass.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.apps.indiclass.LoginActivity;
import com.apps.indiclass.MainActivity;

import java.util.HashMap;


public class SessionManager {
	// Shared Preferences
	SharedPreferences pref;
	
	// Editor for Shared preferences
	Editor editor;
	
	// Context
	Context _context;
	
	// Shared pref mode
	int PRIVATE_MODE = 0;
	
	// Sharedpref file name
	private static final String PREF_NAME = "MyClass";

	// User name (make variable public to access from outside)
	public static final String KEY_TOKEN = "token";

	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";
	
	// User name (make variable public to access from outside)
	public static final String KEY_NAME = "nama";
	
	// Email address (make variable public to access from outside)
	public static final String KEY_JENI = "jenjang";
	
	// Email address (make variable public to access from outside)
	public static final String KEY_IDUS = "iduser";

	// Email address (make variable public to access from outside)
	public static final String KEY_IMGS = "img";

	// Email address (make variable public to access from outside)
	public static final String KEY_EMAI = "email";

	// Email address (make variable public to access from outside)
	public static final String KEY_TYPE = "TYPE";

	// Email address (make variable public to access from outside)
	public static final String KEY_GOOGLE = "GOOGLE";
	// Email address (make variable public to access from outside)
	public static final String KEY_STAS = "STAS";

	public static final String KEY_CLASSID = "CLASSID";

    public static final String KEY_KIDSID = "KIDSID";

    public static final String KEY_KIDSJENI = "kidsjenjang";

    public static final String KEY_KIDSJNAME = "kidsjenjangname";

	// Constructor
	public SessionManager(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	public String getJenjang(){
		String user = pref.getString(KEY_JENI, "0");
		return user;
	}

	public String getJenjangKid(){
		String user = pref.getString(KEY_KIDSJENI, "0");
		return user;
	}

	public String getJenjangNameKid(){
		String user = pref.getString(KEY_KIDSJNAME, "0");
		return user;
	}

	public String getUserType(){
		String type = pref.getString(KEY_TYPE, null);
		return type;
	}

	public String getIds(){
		String id = pref.getString(KEY_IDUS, "0");
		return id;
	}

	public String getTokenFcm(){
		String token = pref.getString("tokenfcm", null);
		return token;
	}

	public void createTokenFcm(String tokenfcm) {
		editor.putString("tokenfcm", tokenfcm);
		editor.commit();
	}

	public void updateIMGS(String tokenfcm) {
		editor.putString(KEY_IMGS, tokenfcm);
		editor.commit();
	}

	public String getStas(){
		String token = pref.getString(KEY_STAS, "0");
		return token;
	}

	public void createClassId(int tokenfcm) {
		editor.putInt(KEY_CLASSID, tokenfcm);
		editor.commit();
	}

	public int getKeyClassid(){
		int token = pref.getInt(KEY_STAS, 0);
		return token;
	}

	public String getToken(){
		return pref.getString(KEY_TOKEN, "");
	}

	public void createJenjang(String tokenfcm) {
		editor.putString(KEY_JENI, tokenfcm);
		editor.commit();
	}

	public void createKidsSession(String kidsid, String kidsjen, String kidsimg, String kidsjname) {

        // Storing jenjang in pref
        editor.putString(KEY_KIDSID, kidsid);

        // Storing jenjang in pref
        editor.putString(KEY_KIDSJENI, kidsjen);

        // Storing jenjang in pref
        editor.putString(KEY_KIDSJNAME, kidsjname);

        // Storing jenjang in pref
        editor.putString(KEY_IMGS, kidsimg);

        // commit changes
        editor.commit();

		// user is not logged in redirect him to Login Activity
		Intent i = new Intent(_context, MainActivity.class);
		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		// Staring Login Activity
		_context.startActivity(i);
    }

	/**
	 * Create login session
	 * */
	public void createLoginSession(String token, String nama, String email, String iduser, String img, String jenjang, String type, String google, String stuas){
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);

		// Storing nama in pref
		editor.putString(KEY_TOKEN, token);
		
		// Storing nama in pref
		editor.putString(KEY_NAME, nama);
		
		// Storing email in pref
		editor.putString(KEY_EMAI, email);
		
		// Storing iduser in pref
		editor.putString(KEY_IDUS, iduser);

		// Storing img in pref
		editor.putString(KEY_IMGS, img);

		// Storing jenjang in pref
		editor.putString(KEY_JENI, jenjang);

		// Storing jenjang in pref
		editor.putString(KEY_TYPE, type);

		// Storing jenjang in pref
		editor.putString(KEY_GOOGLE, google);

		// Storing jenjang in pref
		editor.putString(KEY_STAS, stuas);

		// commit changes
		editor.commit();
	}	
	
	/**
	 * Check login method wil check user login status
	 * If false it will redirect user to login page
	 * Else won't do anything
	 * */
	public void checkLogin(){
		// Check login status
		if(!this.isLoggedIn()){
			
			// user is not logged in redirect him to Login Activity
			Intent i = new Intent(_context, LoginActivity.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			
			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			// Staring Login Activity
			_context.startActivity(i);
		}
		
	}
	
	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> user = new HashMap<String, String>();
		// user name
		user.put(KEY_NAME, pref.getString(KEY_NAME, ""));
		
		// user email id
		user.put(KEY_EMAI, pref.getString(KEY_EMAI, ""));

		// user id user
		user.put(KEY_IDUS, pref.getString(KEY_IDUS, ""));

		// user img
		user.put(KEY_IMGS, pref.getString(KEY_IMGS, ""));

		// user jenjang
		user.put(KEY_JENI, pref.getString(KEY_JENI, "0"));

		// user jenjang
		user.put(KEY_GOOGLE, pref.getString(KEY_GOOGLE, ""));

        // user jenjang
        user.put(KEY_KIDSID, pref.getString(KEY_KIDSID, "0"));

        // user jenjang
        user.put(KEY_KIDSJENI, pref.getString(KEY_KIDSJENI, "0"));

		// return user
		return user;
	}
	
	/**
	 * Clear session details
	 * */
	public void logoutUser(){
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();
		
		// After logout redirect user to Loing Activity
		Intent i = new Intent(_context, LoginActivity.class);
		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		// Staring Login Activity
		_context.startActivity(i);
	}
	
	/**
	 * Quick check for login
	 * **/
	// Get Login State
	public boolean isLoggedIn(){
		return pref.getBoolean(IS_LOGIN, false);
	}
}
