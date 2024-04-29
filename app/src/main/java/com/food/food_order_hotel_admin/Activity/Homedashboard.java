package com.food.food_order_hotel_admin.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.food.food_order_hotel_admin.Activity.Hotel.HotelMainActivity;
import com.food.food_order_hotel_admin.Activity.Hotel.HotelMenuActivity;
import com.food.food_order_hotel_admin.utilities.Config;
import com.food.food_order_hotel_admin.utilities.ConnectionDetector;
import com.food.food_order_hotel_admin.utilities.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import food.food_order_hotel_admin.R;

public class Homedashboard extends AppCompatActivity {
    CardView payout,settings,more,menuopen;
    TextView txtPayNow,sales,clearnew;
    ImageView orderopen;
    AsyncTask<String, Void, String> getAllOrdersnew_Async;
    AsyncTask<String, Void, String> getAllOrdersnew_Asyncn;
    AsyncTask<String, Void, String> getAllOrders_Async;
    AsyncTask<String, Void, String> getAllOrdersnew_Asyncs;
    JSONParser jsonParser=new JSONParser();
    ConnectionDetector cd;
  //  ToggleButton toggleButton;
    public static String VENDOR_ID,HOTEL_NAME;
    SharedPreferences sharedPreferences;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homedashboard);
        payout=findViewById(R.id.payout);
        settings=findViewById(R.id.settings);
        menuopen=findViewById(R.id.menuopen);
        sales=findViewById(R.id.sales);
      //  toggleButton.findViewById(R.id.toggale);
        clearnew=findViewById(R.id.clearnew);
        more=findViewById(R.id.more);
        orderopen=findViewById(R.id.orderopen);
        txtPayNow=findViewById(R.id.txtPayNow);
        cd=new ConnectionDetector(this);
        payout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Homedashboard.this, hotel_settalment.class);
                startActivity(intent);
            }
        });
        txtPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Homedashboard.this, pendingnew_payment.class);
                startActivity(intent);
            }
        });
//        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked)
//                {
////                    if(cd.isConnectingToInternet()){
////                        String url= null;
////                        try {
////                            url = Config.get_url+
////                                    "action=update_hotel_open_status"+
////                                    "&vendor_id="+ URLEncoder.encode(VENDOR_ID=sharedPreferences.getString("vendor_id",null),"utf-8")+
////                                    "&livestatus="+ URLEncoder.encode("Open","utf-8")+
////                                    "&status="+ URLEncoder.encode("approved","utf-8");
////                        } catch (UnsupportedEncodingException e) {
////                            e.printStackTrace();
////                        }
////                        //  Toast.makeText(this, ""+DRIVER_ID, Toast.LENGTH_SHORT).show();
////                        getAllOrdersnew_Async=new GetAllOrdersnew_Async();
////                        getAllOrdersnew_Async.execute(url);
////                    }else{
////                        // Toast.makeText(Collectdel_cash_main_admin.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
////                    }
////                }
////                else {
////                    if(cd.isConnectingToInternet()){
////                        String url= null;
////                        try {
////                            url = Config.get_url+
////                                    "action=update_hotel_close_status"+
////                                    "&vendor_id="+ URLEncoder.encode(VENDOR_ID=sharedPreferences.getString("vendor_id",null),"utf-8")+
////                                    "&livestatus="+ URLEncoder.encode("Close","utf-8")+
////                                    "&status="+ URLEncoder.encode("block","utf-8");
////                        } catch (UnsupportedEncodingException e) {
////                            e.printStackTrace();
////                        }
////                        //Toast.makeText(this, ""+db.getDelivaryboyId(), Toast.LENGTH_SHORT).show();
////                        getAllOrdersnew_Asyncn=new GetAllOrdersnew_Async();
////                        getAllOrdersnew_Asyncn.execute(url);
////                    }else{
////                        // Toast.makeText(Collectdel_cash_main_admin.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
////                    }
////
//
//                }
//            }
//        });
        orderopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Homedashboard.this, HotelMainActivity.class);
                startActivity(intent);
            }
        });   
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Homedashboard.this, "Some work pending our next update coming soon!!!", Toast.LENGTH_SHORT).show();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Homedashboard.this, HotelMainActivity.class);
                startActivity(intent);
            }
        });
        menuopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Homedashboard.this, HotelMenuActivity.class);
                startActivity(intent);
            }
        });
        sharedPreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("hotel_name"))
        {
            HOTEL_NAME=sharedPreferences.getString("hotel_name",null);
        }
        if(sharedPreferences.contains("vendor_id"))
        {
            VENDOR_ID=sharedPreferences.getString("vendor_id",null);
        }

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
            getAllOrders_Async=new GetAllOrderss_Async();
            getAllOrders_Async.execute(url);
        }else{
            Toast.makeText(Homedashboard.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (cd.isConnectingToInternet()) {
            String url = null;

            try {
                url = Config.get_url +
                        "action=get_total_payment"+
                        "&vendor=" + URLEncoder.encode(VENDOR_ID=sharedPreferences.getString("vendor_id",null) ,"utf-8")+
                        "&status=" + URLEncoder.encode("Completed" ,"utf-8")+
                        "&wallet_payment_status=" + URLEncoder.encode("","utf-8");

            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }


            getAllOrdersnew_Asyncs = new GetAllOrders_Async();
            getAllOrdersnew_Asyncs.execute(url);
        } else {
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
    class GetAllOrders_Async extends AsyncTask<String, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(Homedashboard.this);
            dialog.setMessage("Getting Your Wallet balance");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    getAllOrdersnew_Asyncs.cancel(true);
                    finish();
                }
            });
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return jsonParser.doGetRequest(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            dialog.dismiss();
            if(result==null||result.trim().length()<=0){
                Toast.makeText(Homedashboard.this,"No response from server, Please check your internet connection", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONObject jsonObject=new JSONObject(result);
                if(jsonObject.getString("result").equals("true")){
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject jobj = jsonArray.getJSONObject(i);

                        //    String dispatch_order_id=jobj.getString("dispatch_order_id");
//                        String sale_id	 = jobj.getString("sale_id");
//                        String vendor = jobj.getString("vendor");
//                        String vendor_name = jobj.getString("vendor_name");
                        String net_payable_hotel = jobj.getString("net_payable_hotel");
                        String s=String.valueOf(net_payable_hotel).split("\\.")[0];
                        sales.setText("₹ "+s);
//                        if (net_payable_hotel.equals("null"))
//                        {
//                            btnSubmitCash.setVisibility(View.GONE);
//                        }
//                        else {
//                            btnSubmitCash.setVisibility(View.VISIBLE);
//
//                            txtRemainCash.setText(""+net_payable_hotel);
//
//                        }


                        //  Toast.makeText(pendingnew_payment.this, ""+net_pay_hotel_with_cutting_commision, Toast.LENGTH_SHORT).show();



                        // adapter.add(new SkyEatAdminMain_ViewNewIorder(id, title, mobile, amt,city,dist_two,dist_three));

                    }
                }
            } catch (JSONException e) {
                Toast.makeText(Homedashboard.this, ""+e, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    class GetAllOrderss_Async extends AsyncTask<String, Void, String> {
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
                Toast.makeText(Homedashboard.this, "No response from server, Please check your internet connection", Toast.LENGTH_SHORT).show();
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
                        final String cash=jobj.getString("cash");
                        //    Toast.makeText(HotelMainActivity.this  , " Cash received"+vendor_id, Toast.LENGTH_SHORT).show();

                        if (cash.equals("1"))
                        {
                            txtPayNow.setVisibility(View.GONE);
                            sales.setVisibility(View.GONE);
                            clearnew.setVisibility(View.VISIBLE);
                            payout.setVisibility(View.GONE);


                        }

//

                    }
                } else {
                    //Toast.makeText(HotelMainActivity.this  , "not Cash received", Toast.LENGTH_SHORT).show();


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
                Toast.makeText(Homedashboard.this,"No response from server, Please check your internet connection", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONObject jsonObject=new JSONObject(result);
                if(jsonObject.getString("result").equals("true")){



//                    Intent intent=new Intent(Collectdel_cash_main_admin.this,HomeActivity.class);
//                    startActivity(intent);
                    Toast.makeText(Homedashboard.this  , " Restaurant Close Succesfully", Toast.LENGTH_SHORT).show();
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
}