package com.food.food_order_hotel_admin.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.food.food_order_hotel_admin.Fragment.DeliveryBoy.DeliveryBoyCancelOrder;
import com.food.food_order_hotel_admin.Fragment.DeliveryBoy.DeliveryBoyCompleteOrder;
import com.food.food_order_hotel_admin.Fragment.DeliveryBoy.DeliveryBoyNewOrder;

public class DeliveryBoyViewAdapter extends FragmentPagerAdapter {
    public DeliveryBoyViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new DeliveryBoyNewOrder();

        }
        else if (position == 1)
        {
            fragment = new DeliveryBoyCompleteOrder();
        }
        else if (position == 2)
        {
            fragment = new DeliveryBoyCancelOrder();

        }

        return fragment;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;

        switch (position)
        {
            case 0:
                title = "New Order";
                break;
            case 1:
                title = "Complete";
                break;
            case 2:
                title = "Cancel";
                break;

        }
//        if (position == 0)
//        {
//            title = "New Order";
//        }
//        else if (position == 1)
//        {
//            title = "Complete";
//        }
//        else if (position == 2)
//        {
//            title = "Cancel";
//        }
        return title;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
