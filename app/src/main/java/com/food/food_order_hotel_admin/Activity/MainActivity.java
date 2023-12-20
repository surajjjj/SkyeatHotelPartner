package com.food.food_order_hotel_admin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.food.food_order_hotel_admin.Fragment.AllOrder;
import com.food.food_order_hotel_admin.Fragment.DeliveryBoyList;
import com.food.food_order_hotel_admin.Fragment.ManagerListFragment;
import com.food.food_order_hotel_admin.Fragment.Zone;
import com.food.food_order_hotel_admin.Interface.DrawerLocker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.onesignal.OneSignal;

import food.food_order_hotel_admin.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DrawerLocker {

    NavigationView navigationView;
    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    TextView txtManagerName;
    TextView txtVersionCode;
    public static boolean isCollectCash=false;

    private ActionBarDrawerToggle drawerToggle;
    SharedPreferences sharedPreferences;
    public static String ZONE_NAME,ID,managerName;
    private static final String ONESIGNAL_APP_ID = "c27752b4-e673-4978-8e24-18a67ba0d312";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        OneSignal.promptForPushNotifications();
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MainActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String mToken = instanceIdResult.getToken();

                Log.e("Token",mToken);
                 Toast.makeText(MainActivity.this, ""+mToken, Toast.LENGTH_SHORT).show();

//                if(cd.isConnectingToInternet()){
//                    String url= null;
//
//                        try {
//                            url = Config.get_url+
//                                    "action=update_hotel_token" +
//                                    "&id=" + URLEncoder.encode(store.getId() ,"utf-8")+
//                                    "&fcm_token=" + URLEncoder.encode(mToken, "utf-8");
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }
//
//                    updatetoken = new UpdateToken();
//                    updatetoken.execute(url);
//
//                }else{
////                                Toast.makeText(HotelAdmin_MenusActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
////                                finish();
//                }
//


            }
        });

//        FirebaseMessaging.getInstance().subscribeToTopic("NewOrder")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        String msg = "Done";
//
//                        if (!task.isSuccessful()) {
//                            msg = "failed";
//                        }
//                        Log.d(TAG, msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });


//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                        if (!task.isSuccessful()) {
//
//                            return;
//                        }
//                        // Get new Instance ID token
//                        String token = task.getResult().getToken();
//                        Log.e("TOKEN",token);
//
//                        //Toast.makeText(MainActivity.this, ""+token, Toast.LENGTH_SHORT).show();
//                    }
//                });
        drawerLayout=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        navigationView=findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        txtManagerName= headerView.findViewById(R.id.txtManagerName);
        txtVersionCode = navigationView.findViewById(R.id.txtVersionCode);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AllOrder());
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_draw_open,R.string.navigation_draw_open);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toolbar.setTitle("All Orders");

        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AllOrder()).commit();
            navigationView.setCheckedItem(R.id.menuAllOrder);
        }
        sharedPreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("zone_name"))
        {
            ZONE_NAME=sharedPreferences.getString("zone_name",null);
        }
        if(sharedPreferences.contains("id"))
        {
            ID=sharedPreferences.getString("id",null);
        }
        if(sharedPreferences.contains("username"))
        {
            managerName=sharedPreferences.getString("username",null);
        }
        txtManagerName.setText("Mr. "+managerName);
        txtVersionCode.setText(SplashActivity.versionName);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menuAllOrder:
                isCollectCash=false;
                toolbar.setTitle("All Orders");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AllOrder()).commit();

//                Intent intent = new Intent(this, AllOrder.class);
//                startActivity(intent);
                break;
            case R.id.menupaymnt:
                isCollectCash=false;
                toolbar.setTitle("All Orders");
                Intent intentw = new Intent(MainActivity.this, PendingPayment.class);
                startActivity(intentw);
//                Intent intent = new Intent(this, AllOrder.class);
//                startActivity(intent);
                break;
            case R.id.menuDeliveryBoy:
                isCollectCash=false;
                toolbar.setTitle("Delivery Boy List");
            //    Toast.makeText(this, "Delivery Boy List", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DeliveryBoyList()).commit();

                break;

               case  R.id.menuCollectCash:
                   isCollectCash=true;
                   toolbar.setTitle("Delivery Boy List");
                   //    Toast.makeText(this, "Delivery Boy List", Toast.LENGTH_SHORT).show();
                   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DeliveryBoyList()).commit();

                   break;

            case R.id.menuMyZone:
                isCollectCash=false;
                toolbar.setTitle("My Zone");
             //   Toast.makeText(this, "My Zone", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Zone()).commit();
                break;
            case R.id.menuPayment:
                isCollectCash=false;
               // Toast.makeText(this, "Payment", Toast.LENGTH_SHORT).show();
                if(MainActivity.ZONE_NAME.equalsIgnoreCase("all"))
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ManagerListFragment()).commit();
                }
                else
                {
                  //  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PendingPayment()).commit();
                    Intent intent = new Intent(MainActivity.this, PendingPayment.class);
                    startActivity(intent);
                }
                break;

            case R.id.menuLogout:
                    isCollectCash=false;
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this, LoginTypeActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }
    @Override
    public void onBackPressed()
    {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer((GravityCompat.START));
        }
        else {
            exitAlertDialog();
           // super.onBackPressed();
        }
    }

    public void toggleNavigationDrawerIconVisibility(boolean visible) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (visible) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_24);
            } else {
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    @Override
    public void setDrawerLocked(boolean enabled) {

        if(enabled)
        {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            //navigationView.setVisibility(View.GONE);
            toolbar.setVisibility(View.GONE);

        }else
        {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            navigationView.setVisibility(View.VISIBLE);
           // toolbar.setVisibility(View.GONE);
        }
    }

    public void exitAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Alert Dialog!!!");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform any necessary actions and close the activity
                //super.onBackPressed();
                dialog.dismiss();
                finishAffinity();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog and continue with the activity
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

}