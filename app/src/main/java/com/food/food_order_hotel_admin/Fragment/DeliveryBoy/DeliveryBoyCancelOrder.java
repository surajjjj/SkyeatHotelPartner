package com.food.food_order_hotel_admin.Fragment.DeliveryBoy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import food.food_order_hotel_admin.R;


public class DeliveryBoyCancelOrder extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_delivery_boy_cancel_order, container, false);
        return view;
    }
}