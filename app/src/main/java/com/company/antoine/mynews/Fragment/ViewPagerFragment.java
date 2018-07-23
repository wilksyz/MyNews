package com.company.antoine.mynews.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.antoine.mynews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragment extends Fragment {

    private static final String KEY_POSITION="position";

    public ViewPagerFragment() { }

    public static ViewPagerFragment newInstance(int position){

        ViewPagerFragment view = new ViewPagerFragment();

        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        view.setArguments(args);
        return view;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_view_pager, container, false);
        int position = getArguments().getInt(KEY_POSITION, -1);
        TextView message = result.findViewById(R.id.fragment_text);

        message.setText("page "+position);


        return result;
    }

}
