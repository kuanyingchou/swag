package swag.swag;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private MySurfaceView surfaceView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.fragment_main, container, false);

        surfaceView = new MySurfaceView(getActivity());
        root.addView(surfaceView);
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
