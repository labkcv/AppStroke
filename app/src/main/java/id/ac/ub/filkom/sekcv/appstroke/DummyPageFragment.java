package id.ac.ub.filkom.sekcv.appstroke;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by syafiq on 8/10/16.
 */
public class DummyPageFragment extends Fragment
{
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static DummyPageFragment newInstance(int page)
    {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        DummyPageFragment fragment = new DummyPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.mainpage_viewpager_diagnose, container, false);
        return view;
    }
}
