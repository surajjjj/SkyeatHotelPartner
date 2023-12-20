package com.food.food_order_hotel_admin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Calendar;
import java.util.Locale;

import food.food_order_hotel_admin.R;

public class pendingnew_payment extends AppCompatActivity {
    AsyncTask<String, Void, String> getAllOrdersnew_Async;
    AsyncTask<String, Void, String> getAllOrdersnew_Asyncc;
    AsyncTask<String, Void, String> update_rest_wallet_status;
    AsyncTask<String, Void, String> getAllOrdersnew_Asyncs;
    JSONParser jsonParser=new JSONParser();
    ConnectionDetector cd;
    public static String VENDOR_ID,HOTEL_NAME;
    SharedPreferences sharedPreferences;
    EditText txtRemainCash,txtWalletAmount,txtMassage,btnUpdateWallet;
    TextView btnSubmitCash;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendingnew_payment);
        cd=new ConnectionDetector(this);
        txtRemainCash=findViewById(R.id.txtRemainCash);
        txtWalletAmount=findViewById(R.id.txtWalletAmount);
        txtMassage=findViewById(R.id.txtMassage);
        txtMassage=findViewById(R.id.txtMassage);
        btnSubmitCash=findViewById(R.id.btnSubmitCash);
        btnUpdateWallet=findViewById(R.id.btnUpdateWallet);

//        String date =  DateUtils.formatDateTime(this, 1378798459, DateUtils.FORMAT_SHOW_DATE);

        sharedPreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("hotel_name"))
        {
            HOTEL_NAME=sharedPreferences.getString("hotel_name",null);
        }
        if(sharedPreferences.contains("vendor_id"))
        {
            VENDOR_ID=sharedPreferences.getString("vendor_id",null);
        }


        btnUpdateWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(pendingnew_payment.this, hotel_settalment.class);
                startActivity(intent);

            }
        });

               btnSubmitCash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (txtRemainCash.equals(""))
                    {
                        Toast.makeText(pendingnew_payment.this, "You Remain Cash is 0", Toast.LENGTH_SHORT).show();
                    }
                    else {



                    if (cd.isConnectingToInternet()) {
                        String url = null;
                        try {
                            url = Config.get_url +
                                    "action=insert_hotel_requeset" +
                                    "&type=" + URLEncoder.encode("Dr", "utf-8") +
                                    "&amount=" + URLEncoder.encode(txtRemainCash.getText().toString(), "utf-8") +
                                    "&balance=" + URLEncoder.encode(txtRemainCash.getText().toString(), "utf-8") +
                                    "&status=" + URLEncoder.encode("Request", "utf-8") +
                                    "&message=" + URLEncoder.encode(txtMassage.getText().toString(), "utf-8") +
                                    "&made_by=" + URLEncoder.encode("vendor", "utf-8") +
                                    "&user_type=" + URLEncoder.encode("vendor", "utf-8") +
//                                    "&date_new=" + URLEncoder.encode(date, "utf-8") +
                                    "&id=" + URLEncoder.encode(VENDOR_ID = sharedPreferences.getString("vendor_id", null), "utf-8");


                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        getAllOrdersnew_Asyncs = new GetAllOrdersnew_Asyncs();
                        getAllOrdersnew_Asyncs.execute(url);
                    } else {
                        Toast.makeText(pendingnew_payment.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }

                    }

                }
            });


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


            getAllOrdersnew_Async = new GetAllOrders_Async();
            getAllOrdersnew_Async.execute(url);
        } else {
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (cd.isConnectingToInternet()) {
            String url = null;

            try {
                url = Config.get_url +
                        "action=get_total_payment_pending"+
                        "&vendor=" + URLEncoder.encode(VENDOR_ID=sharedPreferences.getString("vendor_id",null) ,"utf-8")+
                        "&status=" + URLEncoder.encode("Completed" ,"utf-8")+
                        "&wallet_payment_status=" + URLEncoder.encode("1","utf-8");

            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }


            getAllOrdersnew_Asyncc = new GetAllOrders_Asyncc();
            getAllOrdersnew_Asyncc.execute(url);
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
            dialog=new ProgressDialog(pendingnew_payment.this);
            dialog.setMessage("Getting Your Wallet balance");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    getAllOrdersnew_Async.cancel(true);
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
                Toast.makeText(pendingnew_payment.this,"No response from server, Please check your internet connection", Toast.LENGTH_SHORT).show();
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

                        if (net_payable_hotel.equals("null"))
                        {
                            btnSubmitCash.setVisibility(View.GONE);
                        }
                        else {
                            btnSubmitCash.setVisibility(View.VISIBLE);

                            txtRemainCash.setText("₹ "+s);

                        }


                      //  Toast.makeText(pendingnew_payment.this, ""+net_pay_hotel_with_cutting_commision, Toast.LENGTH_SHORT).show();



                       // adapter.add(new SkyEatAdminMain_ViewNewIorder(id, title, mobile, amt,city,dist_two,dist_three));

                    }
                }
            } catch (JSONException e) {
                Toast.makeText(pendingnew_payment.this, ""+e, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
    class GetAllOrders_Asyncc extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(pendingnew_payment.this);
            dialog.setMessage("Getting Your Wallet balance");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    getAllOrdersnew_Asyncc.cancel(true);
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
                Toast.makeText(pendingnew_payment.this,"No response from server, Please check your internet connection", Toast.LENGTH_SHORT).show();
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

                      //  Toast.makeText(pendingnew_payment.this, ""+net_pay_hotel_with_cutting_commision, Toast.LENGTH_SHORT).show();



                       // adapter.add(new SkyEatAdminMain_ViewNewIorder(id, title, mobile, amt,city,dist_two,dist_three));

                        txtWalletAmount.setText("₹ "+s);
                    }
                }
            } catch (JSONException e) {
                Toast.makeText(pendingnew_payment.this, ""+e, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    class GetAllOrdersnew_Asyncs extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(pendingnew_payment.this);
            dialog.setMessage("Creating Your Request");
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
                Toast.makeText(pendingnew_payment.this,"No response from server, Please check your internet connection", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONObject jsonObject=new JSONObject(result);
                if(jsonObject.getString("result").equals("true")){

                        Toast.makeText(pendingnew_payment.this, "Your Request Created Succesfully...", Toast.LENGTH_SHORT).show();

                        if (cd.isConnectingToInternet()) {
                            String url = null;
                            try {
                                url = Config.get_url +
                                        "action=update_rest_wallet_status" +
                                        "&vendor=" + URLEncoder.encode(VENDOR_ID = sharedPreferences.getString("vendor_id", null), "utf-8") +
                                        "&wallet_payment_status=" + URLEncoder.encode("1", "utf-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                            ;
                            //Toast.makeText(this, ""+db.getDelivaryboyId(), Toast.LENGTH_SHORT).show();
                            update_rest_wallet_status = new GetAllUpdateOrders_Asyncwallet();
                            update_rest_wallet_status.execute(url);
                        } else {
                            Toast.makeText(pendingnew_payment.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                            finish();


                        }


                    }

            } catch (JSONException e) {
                Toast.makeText(pendingnew_payment.this, ""+e, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    class GetAllUpdateOrders_Asyncwallet extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(pendingnew_payment.this);
            dialog.setMessage("Getting all orders");
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
                Toast.makeText(pendingnew_payment.this,"No response from server, Please check your internet connection", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONObject jsonObject=new JSONObject(result);
                if(jsonObject.getString("result").equals("true")){
                    Toast.makeText(pendingnew_payment.this, "Wallet Request Created Succesfully", Toast.LENGTH_SHORT).show();




                    }

            } catch (JSONException e) {
                Toast.makeText(pendingnew_payment.this, ""+e, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}