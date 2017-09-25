package futsal.dev.yangadha.alpha;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import futsal.dev.yangadha.R;

public class Detail_Competition_Activity extends AppCompatActivity {

    private ImageView imageViewTurnamen;
    String id,url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__competition_);

        imageViewTurnamen = (ImageView) findViewById(R.id.img_turnamen);

        Intent intent = getIntent();
        id  = intent.getStringExtra(Config.TURNAMEN_ID);
        url = "http://rasyid.esy.es/images/"+id+".jpg";
        Log.e("Tes",url);
        Picasso.with(this)
                .load("http://rasyid.esy.es/images/"+id+".jpg")
                .placeholder(R.mipmap.ic_launcher) // can also be a drawable
                .error(R.mipmap.ic_marker)
                .into(imageViewTurnamen);
    }
}
