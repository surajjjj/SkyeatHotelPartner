package com.food.food_order_hotel_admin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.food.food_order_hotel_admin.Adapter.NewTrajectionAdpter;
import com.food.food_order_hotel_admin.Model.TranjectionItem;
import com.food.food_order_hotel_admin.utilities.Config;
import com.food.food_order_hotel_admin.utilities.ConnectionDetector;
import com.food.food_order_hotel_admin.utilities.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import food.food_order_hotel_admin.R;

public class hotel_settalment extends AppCompatActivity {


    AsyncTask<String, Void, String> getAllOrders_Async;
    NewTrajectionAdpter adapter;
    ListView lvOrders;
    ArrayList<TranjectionItem> list=new ArrayList<TranjectionItem>();
    JSONParser jsonParser=new JSONParser();
    ConnectionDetector cd;
    public static String VENDOR_ID,HOTEL_NAME;
    SharedPreferences sharedPreferences;
    TextView btnall,btncomplte,btnpending;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_settalment);
        cd=new ConnectionDetector(this);
        lvOrders=(ListView)findViewById(R.id.lvOrders);

        btnall=(TextView) findViewById(R.id.btnall);
        btncomplte=(TextView) findViewById(R.id.btncomplte);
        btnpending=(TextView) findViewById(R.id.btnpending);
        adapter=new NewTrajectionAdpter(this,list);

        sharedPreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("hotel_name"))
        {
            HOTEL_NAME=sharedPreferences.getString("hotel_name",null);
        }
        if(sharedPreferences.contains("vendor_id"))
        {
            VENDOR_ID=sharedPreferences.getString("vendor_id",null);
        }


        lvOrders.setAdapter(adapter);
        if(cd.isConnectingToInternet()){
            String url= null

                    ;
            try {
                url = Config.get_url+
                        "action=get_all_wallet_tranjection"+
                        "&id=" + URLEncoder.encode(VENDOR_ID=sharedPreferences.getString("vendor_id",null) ,"utf-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            //Toast.makeText(this, ""+db.getDelivaryboyId(), Toast.LENGTH_SHORT).show();
            getAllOrders_Async=new GetAllOrders_Async();
            getAllOrders_Async.execute(url);
        }else{
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
    class GetAllOrders_Async extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(hotel_settalment.this);
            dialog.setMessage("Getting all Delivary Boy");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    getAllOrders_Async.cancel(true);
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
                Toast.makeText(hotel_settalment.this,"No response from server, Please check your internet connection", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONObject jsonObject=new JSONObject(result);
                if(jsonObject.getString("result").equals("true")){
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jobj=jsonArray.getJSONObject(i);

                        final String wallet_transactions_id=jobj.getString("wallet_transactions_id");
                        final String type=jobj.getString("type");
                        String amount=jobj.getString("amount");
                        String balance=jobj.getString("balance");
                        String status=jobj.getString("status");
                        String message=jobj.getString("message");
                        String made_by	=jobj.getString("made_by");
                        String user_type	=jobj.getString("user_type");
                        String date=jobj.getString("date_new");
                        Toast.makeText(hotel_settalment.this, ""+wallet_transactions_id, Toast.LENGTH_SHORT).show();
                        if (made_by.equals("auto")) {

                        }
                        else {

                            adapter.add(new TranjectionItem(wallet_transactions_id, type, amount, balance, status, message, made_by, user_type, date));
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}