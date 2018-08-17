package com.company.antoine.mynews;


import com.company.antoine.mynews.Models.TimesArticleAPI;
import com.company.antoine.mynews.Utils.NYTimesStreams;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.hamcrest.MatcherAssert.assertThat;

public class ViewPagerFragmentTest {

    @Test
    public void streamFetchTimesMostPopular() throws Exception {
        Observable<TimesArticleAPI> observableMost = NYTimesStreams.streamFetchTimesMostPopular("Technology");
        TestObserver<TimesArticleAPI> testObserver = new TestObserver<>();
        observableMost.subscribeWith(testObserver)
                .assertNoErrors() // 3.1 - Check if no errors
                .assertNoTimeout() // 3.2 - Check if no Timeout
                .awaitTerminalEvent();// 3.3 - Await the stream terminated before continue

        TimesArticleAPI timesAPI = testObserver.values().get(0);
        assertThat("API status: ", timesAPI.getStatus().equals("OK"));
    }

    @Test
    public void streamFetchTimesTopStories() throws Exception {
        Observable<TimesArticleAPI> observableMost = NYTimesStreams.streamFetchTimesTopStories("home");
        TestObserver<TimesArticleAPI> testObserver = new TestObserver<>();
        observableMost.subscribeWith(testObserver)
                .assertNoErrors() // 3.1 - Check if no errors
                .assertNoTimeout() // 3.2 - Check if no Timeout
                .awaitTerminalEvent();// 3.3 - Await the stream terminated before continue

        TimesArticleAPI timesAPI = testObserver.values().get(0);
        assertThat("API status: ", timesAPI.getStatus().equals("OK"));
    }
}