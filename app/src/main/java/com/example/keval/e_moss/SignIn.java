package com.example.keval.e_moss;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keval.e_moss.Aadhaar.Barcode.BarcodeScan;
import com.example.keval.e_moss.Aadhaar.FingerPrint.FingerPrintView;
import com.example.keval.e_moss.Aadhaar.OTP.Adharotp;
import com.example.keval.e_moss.Utils.Constants;
import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    Button btSignInSignUp, btSignInSignIn, fb_login_button, google_button;
    EditText etSignInUserName, etSignInPassword;
    JSONArray arrayAllUserData;
    TextView tvSignInForgotPassword;
    ImageView aadhaarSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btSignInSignUp = (Button) findViewById(R.id.btSignInSignUp);
        btSignInSignIn = (Button) findViewById(R.id.btSignInSignIn);
        fb_login_button = (Button) findViewById(R.id.fb_login_button);
        google_button = (Button) findViewById(R.id.google_button);
        aadhaarSignIn = (ImageView) findViewById(R.id.aadhaarSignIn);
        etSignInUserName = (EditText) findViewById(R.id.etSignInUserName);
        etSignInPassword = (EditText) findViewById(R.id.etSignInPassword);
        tvSignInForgotPassword = (TextView) findViewById(R.id.tvSignInForgotPassword);

        btSignInSignUp.setOnClickListener(this);
        btSignInSignIn.setOnClickListener(this);
        tvSignInForgotPassword.setOnClickListener(this);
        aadhaarSignIn.setOnClickListener(this);

        arrayAllUserData = new JSONArray();

        try {
            if (!SharedPreferenceUtil.getString(Constants.USER_DETAILS, "").equalsIgnoreCase(""))
                arrayAllUserData = new JSONArray(SharedPreferenceUtil.getString(Constants.USER_DETAILS, ""));
            else
                arrayAllUserData = new JSONArray();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void login() {

        if (etSignInUserName.getText().toString().trim().equalsIgnoreCase(""))
            Toast.makeText(this, "Enter UserName", Toast.LENGTH_SHORT).show();
        else if (etSignInPassword.getText().toString().trim().equalsIgnoreCase(""))
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        else {

            if (arrayAllUserData.length() != 0)
                for (int i = 0; i <= arrayAllUserData.length() - 1; i++) {
                    JSONObject objectAllUserData = arrayAllUserData.optJSONObject(i);

                    if (!etSignInUserName.getText().toString().trim().equalsIgnoreCase(objectAllUserData.optString("userName"))
                            || !etSignInPassword.getText().toString().trim().equalsIgnoreCase(objectAllUserData.optString("password"))) {
                        Toast.makeText(this, "Wrong Credential", Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        startActivity(new Intent(this, DashBoard.class));
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            else
                Toast.makeText(this, "Wrong Credential", Toast.LENGTH_SHORT).show();
        }
    }

    public void AadhaarSignIn() {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notice");
        builder.setMessage("Launching this missile will destroy the entire universe. Is this what you intended to do?");

        // add the buttons
        builder.setPositiveButton("Launch missile", null);
        builder.setNegativeButton("Cancel", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setContentView(R.layout.my_dialog_layout);
        TextView myDialogLayoutOTP = (TextView) dialog.findViewById(R.id.myDialogLayoutOTP);
        TextView myDialogLayoutQR = (TextView) dialog.findViewById(R.id.myDialogLayoutQR);
        TextView myDialogLayoutFinger = (TextView) dialog.findViewById(R.id.myDialogLayoutFinger);

        myDialogLayoutOTP.setOnClickListener(this);
        myDialogLayoutQR.setOnClickListener(this);
        myDialogLayoutFinger.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btSignInSignUp:
                startActivity(new Intent(SignIn.this, SignUp.class));
                break;
            case R.id.btSignInSignIn:
                login();
                break;
            case R.id.tvSignInForgotPassword:

                break;
            case R.id.aadhaarSignIn:
                AadhaarSignIn();
                break;
            case R.id.myDialogLayoutOTP:
                startActivity(new Intent(this, Adharotp.class));
                break;
            case R.id.myDialogLayoutQR:
                startActivity(new Intent(this, BarcodeScan.class));
                break;
            case R.id.myDialogLayoutFinger:
                startActivity(new Intent(this, FingerPrintView.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        CommonUtil.closeKeyboard(this);
        finish();
        super.onBackPressed();
    }
}
