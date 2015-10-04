package darroo.com.PCall.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import darroo.com.PCall.R;

/**
 * Created by tangyangkai on 2015/7/27.
 * 我的Fragment
 */
public class PersonFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_person, container, false);
    }
}
