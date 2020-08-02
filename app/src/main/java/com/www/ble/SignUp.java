package com.www.ble;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private static final Pattern Password = Pattern.compile("^"+"(?=.*[a-z])"+"(?=.*[A-Z])"+"(?=.*[0-9])"+"(?=.*[!@#$%&*+])"+"(?=\\S+$)"+".{8,15}"+"$");

    public static final Pattern Phone= Pattern.compile("^[+]?(0)?[1-9 ][0-9 ]{9,15}$");

    int b=2;
    int c=2;

    private FirebaseAuth mAuth;

    ImageView eye2;

    ProgressBar progressbar;
    EditText etc_name,etc_mail,etc_address,etc_phone,etc_password;
    TextView tvc_name,tvc_mail,tvc_address,tvc_phone,tvc_password;
    Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etc_name=findViewById(R.id.etc_name);
        etc_mail=findViewById(R.id.etc_email);
        etc_address=findViewById(R.id.etc_address);
        etc_phone=findViewById(R.id.etc_phone);
        etc_password=findViewById(R.id.etc_password);
        tvc_name=findViewById(R.id.tvc_name);
        tvc_mail=findViewById(R.id.tvc_email);
        tvc_address=findViewById(R.id.tvc_address);
        tvc_phone=findViewById(R.id.tvc_phone);
        tvc_password=findViewById(R.id.tvc_password);
        btn_signup=findViewById(R.id.btn_create);
        eye2=findViewById(R.id.eye2);
        progressbar=findViewById(R.id.sign_progressbar);

        //for firebase
        mAuth = FirebaseAuth.getInstance();



        eye2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b%2==0){
                    etc_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    etc_password.setSelection(etc_password.length());
                    b++;
                    Log.d("test","invisible: "+b);

                }
                else if(b%2!=0){
                    etc_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etc_password.setSelection(etc_password.length());
                    b++;
                    Log.d("test","invisible: "+b);
                }
            }
        });


        etc_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 String name= etc_name.getText().toString().trim();

                 if(name.isEmpty()){
                     tvc_name.setVisibility(View.VISIBLE);
                 }
                 else if(name.length() < 4){
                     tvc_name.setVisibility(View.VISIBLE);
                 }
                 else{
                     tvc_name.setVisibility(View.GONE);
                 }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etc_mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String mail= etc_mail.getText().toString().trim();
                if(mail.isEmpty()){
                    tvc_mail.setVisibility(View.VISIBLE);
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    tvc_mail.setVisibility(View.VISIBLE);
                }
                else{
                    tvc_mail.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etc_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               String address= etc_address.getText().toString().trim();
               if(address.isEmpty()){
                   tvc_address.setVisibility(View.VISIBLE);
               }
               else if(address.length() < 10){
                   tvc_address.setVisibility(View.VISIBLE);
               }
               else{
                   tvc_address.setVisibility(View.GONE);
               }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etc_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phone=etc_phone.getText().toString().trim();
                if(phone.isEmpty()){
                    tvc_phone.setVisibility(View.VISIBLE);
                }
                else if(!Phone.matcher(phone).matches()){
                    tvc_phone.setVisibility(View.VISIBLE);
                }
                else{
                    tvc_phone.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etc_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password= etc_password.getText().toString().trim();
                if(password.isEmpty()){
                    tvc_password.setVisibility(View.VISIBLE);
                }
                else if(!Password.matcher(password).matches()){
                    tvc_password.setVisibility(View.VISIBLE);
                }
                else{
                    tvc_password.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }


    public boolean validate_name(){
        String name= etc_name.getText().toString().trim();

        if(name.isEmpty()){
            tvc_name.setVisibility(View.VISIBLE);
            return false;
        }
        else if(name.length() < 4){
            tvc_name.setVisibility(View.VISIBLE);
            return false;
        }
        else{
            tvc_name.setVisibility(View.GONE);
            return true;
        }
    }

    public boolean validate_email(){
        String mail= etc_mail.getText().toString().trim();
        if(mail.isEmpty()){
            tvc_mail.setVisibility(View.VISIBLE);
            return false;
        }
        else  if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            tvc_mail.setVisibility(View.VISIBLE);
            return false;
        }
        else{
            tvc_mail.setVisibility(View.GONE);
            return true;
        }

    }

    public boolean validate_address(){
        String address= etc_address.getText().toString().trim();
        if(address.isEmpty()){
            tvc_address.setVisibility(View.VISIBLE);
            return false;
        }
        else if(address.length() < 10){
            tvc_address.setVisibility(View.VISIBLE);
            return false;
        }
        else{
            tvc_address.setVisibility(View.GONE);
            return true;
        }
    }

    public boolean validate_phone(){
        String phone=etc_phone.getText().toString().trim();
        if(phone.isEmpty()){
            tvc_phone.setVisibility(View.VISIBLE);
            return false;
        }
        else if(!Phone.matcher(phone).matches()){
            tvc_phone.setVisibility(View.VISIBLE);
            return false;
        }
        else{
            tvc_phone.setVisibility(View.GONE);
            return true;
        }
    }

    public boolean validate_password(){
        String password= etc_password.getText().toString().trim();
        if(password.isEmpty()){
            tvc_password.setVisibility(View.VISIBLE);
            return false;
        }
        else if(!Password.matcher(password).matches()){
            tvc_password.setVisibility(View.VISIBLE);
            return false;
        }
        else{
            tvc_password.setVisibility(View.GONE);
            return true;
        }

    }



    public void create_account(View view){
        progressbar.setVisibility(View.VISIBLE);
        if(!validate_name() | !validate_email() | !validate_address() | !validate_phone() | !validate_password() ){
            progressbar.setVisibility(View.GONE);
            return;
        }
        else{

            //for firebase authentication
            mAuth.createUserWithEmailAndPassword(etc_mail.getText().toString(),etc_password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressbar.setVisibility(View.GONE);
                            if(task.isSuccessful()){

                                Intent intent= new Intent(SignUp.this,Login.class);
                                intent.putExtra("mail",etc_mail.getText().toString());
                                intent.putExtra("pass",etc_password.getText().toString());
                                startActivity(intent);
                                finish();
                            }
                            else{
                                if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                    Toast.makeText(SignUp.this, "You are already registered", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });


        }
    }

    public void signIn(View view){
        Intent intent=new Intent(SignUp.this,Login.class);
        startActivity(intent);
        finish();
    }

}