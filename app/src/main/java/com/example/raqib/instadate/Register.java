package com.example.raqib.instadate;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity  implements View.OnClickListener{
    EditText UserEmail, UserPassword,UserName,UserPasswordConfirm;
    TextView LoginHere,RegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getIntent();

        RegisterButton = (TextView) findViewById(R.id.RegisterButton);
        LoginHere = (TextView) findViewById(R.id.LoginHere);

        UserEmail = (EditText) findViewById(R.id.UserEmail);
        UserName = (EditText) findViewById(R.id.UserName);
        UserPassword = (EditText) findViewById(R.id.UserPassword);
        UserPasswordConfirm = (EditText) findViewById(R.id.UserPasswordConfirm);


        RegisterButton.setOnClickListener(this);
        LoginHere.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RegisterButton:

                String name = UserName.getText().toString();
                String email = UserEmail.getText().toString();
                String password = UserPassword.getText().toString();
                String passwordConfirm = UserPasswordConfirm.getText().toString();


                //CHECK THE CREDENTIALS
                //CHECK FOR EMPTY NAME
                boolean nameCheckEmpty = true;
                if(name.equals("")){
                    nameCheckEmpty = false;
                  Toast toast =  Toast.makeText(Register.this, "Please Provide Your Name! ", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }

                //CHECK FOR EMPTY EMAIL
                Boolean emailCheckEmpty = true;
                boolean emailCheck = true;
                if(email.equals("")){
                    emailCheckEmpty = false;
                    Toast toast =  Toast.makeText(Register.this, "Please Provide Your Email! ", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else{

                    //CHECK FOR VALID EMAIL FORMAT
                    emailCheck = isValidEmail(email);
                    if(!emailCheck ){
                        emailCheck = false;

                        Toast toast =  Toast.makeText(Register.this, "This Is Not A Valid Email Format, Please Provide Valid Email! ", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                }

                //CHECK FOR EMPTY PASSWORD
                boolean passwordCheckEmpty= true;
                if(password.equals("")){
                    passwordCheckEmpty = false;
                   Toast toast = Toast.makeText(Register.this, "Please Provide A Strong Password! ", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }

                //CHECK FOR EMPTY CONFIRM PASSWORD
                boolean confirmPasswordCheckEmpty= true;
                if(passwordConfirm.equals("") && passwordCheckEmpty){
                    confirmPasswordCheckEmpty = false;
                   Toast toast = Toast.makeText(Register.this, "Please Type Your Password Again! ", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }

                //CHECK FOR PASSWORD AND CONFIRM PASSWORD MATCH
                boolean passwordCheck= true;
                if(!password.equals(passwordConfirm) && confirmPasswordCheckEmpty) {
                    passwordCheck   = false;
                   Toast toast = Toast.makeText(Register.this, "Password and Confirm Password Are Not Same, Please Check Again! ", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();

                }

                if(nameCheckEmpty && emailCheckEmpty && passwordCheckEmpty && passwordCheck && emailCheck && confirmPasswordCheckEmpty ) {

                    User user = new User(name, email, password);
                    registeredUser(user);
                }

                break;

            case R.id.LoginHere:
                startActivity(new Intent(Register.this, Login.class));
                this.finish();

                break;
        }

    }

    public static boolean isValidEmail(CharSequence  target ) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    //IMPLEMENTATION OF REGISTERED USER
    private void registeredUser(User user) {
        ServerRequest serverRequest = new ServerRequest(this);

        Log.e("name", user.name);
        Log.e("email", user.email);
        Log.e("password", user.password);

        if (isNetworkAvailable()) {

            serverRequest.storeUserDataInBackground(user, new GetUserCallback() {
                @Override
                public void done(User returnedUser) {
                    startActivity(new Intent(Register.this, Login.class));
                    Toast toast =Toast.makeText(Register.this, "You Have Been Successfully Registered LogIn Now! ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();

                }
            });
        } else{
            Toast toast = Toast.makeText(Register.this, "You Don't Have An Active Internet Connection Please Check Your Internet Connection", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    //HELPER METHOD TO DETERMINE THE ACTIVE INTERNET CONNECTION
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
