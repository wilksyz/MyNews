package com.company.antoine.mynews;




import com.company.antoine.mynews.Models.TimesArticleAPI;
import com.company.antoine.mynews.Utils.NYTimesService;
import com.company.antoine.mynews.Utils.NYTimesStreams;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.QueryMap;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)

public class ExampleUnitTest {
    @Before
    public void setup(){
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @Test
    public void streamFetchTimesTopStories() throws Exception {

        TimesArticleAPI timesAPI = NYTimesStreams.streamFetchTimesTopStories("home").blockingFirst();
        assertThat("API status: ", timesAPI.getStatus().equals("OK"));
    }

    @Test
    public void streamFetchTimesMostPopular() throws Exception {
        TimesArticleAPI timesAPI = NYTimesStreams.streamFetchTimesMostPopular("Technology").blockingFirst();
        assertThat("API status: ", timesAPI.getStatus().equals("OK"));
    }

    @Test
    public void streamFetchTimesSearchArticle() throws Exception {
        Map<String,String> queryData = new HashMap<>();
        queryData.put("q", "bmw");
        queryData.put("fq", "news_desk: Business Day");
        queryData.put("begin_date", "20181016");
        queryData.put("end_date", "20181017");

        Observable<TimesArticleAPI> observableSearch = NYTimesStreams.streamFetchTimesArticleSearch(queryData);
        TestObserver<TimesArticleAPI> testObserver = new TestObserver<>();
        observableSearch.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        TimesArticleAPI timesAPI = testObserver.values().get(0);
        assertThat("API status: ", timesAPI.getStatus().equals("OK"));
    }
}