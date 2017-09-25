package futsal.dev.yangadha.alpha;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Classes.DbModel;
import futsal.dev.yangadha.R;

/**
 * Created by Hendra on 09/05/2017.
 */
public class Controller_SparingList extends Activity {
    private List<DbModel> dbModels = new ArrayList<>();
    PopupWindow mypopup;
    ProgressDialog pDialog;

    private void getDataOff(){
        dbModels.add(new DbModel("Ngapak FC", "3.5",null, null));
        dbModels.add(new DbModel("Mitra Kekar", "4.5",null, null));
        dbModels.add(new DbModel("Ridho FC", "5",null, null));
        dbModels.add(new DbModel("Real Ngapak", "4.0",null, null));
        dbModels.add(new DbModel("Barcepu FC", "3.5",null, null));
        dbModels.add(new DbModel("Naga FC", "2.5",null, null));
        dbModels.add(new DbModel("Ngapak ", "3.5",null, null));
        dbModels.add(new DbModel("Mitra Kekar", "4.5",null, null));
        dbModels.add(new DbModel("Ridho FC", "5",null, null));
        dbModels.add(new DbModel("Real Ngapak", "4.0",null, null));
        dbModels.add(new DbModel("Barcepu FC", "3.5",null, null));
        dbModels.add(new DbModel("Naga FC", "2.5",null, null));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sparing_layout);
        getDataOff();
        final GridView gridView = (GridView) findViewById(R.id.grid_view_sparing);
        gridView.setAdapter(new ListAdapter() {
            @Override
            public boolean areAllItemsEnabled() {
                return true;
            }

            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return dbModels.size();
            }

            @Override
            public Object getItem(int position) {
                return dbModels.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) Controller_SparingList.this.getSystemService(
                        Controller_SparingList.this.LAYOUT_INFLATER_SERVICE);
                View gridview;
                gridview = inflater.inflate(R.layout.item_team_sparing,null);
                TextView nm_team = (TextView)gridview.findViewById(R.id.sparing_teamName);
                nm_team.setText((CharSequence) dbModels.get(position).getPrm1());

                RatingBar rateTeam = (RatingBar)gridview.findViewById(R.id.sparing_teamRate);
                rateTeam.setRating(Float.parseFloat(dbModels.get(position).getPrm2()));
                return gridview;

            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View customPopup = findViewById(R.layout.myteamdetail);
                mypopup = new PopupWindow(customPopup,200, 200);
//                pDialog = new ProgressDialog(Controller_SparingList.this);
//                pDialog.setMessage(dbModels.get(position).getPrm1());
//                pDialog.show();
                mypopup.showAtLocation(gridView,0,0,0);
            }
        });
    }
}
