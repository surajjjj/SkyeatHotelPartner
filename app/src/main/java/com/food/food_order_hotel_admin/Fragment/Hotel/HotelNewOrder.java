package com.food.food_order_hotel_admin.Fragment.Hotel;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.food.food_order_hotel_admin.Activity.Hotel.HotelMainActivity;
import com.food.food_order_hotel_admin.Adapter.OrderAdapter;
import com.food.food_order_hotel_admin.Model.GAllOrder;
import com.food.food_order_hotel_admin.Retrofit.RetrofitClient;

import java.util.ArrayList;

import food.food_order_hotel_admin.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelNewOrder extends Fragment {
    TextView txtOrderNotAvlble;
    Context context;
    String vendorID;
    OrderAdapter orderAdapter;
    RecyclerView lstHotelNew;
    ArrayList<GAllOrder.Data> newOrderList=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_hotel_new_order, container, false);
        txtOrderNotAvlble = view.findViewById(R.id.txtOrderNotAvalable);
        lstHotelNew = view.findViewById(R.id.lstHotelNew);
        newOrderList.clear();
        vendorID= HotelMainActivity.VENDOR_ID;

        getNewOrder();
        LinearLayoutManager llm=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        lstHotelNew.setLayoutManager(llm);

        return view;
    }
    private void getNewOrder() {
        RetrofitClient.getInstance().getMyApi().getHotelOrders("get_new_order_hotel_wise",vendorID).enqueue(new Callback<GAllOrder>() {
            @Override
            public void onResponse(Call<GAllOrder> call, Response<GAllOrder> response) {
                newOrderList= (ArrayList<GAllOrder.Data>) response.body().getData();
                if(newOrderList.size()>0)
                {
                    txtOrderNotAvlble.setVisibility(View.GONE);
                    orderAdapter = new OrderAdapter(getContext(),newOrderList);
                    lstHotelNew.setAdapter(orderAdapter);
                }
                else {
                    txtOrderNotAvlble.setVisibility(View.VISIBLE);
                    lstHotelNew.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<GAllOrder> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}