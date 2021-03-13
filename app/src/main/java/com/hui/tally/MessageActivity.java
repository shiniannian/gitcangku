package com.hui.tally;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;






public class MessageActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private Button progressBarButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_message);
        initview();

        //把toolbar替代ActionBar并把左上角的图标显示
        setSupportActionBar(toolbar);
        //决定左上角的图标是否可以点击
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //ActionBarDrawerToggle 的作用：改变android.R.id.home返回图标。Drawer拉出、隐藏，带有android.R.id.home动画效果监听Drawer拉出、隐藏；
        //ActionBarDrawerToggle 通常和 NavigationDrawer 搭配使用
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, 0, 0);
        //建立DrawerLayout和ActionBarDrawerToggle的关联
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
    }

    private void initview() {
        drawerLayout = findViewById(R.id.m_drawer);
        toolbar = findViewById(R.id.m_toolbar);
        navigationView = findViewById(R.id.m_nav);
        progressBarButton = findViewById(R.id.progressBarbutton);
        progressBar = findViewById(R.id.ProgressBar1);
        progressBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(progressBar.getVisibility()==View.GONE){ //setVisibility设置控件可见性View.GONE不可见 View.VISIBLE可见 View.INVISIBLE透明性质，不可见在还占空间
                    progressBar.setVisibility(View.VISIBLE);
                }
                else{
                    progressBar.setVisibility(View.GONE);
                }
            }
        });



    }
}