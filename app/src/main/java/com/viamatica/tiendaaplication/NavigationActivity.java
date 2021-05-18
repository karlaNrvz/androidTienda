package com.viamatica.tiendaaplication;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.viamatica.tiendaaplication.ui.dashboard.DashboardFragment;
import com.viamatica.tiendaaplication.ui.home.HomeFragment;
import com.viamatica.tiendaaplication.ui.notifications.NotificationsFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class NavigationActivity extends AppCompatActivity {
    BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
         navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        HomeFragment fragmentHome = new HomeFragment();
                        ChangeFragment(fragmentHome);
                        item.setChecked(true);
                        break;
                    case R.id.navigation_dashboard:
                        //LanzamientosFragments fragmentPerfil = new LanzamientosFragments();
                        DashboardFragment fragmentPerfil = new DashboardFragment();
                        ChangeFragment(fragmentPerfil);
                        break;

                    case R.id.navigation_notifications:
                        NotificationsFragment fragPerfil = new NotificationsFragment();
                        ChangeFragment(fragPerfil);
                        break;

                }
                return true;
            }

        });

        navView.setSelectedItemId(R.id.navigation_home);

    }

    private void ChangeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.commit();


    }

}