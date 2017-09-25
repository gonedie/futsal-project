package futsal.dev.yangadha.alpha;

/**
 * Created by bima on 14/05/17.
 */

public class Config {

    //Alamat script CRUD
    public static final String URL_GET_LAPANGAN = "http://rasyid.esy.es/GetLapangan.php";
    public static final String URL_GET_TURNAMEN = "http://rasyid.esy.es/GetTurnamen.php";
    public static final String URL_LOGIN        = "http://rasyid.esy.es/loginUser.php";
    public static final String URL_REGISTER     = "http://rasyid.esy.es/registerUser.php";
    public static final String URL_UPDATE_USER  = "http://rasyid.esy.es/UpdateUser.php";
    public static final String URL_SIGNUP       = "http://rasyid.esy.es/SignUp.php";

    //keys untuk mengirim request ke php
    //TURNAMEN
    public static final String KEY_TURNAMEN_ID      = "id_turnamen";

    public static final String KEY_EMP_ID   = "id";
    public static final String KEY_EMP_NAME = "name";
    public static final String KEY_EMP_DESG = "desg";
    public static final String KEY_EMP_SAL  = "salary";

    //User
    public static final String KEY_USER_ID      = "id_user";
    public static final String KEY_USER_NAME    = "nama_user";
    public static final String KEY_USER_EMAIL   = "email_user";
    public static final String KEY_USER_TEAM    = "team_user";

    //JSON tags
    //TURNAMEN
    public static final String TAG_DESKRIPSI_TURNAMEN       = "description";
    public static final String TAG_NAME_TURNAMEN            = "nama_turnamen";
    public static final String TAG_END_DATE                 = "end_date";
    public static final String TAG_ID_TURNAMEN              = "id_turnamen";

    //LAPANGAN
    public static final String TAG_NAMA_LAP     = "nama_lap";
    public static final String TAG_ALAMAT_LAP   = "alamat_lap";
    public static final String TAG_RATING_LAP   = "rating_lap";
    public static final String TAG_FOTO_LAP   = "foto_lap";

    public static final String TAG_JSON_ARRAY   = "result";


    //id untuk intent
    public static final String EMP_ID           = "emp_id";
    public static final String TURNAMEN_ID      = "turnamen_id";
    public static final String TURNAMEN_NAME    = "turnamen_name";
}
