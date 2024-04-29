package com.food.food_order_hotel_admin.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HotelProduct
{
    @SerializedName("data")
    @Expose
    public List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data
    {
        @SerializedName("title")
        @Expose
        private String title;

        @SerializedName("foodType")
        @Expose
        private String foodType;

        @SerializedName("image")
        @Expose
        private String image;

        @SerializedName("hotelrealprice")
        @Expose
        private String hotelrealprice;

        @SerializedName("restaurantproduct_id")
        @Expose
        private String restaurantproduct_id;

        public String getRestaurantproduct_id() {
            return restaurantproduct_id;
        }

        public void setRestaurantproduct_id(String restaurantproduct_id) {
            this.restaurantproduct_id = restaurantproduct_id;
        }

        public String getFoodType() {
            return foodType;
        }

        public void setFoodType(String foodType) {
            this.foodType = foodType;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getHotelrealprice() {
            return hotelrealprice;
        }

        public void setHotelrealprice(String hotelrealprice) {
            this.hotelrealprice = hotelrealprice;
        }

        @SerializedName("price")
        @Expose
        private String price;

        @SerializedName("out_of_stock")
        @Expose
        private String out_of_stock;

        public String getOut_of_stock() {
            return out_of_stock;
        }

        public void setOut_of_stock(String out_of_stock) {
            this.out_of_stock = out_of_stock;
        }

        @SerializedName("sale_price")
        @Expose
        private String sale_price;

        @SerializedName("default_price")
        @Expose
        private String default_price;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSale_price() {
            return sale_price;
        }

        public void setSale_price(String sale_price) {
            this.sale_price = sale_price;
        }

        public String getDefault_price() {
            return default_price;
        }

        public void setDefault_price(String default_price) {
            this.default_price = default_price;
        }
    }
}
