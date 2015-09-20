package swag.swag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

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
            surfaceView.refresh();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_WORD, Parcels.wrap(surfaceView.getStrokes()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.fragment_main, container, false);

        surfaceView = new MySurfaceView(getActivity());
        root.addView(surfaceView);

        if(savedInstanceState!=null) {
            Word w = Parcels.unwrap(savedInstanceState.getParcelable(KEY_WORD));
            surfaceView.setStrokes(w);
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
