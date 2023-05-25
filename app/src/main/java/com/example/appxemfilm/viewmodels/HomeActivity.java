package com.example.appxemfilm.viewmodels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appxemfilm.R;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {
    private static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_CHUDE = 2;
    private static final int FRAGMENT_HOT = 3;
    private static final int FRAGMENT_SAPCHIEU = 4;
    private static final int FRAGMENT_TAIKHOAN = 5;
    private static final int FRAGMENT_VECHUNGTOI = 6;
    private static final int FRAGMENT_EXIT = 7;

    private int currentFragment = FRAGMENT_HOME;
    String session_id;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView text_view_header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(getIntent().hasExtra("session")){
            session_id = getIntent().getStringExtra("session");
        }
        AnhXa();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_chu_de:
                        if(currentFragment != FRAGMENT_CHUDE){
                            replaceFragment(new ChuDeFragment());
                            currentFragment = FRAGMENT_CHUDE;
                            text_view_header.setText("Chủ đề");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.item_hot:
                        if(currentFragment != FRAGMENT_HOT){
                            replaceFragment(new PhimHotFragment());
                            currentFragment = FRAGMENT_HOT;
                            text_view_header.setText("Phim hot");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.item_home:
                        if(currentFragment != FRAGMENT_HOME){
                            replaceFragment(new HomeFragment());
                            currentFragment = FRAGMENT_HOME;
                            text_view_header.setText("Trang chủ");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.item_near:
                        if(currentFragment != FRAGMENT_SAPCHIEU){
                            replaceFragment(new SapChieuFragment());
                            currentFragment = FRAGMENT_SAPCHIEU;
                            text_view_header.setText("Phim sắp chiếu");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.item_user:
                        if(currentFragment != FRAGMENT_TAIKHOAN){
                            replaceFragment(new UserFragment());
                            currentFragment = FRAGMENT_TAIKHOAN;
                            text_view_header.setText("Người dùng");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.item_about_us:
                        if(currentFragment != FRAGMENT_VECHUNGTOI){
                            replaceFragment(new AboutUsFragment());
                            currentFragment = FRAGMENT_VECHUNGTOI;
                            text_view_header.setText("Về chúng tôi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.item_exit:
                        if(currentFragment != FRAGMENT_EXIT){
                            replaceFragment(new ExitFragment());
                            currentFragment = FRAGMENT_EXIT;
                            text_view_header.setText("Thoát");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                }
                return false;
            }
        });
    }

    public void AnhXa(){
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        text_view_header = findViewById(R.id.text_view_header);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void replaceFragment (Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
}