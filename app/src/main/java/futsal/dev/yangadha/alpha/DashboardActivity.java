package futsal.dev.yangadha.alpha;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import futsal.dev.yangadha.R;


public class DashboardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dashboard_layout);

        GridView gridView = (GridView) findViewById(R.id.grid_view);

//		Instance of ImageAdapter Class
        gridView.setAdapter(new ImageAdapter(this));

//		 On Click event for Single Gridview Item
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Intent i = new Intent(getApplicationContext(), ControllerMenuActivity.class);
//		passing array index
                i.putExtra("id", position);
                startActivity(i);
            }
        });
    }
}
