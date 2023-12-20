package com.food.food_order_hotel_admin.Model;

/**
 * Created by User on 17/04/2018.
 */
public class TranjectionItem {
    String wallet_transactions_id;
    String type;
    String amount;
    String balance;
    String status;
    String message;
    String made_by;
    String user_type;
    String date;




    public TranjectionItem(String wallet_transactions_id, String type, String amount, String balance, String status, String message, String made_by, String user_type, String date) {
        this.wallet_transactions_id = wallet_transactions_id;
        this.type=type;
        this.amount=amount;
        this.balance=balance;
        this.status=status;
        this.message=message;
        this.made_by=made_by;
        this.user_type=user_type;
        this.date=date;

    }

    public String getWallet_transactions_id() {
        return wallet_transactions_id;
    }

    public String getType() {
        return type;
    }
    public String getAmount() {
        return amount;
    }


    public String getBalance() {
        return balance;
    }
    public String getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
 public String getMade_by() {
        return made_by;
    }
 public String getUser_type() {
        return user_type;
    }public String getDate() {
        return date;
    }



}
