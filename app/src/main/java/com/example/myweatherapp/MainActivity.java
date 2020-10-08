package com.example.myweatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements Constants, NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private  DialogBuilderFragment dialogBuilderFragment;
    private CitiesFragment citiesFragment;
    private final BroadcastReceiver networkReceiver = new NetworkReceiver();
    private LocationManager locationManager;
    private final int PERMISSION_REQUEST_CODE = 100;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initNotificationChannel();
        registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        dialogBuilderFragment = new DialogBuilderFragment();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        if (savedInstanceState == null) {
            citiesFragment = new CitiesFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, citiesFragment)
                    .commit();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Snackbar.make(toolbar, "Нажата кнопка меню", Snackbar.LENGTH_LONG).show();
                return true;
            case R.id.action_delete:
                HistoryDao historyDao = App.getInstance().getHistoryDao();
                HistoryRequestSource historyRequestSource = new HistoryRequestSource(historyDao);
                historyRequestSource.deleteAllHistory();

                openHistoryFragment();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_home:
                if(getSupportFragmentManager().getPrimaryNavigationFragment() != citiesFragment ) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, citiesFragment)
                            .commit();
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_history:
                openHistoryFragment();

                drawer.closeDrawer(GravityCompat.START);
                    return true;
                    case R.id.nav_share:
                        return true;
                    case R.id.nav_send:
                        return true;


        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClickDialogBuilder(View view, String city){
        dialogBuilderFragment.setCity(city);
        dialogBuilderFragment.show(getSupportFragmentManager(), "dialogBuilder");
    }

    public void onDialogResult(String resultDialog){
        Toast.makeText(this, "Выбрано " + resultDialog, Toast.LENGTH_SHORT).show();

    }

    private void initNotificationChannel(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = notificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel("2","name",importance);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void openHistoryFragment(){
        HistoryFragment historyFragment1 = new HistoryFragment(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, historyFragment1)
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length == 2 &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            }
        }
    }


}
