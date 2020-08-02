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

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    private static final Pattern Password = Pattern.compile("^"+"(?=.*[a-z])"+"(?=.*[A-Z])"+"(?=.*[0-9])"+"(?=.*[!@#$%&*+])"+"(?=\\S+$)"+".{8,15}"+"$");

    private FirebaseAuth mAuth;

    ProgressBar progressBar;
    EditText et_mail,et_pswrd;
    TextView tv_mail,tv_pswrs;
    Button login;
    ImageView eye;
    int a=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_mail= findViewById(R.id.et_email);
        et_pswrd=findViewById(R.id.et_password);
        tv_mail=findViewById(R.id.tv_email);
        tv_pswrs=findViewById(R.id.tv_password);
        login= findViewById(R.id.btn_login);
        eye= findViewById(R.id.eye);
        progressBar=findViewById(R.id.login_progressbar);

        //for firebase
        mAuth = FirebaseAuth.getInstance();


//        Intent i= getIntent();
//        et_mail.setText(i.getStringExtra("mail"));
//        et_pswrd.setText(i.getStringExtra("pass"));


        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a%2==0){
                    et_pswrd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    et_pswrd.setSelection(et_pswrd.length());
                    a++;
                    Log.d("test","invisible: "+a);

                }
                else if(a%2!=0){
                    et_pswrd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    et_pswrd.setSelection(et_pswrd.length());
                    a++;
                    Log.d("test","invisible: "+a);
                }
            }
        });

        et_mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String mail= et_mail.getText().toString().trim();
                if(mail.isEmpty()){
                    tv_mail.setVisibility(View.VISIBLE);
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    tv_mail.setVisibility(View.VISIBLE);
                }
                else{
                    tv_mail.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_pswrd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pswrd= et_pswrd.getText().toString().trim();
                if(pswrd.isEmpty()){
                    tv_pswrs.setVisibility(View.VISIBLE);
                }
                else if(!Password.matcher(pswrd).matches()){
                    tv_pswrs.setVisibility(View.VISIBLE);
                }
                else{
                    tv_pswrs.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public boolean validate_email(){
        String mail= et_mail.getText().toString().trim();
        if(mail.isEmpty()){
            tv_mail.setVisibility(View.VISIBLE);
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            tv_mail.setVisibility(View.VISIBLE);
            return false;
        }
        else{

            tv_mail.setVisibility(View.GONE);
            return true;
        }
    }

    public boolean validate_pswrd(){
        String pswrd= et_pswrd.getText().toString().trim();
        if(pswrd.isEmpty()){
            tv_pswrs.setVisibility(View.VISIBLE);
            return false;
        }
        else if(!Password.matcher(pswrd).matches()){
            tv_pswrs.setVisibility(View.VISIBLE);
            return false;
        }
        else{
            tv_pswrs.setVisibility(View.GONE);
            return true;
        }
    }

    public void login(View view){
        progressBar.setVisibility(View.VISIBLE);
        if(!validate_email() | !validate_pswrd()){
            progressBar.setVisibility(View.GONE);
            return;
        }
        else{
            //for firebase authentication for checking the email exits in database or not
            mAuth.signInWithEmailAndPassword(et_mail.getText().toString(),et_pswrd.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if(task.isSuccessful()){
                                Intent intent=new Intent(Login.this, Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            Intent intent=new Intent(Login.this, Home.class);
            startActivity(intent);
            finish();

        }
    }

    public void signUp(View view){
        Intent intent=new Intent(Login.this,SignUp.class);
        startActivity(intent);
        finish();
    }


}