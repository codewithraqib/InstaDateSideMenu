package com.example.raqib.instadate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("ConstantConditions")
public class Login extends AppCompatActivity  implements View.OnClickListener{
    TextView RegisterHere,LoginButton;
    EditText UserEmailLogin,UserLoginPassword;
    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userLocalStore = new UserLocalStore(this);

        UserEmailLogin = (EditText)findViewById(R.id.UserEmailLogin);
        UserLoginPassword = (EditText)findViewById(R.id.UserLoginPassword);
        RegisterHere = (TextView) findViewById(R.id.newUser);
        LoginButton = (TextView) findViewById(R.id.LoginButton);

        LoginButton.setOnClickListener(this);
        RegisterHere.setOnClickListener(this);

    }
    @Override
    protected void onStart() {
        super.onStart();
        if(authenticate()){
            displayDetails();
        }
    }

    private void displayDetails() {
        User user = userLocalStore.getLoggedInUser();
    }

    private boolean authenticate(){
        return userLocalStore.getUserLoggedIn();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.LoginButton:
                String email = UserEmailLogin.getText().toString();
                String password = UserLoginPassword.getText().toString();

                //CHECK FOR EMPTY EMAIL
                boolean emailCheckEmpty = true;
                boolean emailCheck = true;

                if(email.equals("")){
                    emailCheckEmpty = false;
                    Toast toast =  Toast.makeText(Login.this, "Please Provide Your Email! ", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else{

                    //CHECK FOR VALID EMAIL FORMAT
                    emailCheck = isValidEmail(email);
                    if(!emailCheck ){
                        emailCheck = false;

                        Toast toast =  Toast.makeText(Login.this, "This Is Not A Valid Email Format, Please Provide Valid Email! ", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                }

                //CHECK FOR EMPTY PASSWORD
                boolean passwordCheckEmpty= true;
                if(password.equals("")){
                    passwordCheckEmpty = false;
                    Toast toast = Toast.makeText(Login.this, "Please Provide The Particular Password!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }

                if(emailCheckEmpty && passwordCheckEmpty && emailCheck) {

                    User user = new User(email, password);
                    authenticate(user);
                }

                break;

            case R.id.newUser:
                startActivity(new Intent(Login.this, Register.class));

                break;
        }
    }

    public static boolean isValidEmail(CharSequence target ) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void authenticate(User user){
    ServerRequest serverRequest = new ServerRequest(this);

        if (isNetworkAvailable()) {
            serverRequest.fetchUserDataInBackground(user, new GetUserCallback() {
                @Override
                public void done(User returnedUser) {
                    if (returnedUser == null) {
                        showErrorMessage();
                    } else {
                        logUserIn(returnedUser);
                    }
                }
            });
        } else{
            Toast toast = Toast.makeText(Login.this, "You Don't Have An Active Internet Connection, Please Check Your Internet Connection", Toast.LENGTH_LONG);
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

    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
        dialogBuilder.setMessage("Incorrect User Details");
        dialogBuilder.setPositiveButton("OK",null);
        dialogBuilder.show();
    }
    private void logUserIn(User returnedUser){
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);
        startActivity(new Intent(this, MainActivity.class));
         Toast toast = Toast.makeText(this, "You Have Been Successfully Logged In! ", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}
