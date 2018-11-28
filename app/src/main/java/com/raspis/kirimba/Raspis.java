package com.raspis.kirimba;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Raspis extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private WebView webView;

    private static String link ="http://raspisanie.co.nf";

    /**
     * vib or week
     * @param an
     */
    public void lick(String an){
        String and;
        switch (an){
            case ("vib"):{
                and = "/?p";
                break;
            }
            case ("week"):{
                and = "/?page=week";
                break;
            }
            default:{
                and = "/";
            }
        }
        webView.loadUrl(link+and);
    }

    protected void show_hide_area_calendar(){
        LinearLayout calendar = findViewById(R.id.area_calendar);
        if(calendar.getVisibility() == View.INVISIBLE){
            calendar.setVisibility(View.VISIBLE);
        }else {
            calendar.setVisibility(View.INVISIBLE);
        }
    }

    public void onClick_botton_serch_day(View view){
        CalendarView calendar = findViewById(R.id.calendarView3);
        GregorianCalendar naw = new GregorianCalendar(TimeZone.getTimeZone("GMT+03"));
        calendar.setDate(naw.getTimeInMillis());
        long time = naw.getTimeInMillis()/1000;
        //Toast.makeText(getApplicationContext(),naw.getTime().getHours()+":"+naw.getTime().getMinutes()+" "+String.valueOf(time), Toast.LENGTH_SHORT).show();
        show_hide_area_calendar();
        webView.loadUrl(link+"/?day="+time);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raspis);

        webView = findViewById(R.id.webView);
        // включаем поддержку JavaScript
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new MyWebViewClient());
        // указываем страницу загрузки
        lick("null");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_hide_area_calendar();
            }
        });

        CalendarView calendar = findViewById(R.id.calendarView3);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                GregorianCalendar da = new GregorianCalendar(TimeZone.getTimeZone("GMT+03"));
                da.set(year,month,dayOfMonth);
                long time = da.getTimeInMillis()/1000;
                //Toast.makeText(getApplicationContext(),da.getTime().getHours()+":"+da.getTime().getMinutes()+" "+String.valueOf(time), Toast.LENGTH_SHORT).show();
                show_hide_area_calendar();
                webView.loadUrl(link+"/?day="+time);
            }
        });


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(webView.canGoBack()) {
                webView.goBack();
            } else {
                super.onBackPressed();
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.select_group) {
            lick("vib");
        } else if (id == R.id.on_day) {
            lick("nill");
        } else if (id == R.id.on_week) {
            lick("week");
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

class MyWebViewClient extends WebViewClient {
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        view.loadUrl(request.getUrl().toString());
        return true;
    }

    // Для старых устройств
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
