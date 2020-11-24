package com.xample.accountbook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.xample.accountbook.activity.AboutActivity;
import com.xample.accountbook.activity.HelpActivity;
import com.xample.accountbook.activity.ReportActivity;
import com.xample.accountbook.activity.SettingActivity;
import com.xample.accountbook.adapter.*;
import com.xample.accountbook.db.AccountDao;
import com.xample.accountbook.db.DatabaseHelper;
import com.xample.accountbook.fragment.SummaryFragment;
import com.xample.accountbook.fragment.IncomeFragment;
import com.xample.accountbook.fragment.OutlayFragment;

public class Main3Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        viewPager=findViewById(R.id.viewpager);
        setupViewPaper(viewPager);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_summary:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.nav_income:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.nav_outlay:
                        viewPager.setCurrentItem(2);
                        return true;
                    default:break;
                }
                return false;
            }
        });


        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main3, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
            Intent in = new Intent(this, SettingActivity.class);
            startActivity(in);
        } else if (id == R.id.nav_send) {
            // 报告
            Intent intent = new Intent(this, ReportActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_help) {
            //帮助页面
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            // 关于
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setupViewPaper(ViewPager viewPaper) {
        ViewPaperAdapter adapter = new ViewPaperAdapter(getSupportFragmentManager());
        SummaryFragment f1 = new SummaryFragment();
        IncomeFragment f2 = new IncomeFragment();
        OutlayFragment f3 = new OutlayFragment();
        adapter.addFragment(f1);
        adapter.addFragment(f2);
        adapter.addFragment(f3);
        viewPaper.setAdapter(adapter);
    }

}