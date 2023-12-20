package com.food.food_order_hotel_admin.Activity.Hotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.food.food_order_hotel_admin.Activity.DelivaryBoy.DeliveryBoyMainActivity;
import com.food.food_order_hotel_admin.Activity.DeliveryBoyActivity;
import com.food.food_order_hotel_admin.Activity.Homedashboard;
import com.food.food_order_hotel_admin.Activity.LoginActivity;
import com.food.food_order_hotel_admin.Activity.MainActivity;
import com.food.food_order_hotel_admin.Activity.OrderDetailActivity;
import com.food.food_order_hotel_admin.Activity.PendingPayment;
import com.food.food_order_hotel_admin.Activity.SplashActivity;
import com.food.food_order_hotel_admin.Activity.pendingnew_payment;
import com.food.food_order_hotel_admin.Fragment.Hotel.HotelAllOrder;

import com.food.food_order_hotel_admin.MyFirebaseMessagingService;
import com.food.food_order_hotel_admin.Utils.SessionManager;
import com.food.food_order_hotel_admin.utilities.Config;
import com.food.food_order_hotel_admin.utilities.ConnectionDetector;
import com.food.food_order_hotel_admin.utilities.JSONParser;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import food.food_order_hotel_admin.R;

public class HotelMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    Toolbar toolbar;
    TextView  txtManagerName,txtVersionCode,txtPayNow;
    SharedPreferences sharedPreferences;
    String id="Default";
    NotificationCompat.Builder notifiaction;
    private DrawerLayout drawerLayout;
    public static String VENDOR_ID,HOTEL_NAME;
    AsyncTask<String, Void, String> getAllOrders_Async;

    Switch switch1;
    AsyncTask<String, Void, String> getAllOrdersnew_Async;
    AsyncTask<String, Void, String> getAllOrdersnew_Asyncn;


    private ActionBarDrawerToggle drawerToggle;
    ConnectionDetector cd;
    SessionManager sessionManager;
    SwipeRefreshLayout swipeRefreshLayout;
    AsyncTask<String, Void, String> updatetoken;
    JSONParser jsonParser=new JSONParser();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_main);
        drawerLayout=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
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
//        txtPayNow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(HotelMainActivity.this, Homedashboard.class);
//                startActivity(intent);
//            }
//        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try
                {

                    Intent intent=new Intent(HotelMainActivity.this, HotelMainActivity.class);
                    startActivity(intent);
//                    delCompleteOrder="";
//                    orderCharge="";
//                    delTotalAmout=0;
//                    gullak="";
//                    completeOrder="";
//                    totalAmount=0.0;
//                    completeOrderList.clear();
//                    delCompleteOrderList.clear();
//                    txtDelBoyCompeteOrder.setText("");
//                    txtDelBoyTotalAmount.setText("");
//                    txtPerOrderCharge.setText("");
//                    txtGullak.setText("");
//                    txtTotalAmount.setText("");
//                    txtCompeteOrder.setText("");
//                    getOrderCharge();
//                    getDeliveryBoyGullak();
//                    getCompleteOrder();
//                    getDelBoyCompleteOrder();


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
//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(HotelMainActivity.this,  new OnSuccessListener<InstanceIdResult>() {
//            @Override
//            public void onSuccess(InstanceIdResult instanceIdResult) {
//
//                String mToken = instanceIdResult.getToken();
//                Log.e("Token",mToken);
//                Toast.makeText(HotelMainActivity.this, ""+mToken, Toast.LENGTH_SHORT).show();
//
//                if(cd.isConnectingToInternet()){
//                    String url= null;
//
//                        try {
//                            url = Config.get_url+
//                                    "action=update_hotel_token" +
//                                    "&vendor_id=" + URLEncoder.encode(VENDOR_ID=sharedPreferences.getString("vendor_id",null) ,"utf-8")+
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
////
//
//
//            }
//        });

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
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
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

                String url = "https://api.whatsapp.com/send?phone="+"8767070841";
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
                if(jsonObject.getString("result").equals("true")){
                    JSONArray jsonArray1=jsonObject.getJSONArray("data");
                    for(int j=0;j<jsonArray1.length();j++){
                        JSONObject jobj1=jsonArray1.getJSONObject(j);
                        Toast.makeText(HotelMainActivity.this, "Success", Toast.LENGTH_SHORT).show();


                    }
                }
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
                            switch1.setChecked(true);
                        }
                        else {
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


}