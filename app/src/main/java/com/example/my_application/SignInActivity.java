package com.example.my_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.my_application.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignInActivity extends AppCompatActivity {

    EditText inputEmail,inputPassword;
    Button loginButton;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    private ActivitySignInBinding binding;
       @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();


           inputEmail = findViewById(R.id.inputEmail);
           inputPassword = findViewById(R.id.inputPassword);
           loginButton= findViewById(R.id.loginButton);
           progressDialog = new ProgressDialog(this);
           mAuth = FirebaseAuth.getInstance();
           mUser =mAuth.getCurrentUser();


    }
    private  void setListeners(){
           binding.textCreateNewAccount.setOnClickListener(v ->
                   startActivity(new Intent(getApplicationContext(),SignUpActivity.class)));

       loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               perforLogin();
           }
       });


       }

    private void perforLogin() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();


        if (email.matches(emailPattern)) {
            inputEmail.setError("Enter Connext Email");
        } else if (password.isEmpty() || password.length() < 6) {
            inputPassword.setError("Enter Proper Password ");
        } else {
            progressDialog.setMessage("Wait While Sign In...");
            progressDialog.setTitle("Sign In");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(SignInActivity.this,"Sign In Successful", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(SignInActivity.this,""+task.getException(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    } private void sendUserToNextActivity() {
        Intent intent = new Intent(SignInActivity.this,Page1Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}