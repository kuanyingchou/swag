package swag.swag;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;

import swag.swag.data.WordContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class BrowseActivityFragment extends Fragment {

    private static Gson gson = new Gson();

    public BrowseActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_browse, container, false);
        GridView view = (GridView) root.findViewById(R.id.browse_grid);

        ImageButton btn = (ImageButton)view.findViewById(R.id.refresh_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        WordSqlHelper helper = new WordSqlHelper(getActivity());
        SQLiteDatabase db = helper.getReadableDatabase();
        String orderBy = WordContract.WordEntry.COLUMN_DATE + " DESC";
        Cursor c = db.query(WordContract.WordEntry.TABLE_NAME, null, null, null, null, null, orderBy);
//Log.d(">>>>", c.getCount()+"");
        view.setAdapter(new WordAdapter(getActivity(), c, 0));
        return root;
    }

    Paint paint = new Paint();

    private class WordAdapter extends CursorAdapter {
        public WordAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
//            ImageView view = new ImageView(context);
//            view.setImageDrawable(getResources().getDrawable(R.drawable.abc_btn_radio_material));
//            return view;
            return new ImageView(context);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            long id = cursor.getLong(
                    cursor.getColumnIndex(WordContract.WordEntry._ID));

            File f = new File(context.getFilesDir(), id+".png");
            if(f.exists()) {
                Picasso.with(getActivity()).load(f).into((ImageView)view);
            }


        }
    }
}
