package futsal.dev.yangadha.alpha;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.widget.TextView;

import futsal.dev.yangadha.R;


public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.find_futsal,
            R.drawable.competition,
            R.drawable.futsal_field_list,
            R.drawable.my_profile,
            R.drawable.my_team,
            R.drawable.ratting,
            R.drawable.sparing,
            R.drawable.about_us,
            R.drawable.setting,
    };

    public String[] name = {
            "Find Futsal",
            "Turnament",
            "Stadium List",
            "My Profile",
            "My Team",
            "Ranking",
            "Sparing",
            "Setting",
            "About"
    };

    // Constructor
    public ImageAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount()
    {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position)
    {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View gridview;

        gridview = inflater.inflate(R.layout.item,null);
        ImageView imageView = (ImageView)gridview.findViewById(R.id.iconmenu);

        imageView.setImageResource(mThumbIds[position]);

        TextView nameMenu = (TextView)gridview.findViewById(R.id.menu_name);
        nameMenu.setText(name[position]);

        return gridview;
    }
}