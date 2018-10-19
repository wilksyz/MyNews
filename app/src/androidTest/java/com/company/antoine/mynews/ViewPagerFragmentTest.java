package com.company.antoine.mynews;


import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.company.antoine.mynews.Controlers.Activity.MainActivity;
import com.company.antoine.mynews.Controlers.Activity.SearchArticles;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;



import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ViewPagerFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);



    @Test
    public void checkIfActionBarIsAvailable() {
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
    }

    @Test
    public void checkSearchButtonIsAvailable(){
        onView(withId(R.id.menu_activity_main_search)).perform(click());
    }

    @Test
    public void checkStartSearchActivity(){
        Intents.init();
        onView(withId(R.id.menu_activity_main_search)).perform(click());
        intended(hasComponent(SearchArticles.class.getName()));
    }
}