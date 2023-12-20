package com.food.food_order_hotel_admin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.food.food_order_hotel_admin.Activity.Hotel.HotelMainActivity;
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
    CardView payout,settings,more;
    TextView txtPayNow,sales;
    ImageView orderopen;
    AsyncTask<String, Void, String> getAllOrdersnew_Asyncs;
    JSONParser jsonParser=new JSONParser();
    ConnectionDetector cd;
    public static String VENDOR_ID,HOTEL_NAME;
    SharedPreferences sharedPreferences;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homedashboard);
        payout=findViewById(R.id.payout);
        settings=findViewById(R.id.settings);
        sales=findViewById(R.id.sales);
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
        sharedPreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("hotel_name"))
        {
            HOTEL_NAME=sharedPreferences.getString("hotel_name",null);
        }
        if(sharedPreferences.contains("vendor_id"))
        {
            VENDOR_ID=sharedPreferences.getString("vendor_id",null);
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
}