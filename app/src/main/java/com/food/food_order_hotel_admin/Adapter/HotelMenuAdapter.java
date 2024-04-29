package com.food.food_order_hotel_admin.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.food.food_order_hotel_admin.Model.HotelProduct;
import com.food.food_order_hotel_admin.Model.Sample;
import com.food.food_order_hotel_admin.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.Objects;

import food.food_order_hotel_admin.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HotelMenuAdapter extends RecyclerView.Adapter<HotelMenuAdapter.MyViewHolder>
{

    Context context;
    String ACTION;
    int possition;
    String productId;
    private OnCheckChangeListener checkChangeListener;

    public void setCheckChangeListener(OnCheckChangeListener checkChangeListener) {
        this.checkChangeListener = checkChangeListener;
    }

    ArrayList<HotelProduct.Data> HotelMenuList=new ArrayList<>();

    public HotelMenuAdapter(Context context, ArrayList<HotelProduct.Data> hotelMenuList) {
        this.context = context;
        HotelMenuList = hotelMenuList;
    }

    public interface OnCheckChangeListener {
        void onCheckedChange(int position, boolean isChecked);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_hotel_menu,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        possition =position;
        String foodType=HotelMenuList.get(position).getFoodType();
        if(foodType.equalsIgnoreCase("Veg"))
        {
            holder.imgVeg.setImageResource(R.drawable.ic_veg);
        }
        else
        {
            holder.imgVeg.setImageResource(R.drawable.ic_nonveg);
        }
        holder.txtMenuName.setText(HotelMenuList.get(position).getTitle());
        holder.txtMenuPrice.setText(HotelMenuList.get(position).getHotelrealprice());

        String imageUrl = HotelMenuList.get(position).getImage();
        String out_of_stock = HotelMenuList.get(position).getOut_of_stock();
//        holder.switchManually.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            int pos = getAdapterPosition();
//            if (pos != RecyclerView.NO_POSITION) {
//                checkChangeListener.onCheckedChange(pos, isChecked);
//            }
//        });

        if(Objects.equals(out_of_stock, "0"))
        {
            holder.switchManually.setChecked(true);
            //holder.switchManually.setText("In Stock");
        }
        else {
            holder.switchManually.setChecked(false);
            //holder.switchManually.setText("turn manually");
        }


//        Glide.with(context)
//                .load(imageUrl)
//                //.error(R.drawable.ic_launcher_background) // Replace with your error placeholder
//                .into(holder.imgHotelMenu);
    }

    @Override
    public int getItemCount() {
        return HotelMenuList.size();
    }

    public void menuAvailable()
    {
        RetrofitClient.getInstance().getMyApi().updateMenuAvailable(ACTION,productId).enqueue(new Callback<Sample>() {
            @Override
            public void onResponse(Call<Sample> call, Response<Sample> response) {
                boolean result=response.body().getResult();
                if(result)
                {
                    Toast.makeText(context, "Status update successfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context, "Status not update", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Sample> call, Throwable t)
            {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtMenuName,txtMenuPrice;
        Switch switchManually;
        ImageView imgHotelMenu,imgVeg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMenuName=itemView.findViewById(R.id.txtMenuName);
            txtMenuPrice=itemView.findViewById(R.id.txtMenuPrice);
            imgHotelMenu=itemView.findViewById(R.id.imgHotelMenu);
            switchManually=itemView.findViewById(R.id.switchManually);
            imgVeg=itemView.findViewById(R.id.imgVeg);

            switchManually.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    productId=HotelMenuList.get(possition ).getRestaurantproduct_id();
                    if(b)
                    {
                        ACTION="update_menu_status_avb";
                        Toast.makeText(context, productId, Toast.LENGTH_SHORT).show();
                        menuAvailable();

                    }
                    else {
                        ACTION="update_menu_status_not_avb";
                        Toast.makeText(context, productId, Toast.LENGTH_SHORT).show();
                        menuAvailable();
                    }

                }
            });
        }
    }
}
