package futsal.dev.yangadha.alpha;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import futsal.dev.yangadha.R;


public class SignUp_Activity extends FragmentActivity implements ActionBar.TabListener {

    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ViewPager mViewPager;
    public static HashMap<String, String> params = new HashMap();
    LinearLayout dotsLayout;
    static RadioButton footSelected;
    static String birthdate = "";
    static ImageButton buttonChoose;
    static DatePicker datePicker;
    static CircleImageView imageView;
    Bitmap bitmap;
    static String foot;
    int PICK_IMAGE_REQUEST = 1;
    private String KEY_USERNAME = "username",KEY_EMAIL = "email"
            ,KEY_PASSWORD = "password", KEY_IMAGE = "image"
            , KEY_NAME = "name", KEY_FOOT = "foot", KEY_HEIGHT = "height"
            ,KEY_BIRTHDATE = "birthdate", KEY_WEIGHT = "weight";
    static RadioGroup radioFoot;
    static EditText signup_email, signup_height
            , signup_name, signup_password
            , signup_username, signup_weight;

    public static void addParameterToSignUp(String Key, String Value)
    {
        params.put(Key, Value);
    }

    private void Register() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SignUp_Activity.this, "Fetching Data", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(SignUp_Activity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... para) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequst(Config.URL_SIGNUP,params);
                return s;

            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void addBottomDot(int paramInt)
    {
        if (dotsLayout != null) {
            dotsLayout.removeAllViews();
        }
        TextView[] dot = new TextView[3];
        for (int i = 0; i < 3; i++)
        {
            dot[i] = new TextView(this);
            dot[i].setText(Html.fromHtml("&#8226;"));
            dot[i].setTextSize(35.0F);
            dot[i].setTextColor(getResources().getInteger(R.color.dot_inactive));
            dotsLayout.addView(dot[i]);
        }
        if (dot.length > 0) {
            dot[paramInt].setTextColor(getResources().getInteger(R.color.dot_active));
        }
    }

    private void addToParams()
    {
        addParameterToSignUp(KEY_USERNAME, signup_username.getText().toString());
        addParameterToSignUp(KEY_EMAIL, signup_email.getText().toString());
        addParameterToSignUp(KEY_PASSWORD, signup_password.getText().toString());
        Log.d("add to param", " : user password success");
        addParameterToSignUp(KEY_NAME, signup_name.getText().toString());
        addParameterToSignUp(KEY_WEIGHT, signup_weight.getText().toString());
        addParameterToSignUp(KEY_HEIGHT, signup_height.getText().toString());
        addParameterToSignUp(KEY_FOOT, foot);
        addParameterToSignUp(KEY_BIRTHDATE, birthdate);
        addParameterToSignUp(this.KEY_IMAGE, getStringImage(this.bitmap));
        Log.d("add to param", " : image ");
    }

    private void showFileChooser()
    {
        Intent localIntent = new Intent();
        localIntent.setType("image/*");
        localIntent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(Intent.createChooser(localIntent, "Select Picture"), this.PICK_IMAGE_REQUEST);
    }

    public String getStringImage(Bitmap paramBitmap)
    {
        if (paramBitmap == null){
            return "";
        }else {
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            paramBitmap.compress(Bitmap.CompressFormat.JPEG, 100, localByteArrayOutputStream);
            return Base64.encodeToString(localByteArrayOutputStream.toByteArray(), 0);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Uri localUri = null;
        if ((requestCode == this.PICK_IMAGE_REQUEST) && (resultCode == -1) && (data != null) && (data.getData() != null)) {
            localUri = data.getData();
        }
        try
        {
            this.bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), localUri);
            imageView.setImageBitmap(this.bitmap);

            return;
        }
        catch (IOException localIOException)
        {
            localIOException.printStackTrace();
        }
    }



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        dotsLayout = (LinearLayout) findViewById(R.id.dot);
        addBottomDot(0);
        final Button buttonnext = (Button)findViewById(R.id.btn_next);
        buttonnext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                addBottomDot(position);
                if (position == 2) {
                    buttonnext.setText("REGISTER");
                    int selected = radioFoot.getCheckedRadioButtonId();
                    if (selected == 0){
                        Toast.makeText(SignUp_Activity.this, "Please Select Foot", Toast.LENGTH_LONG).show();
                    }else{
                        footSelected = (RadioButton) findViewById(selected);
                        foot = footSelected.getText().toString();
                    }
                    String Day= "", Month = "";
                    int bln = 1 + datePicker.getMonth();
                    int hr = datePicker.getDayOfMonth();
                    if (hr < 10) {
                        Day = "0" + String.valueOf(hr);
                    }
                    if (bln < 10) {
                        Month = "0" + String.valueOf(bln);
                    }
                    else {
                        Day   = String.valueOf(hr);
                        Month = String.valueOf(bln);
                    }
                    birthdate = String.valueOf(datePicker.getYear()) + Day + Month;
                    buttonnext.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            addToParams();
                            Register();
                        }
                    });
                    buttonChoose.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v) {
                            showFileChooser();
                        }
                    });
                }else{
                    buttonnext.setText("NEXT");
                    buttonnext.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
                        }
                    });
                }

            }
        });
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }
    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new fragment_signup_1();
                case 1:
                    return new fragment_signup_2();
                default:
                    return new fragment_signup_uploadfoto();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Section " + (position + 1);
        }
    }


    public static class fragment_signup_1 extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View localView = inflater.inflate(R.layout.fragment_signup_1, container, false);
            signup_username = (EditText)localView.findViewById(R.id.signup_username);
            signup_email = (EditText)localView.findViewById(R.id.signup_email);
            signup_password = (EditText)localView.findViewById(R.id.signup_password);
            return localView;
        }
    }

    public static class fragment_signup_2 extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View localView = inflater.inflate(R.layout.fragment_signup_2, container, false);
            datePicker    = (DatePicker)localView.findViewById(R.id.datePicker);
            datePicker.setCalendarViewShown(false);
            signup_name   = (EditText)localView.findViewById(R.id.signup_name);
            signup_height = (EditText)localView.findViewById(R.id.signup_tinggibadan);
            signup_weight = (EditText)localView.findViewById(R.id.signup_beratbadan);
            radioFoot = (RadioGroup)localView.findViewById(R.id.radio_foot);
            return localView;
        }
    }

    public static class fragment_signup_uploadfoto extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View localView = inflater.inflate(R.layout.fragment_signup_uploadfoto, container, false);
            buttonChoose = (ImageButton)localView.findViewById(R.id.btn_choose);
            imageView = (CircleImageView)localView.findViewById(R.id.PhotoProfile);
            return localView;
        }
    }
}
