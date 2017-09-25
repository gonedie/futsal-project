package futsal.dev.yangadha.alpha;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import futsal.dev.yangadha.R;

/**
 * Created by bima on 11/06/17.
 */

public class Edit_Profile_User extends AppCompatActivity implements View.OnClickListener{
    Integer id;
    EditText editNamaUser, editEmailUser, editTeamUser;
    Button btnEditUser;
    CircularImageView imageView;
    Bitmap bitmap;
    int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        id  = SharedPrefManager.getInstance(this).getUserId();

        btnEditUser  = (Button)  findViewById(R.id.btnUpdateUser);
        editNamaUser = (EditText)findViewById(R.id.editNamaUser);
        editEmailUser= (EditText)findViewById(R.id.editEmailUser);
        editTeamUser = (EditText)findViewById(R.id.editTeamUser);
        imageView    = (CircularImageView)findViewById(R.id.UpdatePhotoProfile);

        editNamaUser.setText(SharedPrefManager.getInstance(this).getFullName());
        editEmailUser.setText(SharedPrefManager.getInstance(this).getUserEmail());
        editTeamUser.setText(SharedPrefManager.getInstance(this).getUserTeam());
        imageView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                showFileChooser();
            }
        });


        btnEditUser.setOnClickListener(this);
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

    private void updateProfile(){
        final String nama   = editNamaUser.getText().toString().trim();
        final String email  = editEmailUser.getText().toString().trim();
        final String team   = editTeamUser.getText().toString().trim();

        class updateProfile extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Edit_Profile_User.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(Edit_Profile_User.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();


                hashMap.put(Config.KEY_USER_ID,String.valueOf(id));
                hashMap.put(Config.KEY_USER_NAME,nama);
                hashMap.put(Config.KEY_USER_EMAIL,email);
                hashMap.put(Config.KEY_USER_TEAM,team);
                hashMap.put("image",getStringImage(bitmap));

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequst(Config.URL_UPDATE_USER,hashMap);

                return s;

            }
        }

        updateProfile up = new updateProfile();
        up.execute();
        SharedPrefManager.getInstance(getApplicationContext()).update(nama,email,team);
    }


    @Override
    public void onClick(View v) {
        if (v == btnEditUser){

            updateProfile();

            Intent intentProfile = new Intent(Edit_Profile_User.this, ProfileActivity.class);
            startActivity(intentProfile);
            Edit_Profile_User.this.finish();
        }
    }
}
