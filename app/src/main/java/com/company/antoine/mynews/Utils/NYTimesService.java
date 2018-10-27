package com.company.antoine.mynews.Utils;

import com.company.antoine.mynews.Models.TimesArticleAPI;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface NYTimesService {

    //Prepare the link of API request
    @GET("search/v2/articlesearch.json?api-key=d6c26d2178d54e9f9de2f60d96152da0&sort=newest&fl=web_url,pub_date,multimedia,headline")
    Observable<TimesArticleAPI> articleSearchTerm(@QueryMap Map<String, String> researchValue);

    @GET("mostpopular/v2/mostviewed/{id}/7.json?api-key=d6c26d2178d54e9f9de2f60d96152da0")
    Observable<TimesArticleAPI> mostPopularId(@Path(value = "id") String id);

    @GET("topstories/v2/{section}.json?api-key=d6c26d2178d54e9f9de2f60d96152da0")
    Observable<TimesArticleAPI> topStoriesSection(@Path(value = "section") String section);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}