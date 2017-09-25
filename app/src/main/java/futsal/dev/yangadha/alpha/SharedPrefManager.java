package futsal.dev.yangadha.alpha;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bima on 25/05/17.
 */

//Store User data
public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME    = "mysharedpref";
    private static final String KEY_USERNAME        = "username";
    private static final String KEY_USER_EMAIL      = "user_email";
    private static final String KEY_USER_ID         = "id_user";
    private static final String KEY_USER_FULLNAME   = "nama_user";
    private static final String KEY_USER_TEAM       = "team_user";
    private static final String KEY_USER_AGE        = "age_user";


    private SharedPrefManager(Context context){
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(int id, String username, String email, String nama, String team, int age){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor     = sharedPreferences.edit();

        editor.putInt(KEY_USER_ID, id);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_USER_FULLNAME, nama);
        editor.putString(KEY_USER_TEAM, team);
        editor.putInt(KEY_USER_AGE, age);

        editor.apply();

        return true;
    }

    public boolean update(String nama, String email,  String team){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor     = sharedPreferences.edit();

        editor.putString(KEY_USER_FULLNAME, nama);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_TEAM, team);

        editor.commit();

        return true;
    }

    //Check user Login
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USERNAME,null) != null){
            return true;
        }
        return false;
    }

    //Check user Logout
    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();

        return true;
    }

    public String getUsername(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME,null);
    }

    public String getFullName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_FULLNAME,null);
    }

    public String getUserTeam(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_TEAM,null);
    }

    public String getUserEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL,null);
    }

    public Integer getUserId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_ID,0);
    }

}
