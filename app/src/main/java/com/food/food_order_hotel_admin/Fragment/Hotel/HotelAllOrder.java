package com.food.food_order_hotel_admin.Fragment.Hotel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.food.food_order_hotel_admin.Activity.Homedashboard;
import com.food.food_order_hotel_admin.Adapter.HotelViewAdapter;
import com.google.android.material.tabs.TabLayout;

import food.food_order_hotel_admin.R;


public class HotelAllOrder extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;
    TextView txtPayNow;
    HotelViewAdapter viewPagerAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_hotel_all_order, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager1);
        viewPagerAdapter = new HotelViewAdapter(getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs1);
        txtPayNow = (TextView) view.findViewById(R.id.txtPayNow);
        tabLayout.setupWithViewPager(viewPager);
        txtPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), Homedashboard.class);
                startActivity(intent);
            }
        });

        //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("title");
        return view;
    }
}