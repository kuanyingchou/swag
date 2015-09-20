package swag.swag;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.gson.Gson;

import org.parceler.Parcels;

import java.io.File;
import java.util.Calendar;

import swag.swag.data.WordContract;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    public static final String KEY_WORD = "word";

    private MySurfaceView surfaceView;

    public MainActivityFragment() {
        setHasOptionsMenu(true);
    }

    //[ options
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_refresh) {
            surfaceView.reset();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_WORD, Parcels.wrap(surfaceView.getWord()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.fragment_main, container, false);

        final FrameLayout squareContainer = new FrameLayout(getActivity()) {
            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                int size = Math.min(widthMeasureSpec, heightMeasureSpec);
                super.onMeasure(size, size);
            }
        };
        root.addView(squareContainer, 0);

        surfaceView = new MySurfaceView(getActivity());
        squareContainer.addView(surfaceView);
        //root.addView(surfaceView);

        Button saveButton = (Button) root.findViewById(R.id.action_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(surfaceView.getWord());
                WordSqlHelper helper = new WordSqlHelper(getActivity());
                SQLiteDatabase db = helper.getWritableDatabase();

                ContentValues cv = new ContentValues();

                Calendar c = Calendar.getInstance();
                int seconds = c.get(Calendar.SECOND);

                cv.put(WordContract.WordEntry.COLUMN_DATE, seconds);
                cv.put(WordContract.WordEntry.COLUMN_SKETCH, json);

                long id = db.insert(WordContract.WordEntry.TABLE_NAME, null, cv); //TODO: err handling

                //thumbnail
                Bitmap bitmap = Bitmap.createBitmap(192, 192, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                surfaceView.getWord().draw(canvas, new Paint(), 192);
                File file = new File(getActivity().getFilesDir(), id+".png");
                Utility.saveBitmapToFile(bitmap, file);

                //Log.d(">>> ", gson.toJson(surfaceView.getWord()));

            }
        });
        Button browseButton = (Button) root.findViewById(R.id.action_browse);
        browseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BrowseActivity.class);
                startActivity(intent);
            }
        });


        if(savedInstanceState!=null) {
            Word w = Parcels.unwrap(savedInstanceState.getParcelable(KEY_WORD));
            surfaceView.setWord(w);
        }

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        surfaceView.onResumeMySurfaceView();
    }

    @Override
    public void onPause() {
        super.onPause();
        surfaceView.onPauseMySurfaceView();
    }
}
