package futsal.dev.yangadha.alpha;

/**
 * Created by Hendra on 03/05/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


import futsal.dev.yangadha.R;

public class SplashscreenActivity extends Activity{
    private static int splashinterval = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splashscreen_layout);
        StartAnimation();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashscreenActivity.this, LoginActivity.class);
                startActivity(i);

                SplashscreenActivity.this.finish();
            }


        }, splashinterval);


    }
    private void StartAnimation(){
        Animation animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade);
        animFadeIn.reset();
        ImageView iv = (ImageView) findViewById(R.id.yangadha_dev);
        iv.clearAnimation();
        iv.startAnimation(animFadeIn);
    }

}
