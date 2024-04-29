package com.food.food_order_hotel_admin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.food.food_order_hotel_admin.Activity.Settalmentbreakup;
import com.food.food_order_hotel_admin.Activity.hotel_settalment;
import com.food.food_order_hotel_admin.Activity.pendingnew_payment;
import com.food.food_order_hotel_admin.Model.TranjectionItem;
import java.util.List;

import food.food_order_hotel_admin.R;

/**
 * Created by User on 17/04/2018.
 */
public class NewTrajectionAdpter extends ArrayAdapter<TranjectionItem> {
    Context context;

    class ViewHolder{

        TextView tvOrderId,tvdelboyname,tvOrderHDCharges,tvOrderFinalTotal,tvdate,tvOrderMenuqty,tvOrderMenuPrice,tvOrderType;


    }
    public NewTrajectionAdpter(Context context, List<TranjectionItem> objects) {
        super(context, 0, objects);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TranjectionItem oi = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.delivary_order_item_layout, parent, false);
            holder = new ViewHolder();


            holder.tvdelboyname = (TextView) convertView.findViewById(R.id.tvdelboyname);
            holder.tvdate = (TextView) convertView.findViewById(R.id.tvdate);
            holder.tvOrderId = (TextView) convertView.findViewById(R.id.tvOrderId);
            holder.tvOrderFinalTotal = (TextView) convertView.findViewById(R.id.tvOrderFinalTotal);
            holder.tvOrderType = (TextView) convertView.findViewById(R.id.tvOrderType);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (oi.getType().equals("Cr")&&oi.getStatus().equals("Success"))
        {
            holder.tvOrderType.setText("Payment Success");
            holder.tvOrderType.setTextColor(Color.parseColor("#03fc28"));
        } else if (oi.getType().equals("Cr")&&oi.getStatus().equals("Request")) {
            holder.tvOrderType.setText("Payment Pending");
            holder.tvOrderType.setTextColor(Color.parseColor("#e01304"));

        }
        holder.tvOrderId.setText("" + oi.getWallet_transactions_id());
        holder.tvOrderFinalTotal.setText("₹"+ oi.getAmount());
        holder.tvdelboyname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Settalmentbreakup.class);
                context.startActivity(intent);
            }
        });

//        holder.tvOrderType.setText("" + oi.getType() + "/-");
        holder.tvdate.setText("" + oi.getDate());



        return convertView;
    }

}