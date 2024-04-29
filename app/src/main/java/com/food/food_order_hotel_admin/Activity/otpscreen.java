package com.food.food_order_hotel_admin.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.food.food_order_hotel_admin.Activity.Hotel.HotelMainActivity;
import com.food.food_order_hotel_admin.utilities.Config;
import com.food.food_order_hotel_admin.utilities.ConnectionDetector;
import com.food.food_order_hotel_admin.utilities.JSONParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import food.food_order_hotel_admin.R;


public class otpscreen extends AppCompatActivity {

    // variable for FirebaseAuth class
    private FirebaseAuth mAuth;
    ConnectionDetector cd;
    AsyncTask<String, Void, String> updatetokenall;
    JSONParser jsonParser=new JSONParser();

    // variable for our text input
    // field for phone and OTP.
    private TextInputEditText edtPhone, edtOTP;

    // buttons for generating OTP and verifying OTP
    private Button verifyOTPBtn, generateOTPBtn;

    // string for storing our verification ID
    private String verificationId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpscreen);

        // below line is for getting instance
        // of our FirebaseAuth.
        mAuth = FirebaseAuth.getInstance();

        // initializing variables for button and Edittext.
        edtPhone = findViewById(R.id.idEdtPhoneNumber);
        edtOTP = findViewById(R.id.idEdtOtp);
        verifyOTPBtn = findViewById(R.id.idBtnVerify);
        generateOTPBtn = findViewById(R.id.idBtnGetOtp);
        cd=new ConnectionDetector(this);

        edtPhone.setInputType(EditorInfo.TYPE_NUMBER_FLAG_SIGNED|EditorInfo.TYPE_CLASS_NUMBER);
        edtOTP.setInputType(EditorInfo.TYPE_NUMBER_FLAG_SIGNED|EditorInfo.TYPE_CLASS_NUMBER);
        // setting onclick listener for generate OTP button.
        generateOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // below line is for checking whether the user
                // has entered his mobile number or not.
                if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                    // when mobile number text field is empty
                    // displaying a toast message.
                    Toast.makeText(otpscreen.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                } else {

                    if(cd.isConnectingToInternet()){
                        String url= null;


                        try {
                            url = Config.get_url+
                                    "action=verify_hotel" +
                                    "&phone=" + URLEncoder.encode(edtPhone.getText().toString(), "utf-8") ;
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }

                        updatetokenall = new UpdateTokenall();
                        updatetokenall.execute(url);

                    }else{
//                                Toast.makeText(HotelAdmin_MenusActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
//                                finish();
                    }

                    // if the text field is not empty we are calling our
                    // send OTP method for getting OTP from Firebase.


                }
            }
        });

        // initializing on click listener
        // for verify otp button
        verifyOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the OTP text field is empty or not.
                if (TextUtils.isEmpty(edtOTP.getText().toString())) {
                    // if the OTP text field is empty display
                    // a message to user to enter OTP
                    Toast.makeText(otpscreen.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else {
                    // if OTP field is not empty calling
                    // method to verify the OTP.
                    verifyCode(edtOTP.getText().toString());
                }
            }
        });
    }

    private void
    signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // if the code is correct and the task is successful
                            // we are sending our user to new activity.
                            Intent i = new Intent(otpscreen.this, LoginActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Toast.makeText(otpscreen.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)		 // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)				 // Activity (for callback binding)
                        .setCallbacks(mCallBack)		 // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // callback method is called on Phone auth provider.
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            verificationId = s;
        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
                edtOTP.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(otpscreen.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    // below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        // below line is used for getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }

    class UpdateTokenall extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(otpscreen.this);
            dialog.setMessage("Please Wait!!!");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                  //  getcoupon.cancel(true);
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
                Toast.makeText(otpscreen.this,"No response from server, Please check your internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject jsonObject=new JSONObject(result);
                if(jsonObject.getString("result").equals("true")){

                    Toast.makeText(otpscreen.this, "Please Wait verify Your Details...", Toast.LENGTH_SHORT).show();
                    String phone = "+91" + edtPhone.getText().toString();
                    sendVerificationCode(phone);
                    edtPhone.setVisibility(View.GONE);
                    edtOTP.setVisibility(View.VISIBLE);
                    verifyOTPBtn.setVisibility(View.VISIBLE);
                    generateOTPBtn.setVisibility(View.GONE);

                }
                else {
                    Toast.makeText(otpscreen.this, "Mobile Number Not register Please Contact to Company!!!", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
