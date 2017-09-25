package futsal.dev.yangadha.alpha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import futsal.dev.yangadha.R;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textViewProfileName, textViewProfileEmail, textViewProfileTeam;
    ImageView menu;
    Integer id;
    String url;
    CircularImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile_layout);

        menu    = (ImageView)findViewById(R.id.edit_profile);

        textViewProfileName = (TextView)findViewById(R.id.user_profile_name);
        textViewProfileEmail= (TextView)findViewById(R.id.user_profile_email);
        textViewProfileTeam = (TextView)findViewById(R.id.user_profile_team);
        imageView   = (CircularImageView)findViewById(R.id.user_profile_photo);

        textViewProfileName.setText(SharedPrefManager.getInstance(this).getFullName());
        textViewProfileEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());
        textViewProfileTeam.setText(SharedPrefManager.getInstance(this).getUserTeam());

        id  = SharedPrefManager.getInstance(this).getUserId();

        url = "http://rasyid.esy.es/images/profil/"+id+".png";
        Log.e("Tes",url);
        Picasso.with(this)
                .load("http://rasyid.esy.es/images/profil/"+id+".png")
                .placeholder(R.mipmap.ic_launcher) // can also be a drawable
                .error(R.mipmap.ic_marker)
                .into(imageView);

        menu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == menu)
        {
            Intent intentEditProfile = new Intent(ProfileActivity.this, Edit_Profile_User.class);
            startActivity(intentEditProfile);
            ProfileActivity.this.finish();
        }
    }
}
