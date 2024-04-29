package com.food.food_order_hotel_admin.Activity.Hotel;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.food.food_order_hotel_admin.Activity.DeliveryBoyActivity;
import com.food.food_order_hotel_admin.Activity.Homedashboard;
import com.food.food_order_hotel_admin.Activity.LoginActivity;

import com.food.food_order_hotel_admin.Activity.MyForegroundService;
import com.food.food_order_hotel_admin.Activity.SplashActivity;
import com.food.food_order_hotel_admin.Activity.pendingnew_payment;
import com.food.food_order_hotel_admin.CustomDialog;
import com.food.food_order_hotel_admin.Fragment.Hotel.HotelAllOrder;

import com.food.food_order_hotel_admin.Utils.SessionManager;
import com.food.food_order_hotel_admin.utilities.Config;
import com.food.food_order_hotel_admin.utilities.ConnectionDetector;
import com.food.food_order_hotel_admin.utilities.JSONParser;
import com.google.android.material.navigation.NavigationView;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import food.food_order_hotel_admin.R;

public class HotelMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    Toolbar toolbar;
    MediaPlayer mp;
    TextView  txtManagerName,txtVersionCode,txtPayNow;
    CustomDialog customDialog,customDialog1;

    SharedPreferences sharedPreferences;
    String id="Default";
    NotificationCompat.Builder notifiaction;
    private DrawerLayout drawerLayout;
    public static String VENDOR_ID,HOTEL_NAME;
    AsyncTask<String, Void, String> getAllOrders_Async;
    AsyncTask<String, Void, String> getAllOrdersorder_Async;

    Switch switch1;
    AsyncTask<String, Void, String> getAllOrdersnew_Async;
    AsyncTask<String, Void, String> getAllOrdersnew_Asyncn;
    private static final String  ONESIGNAL_APP_ID="f6317f50-c642-46f5-9a75-74bcfbcbac0b";


    private ActionBarDrawerToggle drawerToggle;
    ConnectionDetector cd;
    SessionManager sessionManager;
    SwipeRefreshLayout swipeRefreshLayout;
    AsyncTask<String, Void, String> updatetoken;
    JSONParser jsonParser=new JSONParser();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_main);
        drawerLayout=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE,OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        OneSignal.promptForPushNotifications();

//        txtPayNow=findViewById(R.id.txtPayNow);

        navigationView=findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        switch1=headerView.findViewById(R.id.switch1);
        sessionManager = new SessionManager(this);
        cd=new ConnectionDetector(this);
        txtVersionCode=navigationView.findViewById(R.id.txtVersionCode);
        txtManagerName= headerView.findViewById(R.id.txtManagerName);
        navigationView.setNavigationItemSelectedListener(this);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

//        mp = MediaPlayer.create(this, R.raw.land_line);


////




        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try
                {

                    Intent intent=new Intent(HotelMainActivity.this, HotelMainActivity.class);
                    startActivity(intent);
//

                }
                catch (Exception e)
                {
                    Toast.makeText(HotelMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("",e.getMessage());
                }
                finally {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HotelAllOrder());
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_draw_open,R.string.navigation_draw_open);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toolbar.setTitle("All Orders");

        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HotelAllOrder()).commit();
            navigationView.setCheckedItem(R.id.menuAllOrder);
        }

        sharedPreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("hotel_name"))
        {
            HOTEL_NAME=sharedPreferences.getString("hotel_name",null);
        }
        if(sharedPreferences.contains("vendor_id"))
        {
            VENDOR_ID=sharedPreferences.getString("vendor_id",null);
        }


        OneSignal.promptForPushNotifications();
        String push= OneSignal.getDeviceState().getUserId();
        Toast.makeText(this, ""+push, Toast.LENGTH_SHORT).show();

        if(cd.isConnectingToInternet()){
            String url= null;

            try {
                url = Config.get_url+
                        "action=update_hotel_token" +
                        "&vendor_id=" + URLEncoder.encode(VENDOR_ID=sharedPreferences.getString("vendor_id",null) ,"utf-8")+
                        "&fcm_token=" + URLEncoder.encode(""+push, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            updatetoken = new UpdateToken();
            updatetoken.execute(url);

        }else{
//                                Toast.makeText(HotelAdmin_MenusActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
//                                finish();
        }



        txtManagerName.setText(HOTEL_NAME);
        txtVersionCode.setText(SplashActivity.versionName);

        if(cd.isConnectingToInternet()){
            String url= null;

            try {
                url = Config.get_url+
                        "action=get_all_hotel_status"+
                        "&vendor_id="+ URLEncoder.encode(VENDOR_ID=sharedPreferences.getString("vendor_id",null),"utf-8");
            } catch (UnsupportedEncodingException e)
            {
                throw new RuntimeException(e);
            }
            getAllOrders_Async=new GetAllOrders_Async();
            getAllOrders_Async.execute(url);
        }else{
            Toast.makeText(HotelMainActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            finish();
        }

        if(cd.isConnectingToInternet()){
            String url= null;

            try {
                url = Config.get_url+
                        "action=get_order_status"+
                        "&vendor="+ URLEncoder.encode(VENDOR_ID=sharedPreferences.getString("vendor_id",null),"utf-8");
            } catch (UnsupportedEncodingException e)
            {
                throw new RuntimeException(e);
            }
            getAllOrdersorder_Async=new GetAllOrdersorder_Async();
            getAllOrdersorder_Async.execute(url);
        }else{
            Toast.makeText(HotelMainActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            finish();
        }
        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {

                String title = "Your Store Online Please Check New Order";
                String body = "";
                //    String imgurl = remoteMessage.getData().get("image");


                //   bitmap=getbitmap(imgurl);

             //   getnotifiacation(title,body);

                if(cd.isConnectingToInternet()){
                    String url= null;
                    try {
                        url = Config.get_url+
                                "action=update_hotel_open_status"+
                                "&vendor_id="+ URLEncoder.encode(VENDOR_ID=sharedPreferences.getString("vendor_id",null),"utf-8")+
                                "&livestatus="+ URLEncoder.encode("Open","utf-8")+
                                "&status="+ URLEncoder.encode("approved","utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    //  Toast.makeText(this, ""+DRIVER_ID, Toast.LENGTH_SHORT).show();
                    getAllOrdersnew_Async=new GetAllOrdersnew_Async();
                    getAllOrdersnew_Async.execute(url);
                }else{
                    // Toast.makeText(Collectdel_cash_main_admin.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
                //   status.setText("Avaliable");
            } else {
                if(cd.isConnectingToInternet()){
                    String url= null;
                    try {
                        url = Config.get_url+
                                "action=update_hotel_close_status"+
                                "&vendor_id="+ URLEncoder.encode(VENDOR_ID=sharedPreferences.getString("vendor_id",null),"utf-8")+
                                "&livestatus="+ URLEncoder.encode("Close","utf-8")+
                                "&status="+ URLEncoder.encode("block","utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(this, ""+db.getDelivaryboyId(), Toast.LENGTH_SHORT).show();
                    getAllOrdersnew_Asyncn=new GetAllOrdersnew_Asyncn();
                    getAllOrdersnew_Asyncn.execute(url);
                }else{
                    // Toast.makeText(Collectdel_cash_main_admin.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }



    private boolean Foregroundservice() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if(MyForegroundService.class.getName().equals(service.service.getClassName())) {

                return true;
            }
        }
        return false;
    }



//    private void getnotifiacation(String title, String body) {
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.bell)
//                .setAutoCancel(true)
//                .setContentTitle("name")
//                .setContentText("body")
//                .setGroupSummary(true);
//
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menuAllOrders:

                toolbar.setTitle("All Orders");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HotelAllOrder()).commit();

                break;
            case R.id.menuCompleteOrders:

                Intent i = new Intent(HotelMainActivity.this, DeliveryBoyActivity.class);
                //Intent intent=new Intent(getContext(), OrderDetailActivity.class);
                startActivity(i);
                break;
                case R.id.menuMySale:

                    Intent is = new Intent(HotelMainActivity.this, pendingnew_payment.class);
                    //Intent intent=new Intent(getContext(), OrderDetailActivity.class);
                    startActivity(is);
                break;
            case R.id.menuMenu:
                Intent a = new Intent(HotelMainActivity.this, HotelMenuActivity.class);
                //Intent intent=new Intent(getContext(), OrderDetailActivity.class);
                startActivity(a);
                break;
                case R.id.Homepage:

                    Intent s = new Intent(HotelMainActivity.this, Homedashboard.class);
                    //Intent intent=new Intent(getContext(), OrderDetailActivity.class);
                    startActivity(s);
                break;
            case R.id.menuLogout:

                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(HotelMainActivity.this, LoginActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.menusupport:

                String url = "https://api.whatsapp.com/send?phone="+"918767070841";
                Intent in = new Intent(Intent.ACTION_VIEW);
                in.setData(Uri.parse(url));
                startActivity(in);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

        class UpdateToken extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dialog=new ProgressDialog(CategoriesActivity.this);
//            dialog.setMessage("Getting trending menus");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialog) {
//                    getcoupon.cancel(true);
//                    finish();
//                }
//            });
//            dialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            return jsonParser.doGetRequest(params[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //    dialog.dismiss();
            if(result==null||result.trim().length()<=0){
                Toast.makeText(HotelMainActivity.this,"No response from server, Please check your internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject jsonObject=new JSONObject(result);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class GetAllOrdersnew_Async extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dialog=new ProgressDialog(SkyEatAdminMain_OrderDetails.this);
//            dialog.setMessage("Getting all orders");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialog) {
//                    getAllOrders_Async.cancel(true);
//                    finish();
//                }
//            });
//            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return jsonParser.doGetRequest(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //   dialog.dismiss();
            if(result==null||result.trim().length()<=0){
                Toast.makeText(HotelMainActivity.this,"No response from server, Please check your internet connection", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONObject jsonObject=new JSONObject(result);
                if(jsonObject.getString("result").equals("true")){



//                    Intent intent=new Intent(Collectdel_cash_main_admin.this,HomeActivity.class);
//                    startActivity(intent);
                    Toast.makeText(HotelMainActivity.this  , "Restaurant Open Succesfully ", Toast.LENGTH_SHORT).show();
                    //  finish();



                }
                else
                {
                    // Toast.makeText(DeliveryBoyMainActivity.this  , "not Cash received", Toast.LENGTH_SHORT).show();


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class GetAllOrdersnew_Asyncn extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dialog=new ProgressDialog(SkyEatAdminMain_OrderDetails.this);
//            dialog.setMessage("Getting all orders");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialog) {
//                    getAllOrders_Async.cancel(true);
//                    finish();
//                }
//            });
//            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return jsonParser.doGetRequest(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //   dialog.dismiss();
            if(result==null||result.trim().length()<=0){
                Toast.makeText(HotelMainActivity.this,"No response from server, Please check your internet connection", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONObject jsonObject=new JSONObject(result);
                if(jsonObject.getString("result").equals("true")){



//                    Intent intent=new Intent(Collectdel_cash_main_admin.this,HomeActivity.class);
//                    startActivity(intent);
                    Toast.makeText(HotelMainActivity.this  , " Restaurant Close Succesfully", Toast.LENGTH_SHORT).show();
                    //  finish();



                }
                else
                {
                    //  Toast.makeText(Collectdel_cash_main_admin.this  , "not Cash received", Toast.LENGTH_SHORT).show();


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class GetAllOrders_Async extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dialog=new ProgressDialog(SkyEatAdminMain_OrderDetails.this);
//            dialog.setMessage("Getting all orders");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialog) {
//                    getAllOrders_Async.cancel(true);
//                    finish();
//                }
//            });
//            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return jsonParser.doGetRequest(params[0]);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //   dialog.dismiss();
            if (result == null || result.trim().length() <= 0) {
                Toast.makeText(HotelMainActivity.this, "No response from server, Please check your internet connection", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getString("result").equals("true")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jobj = jsonArray.getJSONObject(i);

                       final String vendor_id=jobj.getString("vendor_id");
//                    final String block=jobj.getString("block");
                        final String status=jobj.getString("status");
                    //    Toast.makeText(HotelMainActivity.this  , " Cash received"+vendor_id, Toast.LENGTH_SHORT).show();

                        if (status.equals("approved"))
                        {
                            Intent intent=new Intent(HotelMainActivity.this, MyForegroundService.class);
                            intent.putExtra("title", "Hello, Your Store Online!!! ");

                            startForegroundService(intent);
                            Foregroundservice();

                            switch1.setChecked(true);
                        }
                        else {
                            Intent intent=new Intent(HotelMainActivity.this, MyForegroundService.class);
                            intent.putExtra("title", "Hello, Your Store Offline Open Store and Accept order!!! ");

                            startForegroundService(intent);
                            Foregroundservice();
                            switch1.setChecked(false);
                        }

//                    if (block.equals("1"))
//                    {
//                        Intent intent=new Intent(OrderDetailActivity.this, blockactivity.class);
//                        startActivity(intent);
//                        finish();
//
//                    }
//                    else {
//
//                    }

//                    Intent intent=new Intent(Collectdel_cash_main_admin.this,HomeActivity.class);
//                    startActivity(intent);
                        //   Toast.makeText(DeliveryBoyMainActivity.this, "Drive Mode Is Off", Toast.LENGTH_SHORT).show();
                        //  finish();


                    }
                } else {
                     //Toast.makeText(HotelMainActivity.this  , "not Cash received", Toast.LENGTH_SHORT).show();


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
  class GetAllOrdersorder_Async extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dialog=new ProgressDialog(SkyEatAdminMain_OrderDetails.this);
//            dialog.setMessage("Getting all orders");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialog) {
//                    getAllOrders_Async.cancel(true);
//                    finish();
//                }
//            });
//            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return jsonParser.doGetRequest(params[0]);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //   dialog.dismiss();
            if (result == null || result.trim().length() <= 0) {
                Toast.makeText(HotelMainActivity.this, "No response from server, Please check your internet connection", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getString("result").equals("true")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jobj = jsonArray.getJSONObject(i);

                       final String vendor=jobj.getString("vendor");
//                    final String block=jobj.getString("block");
                        final String delivery_state=jobj.getString("status");


                    }
                } else {
                     //Toast.makeText(HotelMainActivity.this  , "not Cash received", Toast.LENGTH_SHORT).show();


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    public void openGullakPopPup()
    {
        try {
            customDialog = new CustomDialog(HotelMainActivity.this);
            customDialog.setContentView(R.layout.skyeat_gullak);
            customDialog.setCancelable(false);
            mp.start();

//            txtTotalAmount=customDialog.findViewById(R.id.txtTotalAmount);
//
//            txtCompeteOrder=customDialog.findViewById(R.id.txtCompeteOrder);
//            txtGullak=customDialog.findViewById(R.id.txtGullak);
//            txtCancel=customDialog.findViewById(R.id.txtCancel);
//            txtPayNow=customDialog.findViewById(R.id.txtPayNow);

            Window window = customDialog.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(R.color.purple_500);
            if (window != null) {
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(window.getAttributes());

                // Set the desired width and height of the dialog
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

                window.setAttributes(layoutParams);
            }

//            txtGullak.setText(gullak);
//            txtCompeteOrder.setText(completeOrder);
//            txtTotalAmount.setText(String.valueOf(Math.round(totalAmount)));
            //txtTotalAmount.setText(String.valueOf(totalAmount));

//            txtPayNow.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    C= Calendar.getInstance();
////                    day=C.get(Calendar.DAY_OF_MONTH);
////                    month=C.get(Calendar.MONTH)+1;
////                    year=C.get(Calendar.YEAR);
////                    minute=C.get(Calendar.MINUTE);
////                    year=C.get(Calendar.SECOND);
////
////                    dateTimeInMillis = C.getTimeInMillis();
////                    payNow(txtTotalAmount.getText().toString());
//                    //payNow("1");
//                }
//            });

//            txtCancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    if(isCloseApp)
////                    {
////                        customDialog.dismiss();
////                        finishAffinity();
////                    }
////                    else {
////                        customDialog.dismiss();
////                    }
//
//                }
//            });


        }
        catch (Exception e){
            Log.d("Error",e.getMessage());
        }
        finally {
            customDialog.show();
        }
    }


}