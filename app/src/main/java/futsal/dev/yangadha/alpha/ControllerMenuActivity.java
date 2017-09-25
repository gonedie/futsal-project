package futsal.dev.yangadha.alpha;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import futsal.dev.yangadha.R;

/**
 * Created by Hendra on 04/05/2017.
 */
public class ControllerMenuActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // get intent data
        Intent i = getIntent();

        // Selected image id
        int position = i.getExtras().getInt("id");
        switch (position){
            case 0 :
                Intent intent = new Intent(ControllerMenuActivity.this, MapsActivity.class);
                startActivity(intent);ControllerMenuActivity.this.finish();
                break;
            case 1 :Intent intentCompetition = new Intent(ControllerMenuActivity.this, Competition_Activity.class);
                startActivity(intentCompetition);ControllerMenuActivity.this.finish();
                break;
            case 2 :
                Intent intenta = new Intent(ControllerMenuActivity.this, Controller_StadiumList.class);
                startActivity(intenta);ControllerMenuActivity.this.finish();
                break;
            case 3 :
                Intent intentProfile = new Intent(ControllerMenuActivity.this, ProfileActivity.class);
                startActivity(intentProfile);ControllerMenuActivity.this.finish();
                break;
            case 4 :Intent intentMyT = new Intent(ControllerMenuActivity.this, Controller_MyTeamList.class);
                startActivity(intentMyT);ControllerMenuActivity.this.finish();
                break;
            case 5 :
                setContentView(R.layout.ranking_layout);
                break;
            case 6 :
                Intent intentSp = new Intent(ControllerMenuActivity.this, Controller_SparingList.class);
                startActivity(intentSp);ControllerMenuActivity.this.finish();
                break;
            case 7 :
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;

            default : setContentView(R.layout.splashscreen_layout); break;
        }


    }
}
