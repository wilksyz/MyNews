package com.company.antoine.mynews.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.company.antoine.mynews.Models.TimesArticleAPI;
import com.company.antoine.mynews.R;
import com.company.antoine.mynews.Utils.NYTimesStreams;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragment extends Fragment {

    private static final String KEY_POSITION="position";
    Disposable disposable ;
    Disposable disposables ;


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

        if (position == 0){
            executeHttpRequestMostPopular();
        }
        if (position == 1){
            executeHttpRequestTopStories();
        }
        if (position == 2){

        }

        return result;
    }

    private void executeHttpRequestMostPopular(){
        this.disposable = NYTimesStreams.streamFetchTimesMostPopular("Technology").subscribeWith(new DisposableObserver<TimesArticleAPI>() {
            @Override
            public void onNext(TimesArticleAPI mostPopular) {
                Log.e("TAG","On Next: "+mostPopular.toString());
                // 1.3 - Update UI with list of users

            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","On Error",e);
            }

            @Override
            public void onComplete() {
                Log.e("TAG","On Complete !!");
            }
        });
    }

    private void executeHttpRequestTopStories(){
        this.disposables = NYTimesStreams.streamFetchTimesTopStories("automobiles").subscribeWith(new DisposableObserver<TimesArticleAPI>() {
            @Override
            public void onNext(TimesArticleAPI topStoriesAPI) {
                Log.e("TAG","On Next Top: "+topStoriesAPI.toString());
                // 1.3 - Update UI with list of users
                Toast.makeText(getContext(),topStoriesAPI.getStatus(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","On Error Top "+Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG","On Complete top!!");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposeOnDestroy();
    }

    private void disposeOnDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
        if (this.disposables != null && !this.disposables.isDisposed()) this.disposables.dispose();
    }


}
