package com.company.antoine.mynews.Controlers.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.company.antoine.mynews.Controlers.Fragment.PageAdapter;
import com.company.antoine.mynews.R;
import com.company.antoine.mynews.Utils.NotificationAlarm;

import java.util.Calendar;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private String mSection[] = {"books","politics","sports","technology","theater","travel"};
    private String SAVE_BUTTON = "save button";
    private boolean mActionNotification;
    private PendingIntent mPending;
    private AlarmManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_navigation);
        mActionNotification = getSharedPreferences("My settings", MODE_PRIVATE).getBoolean(SAVE_BUTTON, false);
        configureToolbar();
        configureViewPager();

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()) {
                            case R.id.nav_books:
                                accessSectionDrawer(0);
                                return true;
                            case R.id.nav_politics:
                                accessSectionDrawer(1);
                                return true;
                            case R.id.nav_sports:
                                accessSectionDrawer(2);
                                return true;
                            case R.id.nav_technology:
                                accessSectionDrawer(3);
                                return true;
                            case R.id.nav_theater:
                                accessSectionDrawer(4);
                                return true;
                            case R.id.nav_travel:
                                accessSectionDrawer(5);
                                return true;
                            default: return true;
                        }
                    }
                });

        //AlarmManager for send the notification
        Intent intent = new Intent(this, NotificationAlarm.class);
        mPending = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        mManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 10);
        Objects.requireNonNull(mManager).setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, mPending);

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );
    }

    private void configureViewPager(){
        ViewPager pager = findViewById(R.id.activity_main_viewPager);
        pager.setOffscreenPageLimit(2);
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        TabLayout tabs = findViewById(R.id.activity_main_tabs);
        tabs.setupWithViewPager(pager);
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }

    private void configureToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setElevation(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tool_bar_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //recovers the click on the button of the toolBar
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_activity_main_search:
                Intent intent = new Intent(MainActivity.this, SearchArticles.class);
                startActivity(intent);
                return true;
            case R.id.action_notifications:
                Intent intent1 = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent1);
                return true;
            case R.id.action_help:
                Toast.makeText(this,"Help not available",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_about:
                Toast.makeText(this,"About not available",Toast.LENGTH_SHORT).show();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void accessSectionDrawer(int position){
        Intent intent = new Intent(MainActivity.this, DrawerViewArticleActivity.class);
        intent.putExtra("sectionChecked",mSection[position]);
        startActivity(intent);
        
    }

    @Override
    protected void onPostResume() {
        if (!mActionNotification){
            mManager.cancel(mPending);
        }
        super.onPostResume();
    }
}
