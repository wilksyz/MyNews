package com.company.antoine.mynews.Utils;

import com.company.antoine.mynews.Models.TimesArticleAPI;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NYTimesStreams {

    public static Observable<TimesArticleAPI> streamFetchTimesMostPopular(String id){
        NYTimesService nytimesService = NYTimesService.retrofit.create(NYTimesService.class);
        return nytimesService.mostPopularId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<TimesArticleAPI> streamFetchTimesTopStories(String id){
        NYTimesService nytimesService = NYTimesService.retrofit.create(NYTimesService.class);
        return nytimesService.topStoriesId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
